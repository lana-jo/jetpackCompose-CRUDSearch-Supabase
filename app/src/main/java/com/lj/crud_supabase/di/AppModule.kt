package com.lj.crud_supabase.di

import com.lj.crud_supabase.data.repository.ProductRepositoryImpl
import com.lj.crud_supabase.data.repository.user.AuthRepository
import com.lj.crud_supabase.data.repository.user.AuthRepositoryImpl
import com.lj.crud_supabase.domain.repository.ProductRepository
import com.lj.crud_supabase.domain.usecase.CreateProductUseCase
import com.lj.crud_supabase.domain.usecase.CreateProductUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindCreateProductUseCase(impl: CreateProductUseCaseImpl): CreateProductUseCase

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
