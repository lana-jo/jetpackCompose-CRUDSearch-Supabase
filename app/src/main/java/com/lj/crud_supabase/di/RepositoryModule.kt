package com.lj.crud_supabase.di

import com.lj.crud_supabase.data.repository.AuthRepo
import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.data.repository.ProductRepository
import com.lj.crud_supabase.data.repository.impl.AuthRepoImpl
import com.lj.crud_supabase.data.repository.impl.AuthenticationRepositoryImpl
import com.lj.crud_supabase.data.repository.impl.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Modul Hilt untuk menyediakan implementasi repositori.
 *
 * Modul ini bertanggung jawab untuk mengikat antarmuka repositori ke implementasi konkretnya,
 * memungkinkan injeksi dependensi di seluruh aplikasi.
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    /**
     * Mengikat [ProductRepositoryImpl] ke antarmuka [ProductRepository].
     *
     * @param impl Implementasi dari [ProductRepository].
     * @return Sebuah instance dari [ProductRepository].
     */
    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    /**
     * Mengikat [AuthenticationRepositoryImpl] ke antarmuka [AuthenticationRepository].
     *
     * Anotasi `@Singleton` memastikan bahwa hanya ada satu instance dari [AuthenticationRepository]
     * yang dibuat dan digunakan di seluruh aplikasi.
     *
     * @param impl Implementasi dari [AuthenticationRepository].
     * @return Instance tunggal dari [AuthenticationRepository].
 */
    @Binds
    @Singleton
    abstract fun bindAuthenticateRepository(impl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepoImpl
    ): AuthRepo

}