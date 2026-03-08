package com.lj.crud_supabase.data.repository.impl

import com.lj.crud_supabase.BuildConfig
import com.lj.crud_supabase.data.network.dto.ProductDto
import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.models.Product
import com.lj.crud_supabase.domain.models.Sale
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
) : ProductRepository {
    override suspend fun searchProducts(query: String): List<Product> {
        return emptyList()
    }
    /*override suspend fun searchProducts(query: String): List<Product> {
        return try {
            val dtos = postgrest["products"].select {
                filter {
                    ilike("name", "%$query%")
                }
            }.decodeList<ProductDto>()
            dtos.map { dto ->
                Product(
                    id = dto.id,
                    name = dto.name,
                    price = dto.price,
                    image = dto.image ?: "",
                    stock = dto.stock ?: 0 // Asumsi ada stock di ProductDto, tambah jika belum
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Error searching products")
            emptyList()
        }
    }*//*
    override fun searchProducts(query: String): Flow<List<Product>> = flow {
        val results = postgrest["products"].select { ilike("name", "%$query%") }.decodeList<Product>()
        emit(results)
    }*/

    private companion object {
        const val BUCKET_NAME = "Product%20Image"
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

    override suspend fun insertSale(sale: Sale) {
        /*val saleDto = SaleDto(
            products = Json.encodeToString(sale.products),
            total = sale.*/
      /*  return  try {
            postgrest.from("sales").insert(sale)
        } catch (e: Exception) {
            throw e // Handle in ViewModel
        }*/
        return Timber.d("Sale inserted: $sale")
    }

    override suspend fun updateStock(id: String, quantitySold: Int) {
        return Timber.d("Updating stock for product $id by $quantitySold")
        /*return   try {
            val current = postgrest.from("products").select { eq("id", id) }.decodeSingle<ProductDto>().stock ?: 0
            postgrest.from("products").update({ set("stock", current - quantitySold) }) { eq("id", id) }
        } catch (e: Exception) {
            throw e
        }*/
    }

    override suspend fun updateProduct(
        id: String,
        name: String,
        price: Double,
        imageName: String,
        imageFile: ByteArray
    ) {

        val imagePath =
            storage[BUCKET_NAME].upload(
                path = "$name $imageName.png",
                data = imageFile,
            ){
                this.upsert = true          // ← INI WAJIB ditambah supaya bisa overwrite
                // Opsional tapi disarankan:
//                contentType = "image/png"   // atau "image/jpeg" sesuai format gambar
                // cacheControl = "3600"    // contoh: cache 1 jam
            }.path

        if (imageFile.isNotEmpty()) {
            val oldProduct = try {
                getProduct(id)
            } catch (e: Exception) {
                Timber.e(e, "Error getting old product for image deletion")
                null
            }

            // Hapus gambar lama dari storage jika ada
            /*if (oldProduct?.image != null && oldProduct.image.isNotEmpty()) {
                deleteOldImage(oldProduct.image)
            }*/

            // Update produk dengan gambar baru

            val imageUrl = storage.from(BUCKET_NAME).publicUrl(imagePath)
            postgrest["products"].update({
                set("name", name)
                set("price", price)
                set("image", imageUrl)
//                set("image", buildImageUrl(imageFileName = "$name $imageName.png"))
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