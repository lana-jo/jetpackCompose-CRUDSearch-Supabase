package com.lj.crud_supabase.data.repository

import com.lj.crud_supabase.domain.model.AuthState
import kotlinx.coroutines.flow.StateFlow

/**
 * Mendefinisikan kontrak untuk repositori yang menangani operasi otentikasi.
 */
interface AuthenticationRepository {
    /**
     * Aliran (Flow) yang memancarkan status otentikasi saat ini.
     * Ini memungkinkan pengamat untuk bereaksi terhadap perubahan dalam status otentikasi pengguna (misalnya, masuk, keluar).
     */
    val authState: StateFlow<AuthState>

    /**
     * Mengotentikasi pengguna dengan email dan kata sandi mereka.
     *
     * @param email Email pengguna.
     * @param password Kata sandi pengguna.
     * @return `true` jika proses masuk berhasil, `false` jika tidak.
     */
    suspend fun signIn(email: String, password: String): Boolean

    /**
     * Mendaftarkan pengguna baru dengan email dan kata sandi.
     *
     * @param email Email pengguna.
     * @param password Kata sandi pengguna.
     * @return `true` jika pendaftaran berhasil, `false` jika tidak.
     */
    suspend fun signUp(email: String, password: String): Boolean

//    suspend fun signUp(email: String, password: String): Result<Unit>
    /**
     * Memulai alur masuk dengan Google.
     *
     * @return `true` jika alur masuk Google berhasil dimulai, `false` jika tidak.
     */
    suspend fun signInWithGoogle(): Boolean

    /**
     * Menukarkan kode otentikasi dengan sesi pengguna.
     *
     * @param code Kode otentikasi yang diterima setelah alur otentikasi eksternal (misalnya, OAuth).
     * @return [Result] yang menunjukkan keberhasilan atau kegagalan operasi.
     */
    suspend fun exchangeCodeForSession(code: String): Result<Unit>

    /**
     * Memverifikasi email pengguna menggunakan token hash.
     *
     * @param tokenHash Token hash yang dikirim ke email pengguna untuk verifikasi.
     * @return [Result] yang menunjukkan keberhasilan atau kegagalan operasi.
     */
    suspend fun verifyEmail(tokenHash: String): Result<Unit>

    /**
     * Mengeluarkan pengguna dari sesi saat ini.
     */
    suspend fun signOut()
}