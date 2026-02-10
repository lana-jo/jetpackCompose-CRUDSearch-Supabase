package com.lj.crud_supabase.domain.usecase

import com.lj.crud_supabase.domain.model.Product

interface GetProductsUseCase : UseCase<Unit, GetProductsUseCase.Output> {
    sealed class Output {
        class Success(val data: List<Product>): Output()
        object Failure : Output()
    }

}