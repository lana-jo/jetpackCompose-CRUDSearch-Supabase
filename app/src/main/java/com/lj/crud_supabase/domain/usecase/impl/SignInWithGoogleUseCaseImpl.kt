package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignInWithGoogleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementasi dari [SignInWithGoogleUseCase] untuk masuk dengan Google.
 *
 * @property authenticationRepository Repositori untuk menangani otentikasi.
 */
class SignInWithGoogleUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
): SignInWithGoogleUseCase {

    /**
     * Menjalankan proses masuk Google.
     *
     * @param input Masukan untuk kasus penggunaan.
     * @return Sebuah objek [SignInWithGoogleUseCase.Output].
     */
    override suspend fun execute(input: SignInWithGoogleUseCase.Input): SignInWithGoogleUseCase.Output {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signInWithGoogle()
            SignInWithGoogleUseCase.Output()
        }
    }
}
