package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignUpUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [SignUpUseCase] for user registration.
 *
 * @property authenticationRepository The repository for handling authentication.
 */
class SignUpUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignUpUseCase {
    /**
     * Executes the sign-up process.
     *
     * @param input The input for the use case, containing the user's email and password.
     * @return [SignUpUseCase.Output.Success] if the registration is successful,
     * or [SignUpUseCase.Output.Failure] otherwise.
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
