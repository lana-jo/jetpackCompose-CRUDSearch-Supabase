package com.lj.crud_supabase.domain.usecase.impl

import com.lj.crud_supabase.data.repository.AuthenticationRepository
import com.lj.crud_supabase.domain.usecase.SignInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [SignInUseCase] for user authentication.
 *
 * This class handles the business logic for signing in a user with their email and password.
 * It interacts with the [AuthenticationRepository] to perform the actual sign-in operation.
 *
 * @property authenticationRepository The repository responsible for handling authentication-related data operations.
 */
class SignInUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : SignInUseCase {

    /**
     * Executes the sign-in process with the given user credentials.
     *
     * This suspend function is the main entry point for the use case. It takes the user's email
     * and password as input, and attempts to sign them in using the [authenticationRepository].
     * The operation is performed on the IO dispatcher to avoid blocking the main thread.
     *
     * @param input The input for the use case, containing the user's email and password.
     * @return [SignInUseCase.Output.Success] if the sign-in is successful,
     * or [SignInUseCase.Output.Failure] if the sign-in fails (e.g., incorrect credentials).
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
