package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.DeleteProductUseCase
import javax.inject.Inject

class DeleteProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : DeleteProductUseCase {
    override suspend fun execute(input: DeleteProductUseCase.Input): DeleteProductUseCase.Output {
        productRepository.deleteProduct(input.productId)
        return DeleteProductUseCase.Output.Success
    }
}