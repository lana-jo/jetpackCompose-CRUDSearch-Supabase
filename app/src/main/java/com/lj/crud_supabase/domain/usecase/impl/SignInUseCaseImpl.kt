package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementasi [SignInUseCase] untuk otentikasi pengguna.
 *
 * Kelas ini menangani logika bisnis untuk masuk (sign in) pengguna dengan email dan kata sandi mereka.
 * Ini berinteraksi dengan [AuthenticationRepository] untuk melakukan operasi masuk yang sebenarnya.
 *
 * @property authenticationRepository Repositori yang bertanggung jawab untuk menangani operasi data terkait otentikasi.
 */
class SignInUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignInUseCase {

    /**
     * Menjalankan proses masuk dengan kredensial pengguna yang diberikan.
     *
     * Fungsi penangguhan (suspend function) ini adalah titik masuk utama untuk kasus penggunaan. Ini mengambil email
     * dan kata sandi pengguna sebagai masukan, dan mencoba untuk masuk menggunakan [authenticationRepository].
     * Operasi dilakukan pada dispatcher IO untuk menghindari pemblokiran thread utama.
     *
     * @param input Masukan untuk kasus penggunaan, berisi email dan kata sandi pengguna.
     * @return [SignInUseCase.Output.Success] jika proses masuk berhasil,
     * atau [SignInUseCase.Output.Failure] jika proses masuk gagal (misalnya, kredensial salah).
     */
    override suspend fun execute(input: SignInUseCase.Input): SignInUseCase.Output {
        return withContext(Dispatchers.IO) {
            val result = authenticationRepository.signIn(input.email, input.password)
            if (result) {
                SignInUseCase.Output.Success
            } else {
                SignInUseCase.Output.Failure
            }
        }
    }
}
