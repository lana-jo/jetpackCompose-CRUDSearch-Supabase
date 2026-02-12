package com.lj.crud_supabase.domain.usecase

/**
 * Mendefinisikan kontrak untuk kasus penggunaan. Kasus penggunaan mewakili tindakan atau operasi tunggal dalam aplikasi.
 *
 * @param <InputT> Jenis data masukan yang diperlukan oleh kasus penggunaan.
 * @param <OutputT> Jenis data keluaran yang dihasilkan oleh kasus penggunaan.
 */
interface UseCase<InputT, OutputT> {

    /**
     * Menjalankan kasus penggunaan dengan masukan yang diberikan.
     *
     * @param input Data masukan untuk kasus penggunaan.
     * @return Keluaran dari kasus penggunaan.
     */
    suspend fun execute(input: InputT): OutputT
}