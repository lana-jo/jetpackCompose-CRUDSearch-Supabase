package com.lj.crud_supabase.domain.usecase

/**
 * Kasus penggunaan untuk menghapus produk.
 */
interface DeleteProductUseCase: UseCase<DeleteProductUseCase.Input, DeleteProductUseCase.Output> {

    /**
     * Data masukan untuk [DeleteProductUseCase].
     *
     * @property productId ID produk yang akan dihapus.
     */
    class Input(val productId: String)

    /**
     * Mewakili keluaran dari [DeleteProductUseCase].
     */
    sealed class Output {
        /**
         * Menunjukkan bahwa produk berhasil dihapus.
         */
        object Success: Output()
    }
}