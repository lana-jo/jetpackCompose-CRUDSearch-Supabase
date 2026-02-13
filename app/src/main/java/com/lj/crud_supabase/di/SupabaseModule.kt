package com.lj.crud_supabase.di

import com.lj.crud_supabase.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import javax.inject.Singleton


/**
 * Modul Hilt untuk menyediakan dependensi terkait Supabase.
 *
 * Modul ini bertanggung jawab untuk membuat dan mengonfigurasi `SupabaseClient`
 * dan menyediakan akses ke berbagai fiturnya seperti Postgrest, Auth, dan Storage.
 */
@InstallIn(SingletonComponent::class)
@Module
object SupabaseModule {

    /**
     * Menyediakan instance tunggal dari [SupabaseClient].
     *
     * Klien dikonfigurasi dengan URL dan kunci API dari `BuildConfig`.
     * Klien ini juga menginstal plugin Postgrest, Auth, dan Storage.
     *
     * @return Instance [SupabaseClient] yang telah dikonfigurasi.
     */
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.BASE_URL,
            supabaseKey = BuildConfig.API_KEY
        ) {
            install(Postgrest)
            install(Auth) {
                flowType = FlowType.PKCE
                scheme = "app"
                host = "supabase.com"
            }
            install(Storage)

        }
    }

    /**
     * Menyediakan instance [Postgrest] dari [SupabaseClient].
     * Digunakan untuk berinteraksi dengan database Supabase.
     *
     * @param client [SupabaseClient] yang telah dikonfigurasi.
     * @return Instance [Postgrest].
     */
    @Provides
    @Singleton
    fun provideSupabaseDatabase(client: SupabaseClient): Postgrest {
        return client.postgrest
    }

    /**
     * Menyediakan instance [Auth] dari [SupabaseClient].
     * Digunakan untuk otentikasi pengguna.
     *
     * @param client [SupabaseClient] yang telah dikonfigurasi.
     * @return Instance [Auth].
     */
    @Provides
    @Singleton
    fun provideSupabaseGoTrue(client: SupabaseClient): Auth {
        return client.auth
    }


    /**
     * Menyediakan instance [Storage] dari [SupabaseClient].
     * Digunakan untuk mengelola penyimpanan file.
     *
     * @param client [SupabaseClient] yang telah dikonfigurasi.
     * @return Instance [Storage].
     */
    @Provides
    @Singleton
    fun provideSupabaseStorage(client: SupabaseClient): Storage {
        return client.storage
    }

    fun getImageUrl(filePath: String): String {
        return "${BuildConfig.BASE_URL}/storage/v1/object/public/Product Image/$filePath"
    }
}