package com.lj.crud_supabase.domain.usecase

import com.lj.crud_supabase.data.repository.product.ProductRepository
//import com.lj.crud_supabase.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepository
) : CreateProductUseCase {
    override suspend fun execute(input: CreateProductUseCase.Input): CreateProductUseCase.Output {
        return try {
            productRepository.createProduct(input.product)
            CreateProductUseCase.Output.Success(true)
        } catch (e: Exception) {
            CreateProductUseCase.Output.Failure.InternalError
        }
    }
}
