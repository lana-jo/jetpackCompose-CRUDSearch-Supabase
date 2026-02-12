package com.lj.crud_supabase.domain.usecase

import com.lj.crud_supabase.domain.model.Product

/**
 * Kasus penggunaan untuk membuat produk baru.
 */
interface CreateProductUseCase : UseCase<CreateProductUseCase.Input, CreateProductUseCase.Output> {

    /**
     * Data masukan untuk [CreateProductUseCase].
     *
     * @property product Produk yang akan dibuat.
     */
    class Input(val product: Product)

    /**
     * Mewakili keluaran dari [CreateProductUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa produk berhasil dibuat.
         *
         * @property result Hasil dari operasi pembuatan.
         */
        class Success(val result: Boolean) : Output()

        /**
         * Menunjukkan bahwa terjadi kesalahan saat membuat produk.
         */
        open class Failure : Output() {
            /**
             * Menunjukkan konflik, misalnya, produk sudah ada.
             */
            object Conflict : Failure()

            /**
             * Menunjukkan bahwa operasi tidak diizinkan.
             */
            object Unauthorized : Failure()

            /**
             * Menunjukkan permintaan yang salah.
             */
            object BadRequest : Failure()

            /**
             * Menunjukkan kesalahan internal server.
             */
            object InternalError : Failure()
        }
    }
}