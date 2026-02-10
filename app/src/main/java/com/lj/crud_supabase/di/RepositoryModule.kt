package com.lj.crud_supabase.di

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.data.repository.impl.AuthenticationRepositoryImpl
import com.lj.crud_supabase.data.repository.impl.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindAuthenticateRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

}