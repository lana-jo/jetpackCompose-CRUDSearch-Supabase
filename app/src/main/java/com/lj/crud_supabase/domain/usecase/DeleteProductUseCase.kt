package com.lj.crud_supabase.domain.usecase

interface DeleteProductUseCase: UseCase<DeleteProductUseCase.Input, DeleteProductUseCase.Output> {
    class Input(val productId: String)

    sealed class Output {
        object Success: Output()
    }
}