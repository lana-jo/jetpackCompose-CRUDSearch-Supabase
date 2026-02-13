package com.lj.crud_supabase.data.repository.impl

import com.lj.crud_supabase.BuildConfig
import com.lj.crud_supabase.data.network.dto.ProductDto
import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.model.Product
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : ProductRepository {

    private companion object {
        const val BUCKET_NAME = "Product Image"
    }

    override suspend fun createProduct(product: Product): Boolean {
        return try {
            val productDto = ProductDto(
                name = product.name,
                price = product.price,
            )
            postgrest["products"].insert(productDto)
            true
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    override suspend fun getProducts(): List<ProductDto>? {
        /* val result = postgrest["products"]
             .select().decodeList<ProductDto>()
         return result*/
        return try {
            val result = postgrest["products"]
                .select().decodeList<ProductDto>()
            return result
        } catch (e: Exception) {
            Timber.e(e, "Error getting products")
            null
        }
    }


    override suspend fun getProduct(id: String): ProductDto {
        return postgrest["products"].select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<ProductDto>()
    }

    override suspend fun deleteProduct(id: String) {
        postgrest["products"].delete {
            filter {
                eq("id", id)
            }
        }
    }

    override suspend fun updateProduct(
        id: String,
        name: String,
        price: Double,
        imageName: String,
        imageFile: ByteArray
    ) {


        if (imageFile.isNotEmpty()) {
            val oldProduct = try {
                getProduct(id)
            } catch (e: Exception) {
                Timber.e(e, "Error getting old product for image deletion")
                null
            }

            // Hapus gambar lama dari storage jika ada
            if (oldProduct?.image != null && oldProduct.image.isNotEmpty()) {
                deleteOldImage(oldProduct.image)
            }

            val imagePath =
                storage["Product%20Image"].upload(
//                    path = "$imageName.png",
                    path = "$name ++ $imageName.png",
                    data = imageFile,
                ).path

            val imageUrl = storage.from("Product%20Image").publicUrl(imagePath)
            postgrest["products"].update({
                set("name", name)
                set("price", price)
                set("image", buildImageUrl(imageFileName = imageUrl))
            }) {
                filter {
                    eq("id", id)
                }
            }
        } else {
            postgrest["products"].update({
                set("name", name)
                set("price", price)
            }) {
                filter {
                    eq("id", id)
                }
            }
        }
    }


    /**
     * Fungsi helper untuk menghapus gambar lama dari Supabase Storage
     *
     * @param imageUrl URL publik gambar yang akan dihapus
     */
    private suspend fun deleteOldImage(imageUrl: String) {
        return try {
            // Extract path dari public URL
            // URL format: https://xxx.supabase.co/storage/v1/object/public/Product Image/image_123.png
            val imagePath = extractPathFromUrl(imageUrl)

            if (imagePath.isNotEmpty()) {
                storage[BUCKET_NAME].delete(imagePath)
                Timber.d("Old image deleted successfully: $imagePath")
            } else {

            }
        } catch (e: Exception) {
            // Jangan crash jika gagal hapus file lama
            Timber.e(e, "Error deleting old image from storage")
        }
    }

    /**
     * Extract path dari public URL Supabase
     *
     * Contoh input:
     * https://abc123.supabase.co/storage/v1/object/public/Product Image/image_123.png
     *
     * Contoh output:
     * image_123.png
     */
    private fun extractPathFromUrl(url: String): String {
        return try {
            // Split by "Product Image/" dan ambil bagian setelahnya
            val parts = url.split("$BUCKET_NAME/")
            if (parts.size > 1) {
                parts[1].replace("%20", " ")
            } else {
                ""
            }
        } catch (e: Exception) {
            Timber.e(e, "Error extracting path from URL: $url")
            ""
        }
    }



    private fun buildImageUrl(imageFileName: String) =
        "${BuildConfig.BASE_URL}/storage/v1/object/public/${imageFileName}".replace(" ", "%20")
}