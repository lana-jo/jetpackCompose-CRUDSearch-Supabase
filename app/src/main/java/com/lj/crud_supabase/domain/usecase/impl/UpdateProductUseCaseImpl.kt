package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.UpdateProductUseCase
import javax.inject.Inject

/**
 * Implementasi dari [UpdateProductUseCase] yang memperbarui sebuah produk.
 *
 * @property productRepository Repositori untuk mengelola produk.
 */
class UpdateProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : UpdateProductUseCase {

    /**
     * Menjalankan kasus penggunaan untuk memperbarui sebuah produk.
     *
     * @param input Masukan untuk kasus penggunaan, berisi detail produk yang akan diperbarui.
     * @return [UpdateProductUseCase.Output.Success] jika produk berhasil diperbarui.
     */
    override suspend fun execute(input: UpdateProductUseCase.Input): UpdateProductUseCase.Output {
        productRepository.updateProduct(
            id = input.id, name = input.name, price = input.price,
            imageName = input.imageName, imageFile = input.imageFile
        )
        return UpdateProductUseCase.Output.Success
    }
}