package com.lj.crud_supabase.di

import com.lj.crud_supabase.domain.usecase.*
import com.lj.crud_supabase.domain.usecase.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Modul Hilt untuk menyediakan implementasi kasus penggunaan.
 *
 * Modul ini bertanggung jawab untuk mengikat antarmuka kasus penggunaan ke implementasi konkretnya,
 * memungkinkan injeksi dependensi di seluruh aplikasi.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    /**
     * Mengikat [GetProductsUseCaseImpl] ke antarmuka [GetProductsUseCase].
     */
    @Binds
    abstract fun bindGetProductsUseCase(impl: GetProductsUseCaseImpl): GetProductsUseCase

    /**
     * Mengikat [CreateProductUseCaseImpl] ke antarmuka [CreateProductUseCase].
     */
    @Binds
    abstract fun bindCreateProductUseCase(impl: CreateProductUseCaseImpl): CreateProductUseCase

    /**
     * Mengikat [GetProductDetailsUseCaseImpl] ke antarmuka [GetProductDetailsUseCase].
     */
    @Binds
    abstract fun bindGetProductDetailsUseCase(impl: GetProductDetailsUseCaseImpl): GetProductDetailsUseCase

    /**
     * Mengikat [DeleteProductUseCaseImpl] ke antarmuka [DeleteProductUseCase].
     */
    @Binds
    abstract fun bindDeleteProductUseCase(impl: DeleteProductUseCaseImpl): DeleteProductUseCase

    /**
     * Mengikat [UpdateProductUseCaseImpl] ke antarmuka [UpdateProductUseCase].
     */
    @Binds
    abstract fun bindUpdateProductUseCase(impl: UpdateProductUseCaseImpl): UpdateProductUseCase

    /**
     * Mengikat [SignInUseCaseImpl] ke antarmuka [SignInUseCase].
     */
    @Binds
    abstract fun bindAuthenticateUseCase(impl: SignInUseCaseImpl): SignInUseCase

    /**
     * Mengikat [SignUpUseCaseImpl] ke antarmuka [SignUpUseCase].
     */
    @Binds
    abstract fun bindSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    /**
     * Mengikat [SignInWithGoogleUseCaseImpl] ke antarmuka [SignInWithGoogleUseCase].
     */
    @Binds
    abstract fun bindSignInWithGoogleUseCase(impl: SignInWithGoogleUseCaseImpl): SignInWithGoogleUseCase
}