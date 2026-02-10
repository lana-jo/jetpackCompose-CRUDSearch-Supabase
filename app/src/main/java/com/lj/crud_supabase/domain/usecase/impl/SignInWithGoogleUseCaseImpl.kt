package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignInWithGoogleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [SignInWithGoogleUseCase] for signing in with Google.
 *
 * @property authenticationRepository The repository for handling authentication.
 */
class SignInWithGoogleUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
): SignInWithGoogleUseCase {

    /**
     * Executes the Google sign-in process.
     *
     * @param input The input for the use case.
     * @return A [SignInWithGoogleUseCase.Output] object.
     */
    override suspend fun execute(input: SignInWithGoogleUseCase.Input): SignInWithGoogleUseCase.Output {
        return withContext(Dispatchers.IO) {
            authenticationRepository.signInWithGoogle()
            SignInWithGoogleUseCase.Output()
        }
    }
}
