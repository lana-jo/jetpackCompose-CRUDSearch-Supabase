package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.UpdateProductUseCase
import javax.inject.Inject

/**
 * Implementation of [UpdateProductUseCase] that updates a product.
 *
 * @property productRepository The repository for managing products.
 */
class UpdateProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : UpdateProductUseCase {

    /**
     * Executes the use case to update a product.
     *
     * @param input The input for the use case, containing the product details to be updated.
     * @return [UpdateProductUseCase.Output.Success] if the product was updated successfully.
     */
    override suspend fun execute(input: UpdateProductUseCase.Input): UpdateProductUseCase.Output {
        productRepository.updateProduct(
            id = input.id, name = input.name, price = input.price,
            imageName = input.imageName, imageFile = input.imageFile
        )
        return UpdateProductUseCase.Output.Success
    }
}