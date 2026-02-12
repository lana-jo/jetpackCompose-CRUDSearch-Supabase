package com.lj.crud_supabase.domain.usecase

/**
 * Kasus penggunaan untuk memperbarui produk yang sudah ada.
 */
interface UpdateProductUseCase : UseCase<UpdateProductUseCase.Input, UpdateProductUseCase.Output> {

    /**
     * Data masukan untuk [UpdateProductUseCase].
     *
     * @property id ID produk yang akan diperbarui.
     * @property name Nama baru produk.
     * @property price Harga baru produk.
     * @property imageName Nama file gambar baru.
     * @property imageFile Data byte dari file gambar baru.
     */
    class Input(
        val id: String,
        val name: String,
        val price: Double,
        val imageName: String,
        val imageFile: ByteArray,
    )

    /**
     * Mewakili keluaran dari [UpdateProductUseCase].
     */
    sealed class Output() {
        /**
         * Menunjukkan bahwa produk berhasil diperbarui.
         */
        object Success : Output()

        /**
         * Menunjukkan bahwa pembaruan produk gagal.
         */
        object Failure : Output()
    }
}