package com.lj.crud_supabase.domain.usecase

/**
 * Kasus penggunaan untuk masuk (sign in) pengguna.
 */
interface SignInUseCase : UseCase<SignInUseCase.Input, SignInUseCase.Output> {

    /**
     * Data masukan untuk [SignInUseCase].
     *
     * @property email Email pengguna.
     * @property password Kata sandi pengguna.
     */
    class Input(val email: String, val password: String)

    /**
     * Mewakili keluaran dari [SignInUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa proses masuk berhasil.
         */
        object Success : Output()

        /**
         * Menunjukkan bahwa proses masuk gagal.
         */
        object Failure : Output()
    }
}