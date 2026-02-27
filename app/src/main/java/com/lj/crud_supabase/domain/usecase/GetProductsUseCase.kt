package com.lj.crud_supabase.domain.usecase

import com.lj.crud_supabase.domain.model.Product

/**
 * Kasus penggunaan untuk mengambil daftar produk.
 * Kasus penggunaan ini tidak memerlukan parameter masukan apa pun.
 */
interface GetProductsUseCase : UseCase<Unit, GetProductsUseCase.Output> {

    /**
     * Mewakili keluaran dari [GetProductsUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa produk berhasil diambil.
         * @property data Daftar produk.
         */
        class Success(val data: List<Product>): Output()

        /**
         * Menunjukkan bahwa terjadi kesalahan saat mengambil produk.
         */
        object Failure : Output()
    }

}