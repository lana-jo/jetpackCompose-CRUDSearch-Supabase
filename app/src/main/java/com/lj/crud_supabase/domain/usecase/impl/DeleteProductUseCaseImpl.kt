package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.DeleteProductUseCase
import javax.inject.Inject

/**
 * Implementasi dari [DeleteProductUseCase] yang menghapus sebuah produk.
 *
 * @property productRepository Repositori untuk mengelola produk.
 */
class DeleteProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : DeleteProductUseCase {

    /**
     * Menjalankan kasus penggunaan untuk menghapus sebuah produk.
     *
     * @param input Masukan untuk kasus penggunaan, berisi ID produk yang akan dihapus.
     * @return [DeleteProductUseCase.Output.Success] jika produk berhasil dihapus.
     */
    override suspend fun execute(input: DeleteProductUseCase.Input): DeleteProductUseCase.Output {
        productRepository.deleteProduct(input.productId)
        return DeleteProductUseCase.Output.Success
    }
}