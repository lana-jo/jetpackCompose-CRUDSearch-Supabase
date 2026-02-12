package com.lj.crud_supabase.domain.usecase

import com.lj.crud_supabase.domain.model.Product

/**
 * Kasus penggunaan untuk mengambil detail suatu produk.
 */
interface GetProductDetailsUseCase :
    UseCase<GetProductDetailsUseCase.Input, GetProductDetailsUseCase.Output> {

    /**
     * Data masukan untuk [GetProductDetailsUseCase].
     *
     * @property id ID produk yang akan diambil detailnya.
     */
    class Input(val id: String)

    /**
     * Mewakili keluaran dari [GetProductDetailsUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa detail produk berhasil diambil.
         *
         * @property data Detail produk.
         */
        class Success(val data: Product): Output()

        /**
         * Menunjukkan bahwa terjadi kesalahan saat mengambil detail produk.
         */
        object Failure : Output()
    }
}