package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.CreateProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * Implementasi dari [CreateProductUseCase] yang membuat produk baru.
 *
 * @property productRepository Repositori untuk mengelola produk.
 */
class CreateProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository,
) : CreateProductUseCase {

    /**
     * Menjalankan kasus penggunaan untuk membuat produk baru.
     *
     * @param input Input untuk kasus penggunaan, berisi detail produk yang akan dibuat.
     * @return [CreateProductUseCase.Output.Success] jika produk berhasil dibuat,
     * atau [CreateProductUseCase.Output.Failure] jika terjadi kesalahan.
     */
    override suspend fun execute(input: CreateProductUseCase.Input): CreateProductUseCase.Output {
        return try {
            withContext(Dispatchers.IO) {
                val result = productRepository.createProduct(product = input.product)
                if (result) {
                    CreateProductUseCase.Output.Success(result = result)
                } else {
                    CreateProductUseCase.Output.Failure()
                }
            }
        } catch (e: Exception) {
            return CreateProductUseCase.Output.Failure.Conflict

        }
    }
}