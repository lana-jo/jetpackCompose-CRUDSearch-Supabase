package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementasi [SignUpUseCase] untuk registrasi pengguna.
 *
 * @property authenticationRepository Repositori untuk menangani otentikasi.
 */
class SignUpUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignUpUseCase {
    /**
     * Menjalankan proses pendaftaran.
     *
     * @param input Masukan untuk kasus penggunaan, berisi email dan kata sandi pengguna.
     * @return [SignUpUseCase.Output.Success] jika registrasi berhasil,
     * atau [SignUpUseCase.Output.Failure] jika sebaliknya.
     */
    override suspend fun execute(input: SignUpUseCase.Input): SignUpUseCase.Output =
        withContext(Dispatchers.IO) {
            val result = authenticationRepository.signUp(input.email, input.password)
            if (result) {
                SignUpUseCase.Output.Success
            } else {
                SignUpUseCase.Output.Failure
            }
        }
}
