package com.lj.crud_supabase.domain.usecase

/**
 * Kasus penggunaan untuk mendaftarkan (sign up) pengguna baru.
 */
interface SignUpUseCase: UseCase<SignUpUseCase.Input, SignUpUseCase.Output> {

    /**
     * Data masukan untuk [SignUpUseCase].
     *
     * @property email Email pengguna.
     * @property password Kata sandi pengguna.
     */
    class Input(val email: String, val password: String)

    /**
     * Mewakili keluaran dari [SignUpUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa proses pendaftaran berhasil.
         */
        object Success: Output()

        /**
         * Menunjukkan bahwa proses pendaftaran gagal.
         */
        object Failure: Output()
    }
}