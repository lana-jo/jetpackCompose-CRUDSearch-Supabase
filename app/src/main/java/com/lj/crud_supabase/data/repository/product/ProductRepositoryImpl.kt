package com.lj.crud_supabase.data.repository.product

import com.lj.crud_supabase.BuildConfig
import com.lj.crud_supabase.data.dto.Product
import com.lj.crud_supabase.data.dto.ProductDto
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.*
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import timber.log.Timber
import java.lang.Exception


class ProductRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
    private val auth: Auth
) : ProductRepository {

    @Serializable
    private data class ProductWithUserId(
        val id: String?,
        val name: String,
        val price: Double,
        val image: String?,
        @SerialName("user_id")
        val userId: String? = null
    )

    override suspend fun createProduct(product: Product): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val user = auth.currentUserOrNull() ?: return@withContext false
                val productWithUserId = ProductWithUserId(
                    id = product.id,
                    name = product.name,
                    price = product.price,
                    image = product.image,
                    userId = user.id
                )
                postgrest.from("products").insert(productWithUserId)
                Timber.d("createProduct: success")
                true
            } catch (e: Exception) {
                Timber.e(e, "createProduct: failed")
                false
            }
        }
    }

    override suspend fun getProducts(): List<ProductDto>? {
        return withContext(Dispatchers.IO) {
            try {
                val result = postgrest.from("products")
                    .select().decodeList<ProductDto>()
                Timber.d("getProducts: success")
                result
            } catch (e: Exception) {
                Timber.e(e, "getProducts: failed")
                null
            }
        }
    }


    override suspend fun getProduct(id: String): ProductDto {
        return withContext(Dispatchers.IO) {
            try {
                val product = postgrest.from("products").select {
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<ProductDto>()
                Timber.d("getProduct: success for id $id")
                product
            } catch (e: Exception) {
                Timber.e(e, "getProduct: failed for id $id")
                throw e
            }
        }
    }

    override suspend fun deleteProduct(id: String) {
        withContext(Dispatchers.IO) {
            try {
                postgrest.from("products").delete {
                    filter {
                        eq("id", id)
                    }
                }
                Timber.d("deleteProduct: success for id $id")
            } catch (e: Exception) {
                Timber.e(e, "deleteProduct: failed for id $id")
            }
        }
    }

    override suspend fun updateProduct(
        id: String?,
        name: String,
        price: Double,
        imageName: String,
        imageFile: ByteArray
    ) {
        withContext(Dispatchers.IO) {
            try {
                if (imageFile.isNotEmpty()) {
                    val uploadResponse =
                        storage.from("Product Image").upload(path = "$imageName.png", data = imageFile) {
                            upsert = true
                        }
                    postgrest.from("products").update({
                        set("name", name)
                        set("price", price)
                        set("image", buildImageUrl(imageFileName = uploadResponse.path))
                    }) {
                        filter {
                            eq("id", id!!)
                        }
                    }
                } else {
                    postgrest.from("products").update({
                        set("name", name)
                        set("price", price)
                    }) {
                        filter {
                            eq("id", id!!)
                        }
                    }
                }
                Timber.d("updateProduct: success for id $id")
            } catch (e: Exception) {
                Timber.e(e, "updateProduct: failed for id $id")
            }
        }
    }

    // Because I named the bucket as "Product Image" so when it turns to an url, it is "%20"
    // For better approach, you should create your bucket name without space symbol
    private fun buildImageUrl(imageFileName: String) =
        "${BuildConfig.BASE_URL}/storage/v1/object/public/Product Image/${imageFileName}".replace(" ", "%20")
}