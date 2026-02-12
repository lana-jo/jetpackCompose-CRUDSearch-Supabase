package com.lj.crud_supabase.domain.usecase

/**
 * Kasus penggunaan untuk masuk (sign in) dengan Google.
 */
interface SignInWithGoogleUseCase: UseCase<SignInWithGoogleUseCase.Input, SignInWithGoogleUseCase.Output> {

    /**
     * Data masukan untuk [SignInWithGoogleUseCase]. Kelas ini kosong karena alur masuk Google
     * ditangani oleh pustaka Google Sign-In dan tidak memerlukan masukan eksplisit di sini.
     */
    class Input

    /**
     * Mewakili keluaran dari [SignInWithGoogleUseCase]. Saat ini kosong,
     * tetapi dapat diperluas untuk menyertakan status keberhasilan atau kegagalan.
     */
    class Output
}