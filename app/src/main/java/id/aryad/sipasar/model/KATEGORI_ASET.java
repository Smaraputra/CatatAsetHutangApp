package id.aryad.sipasar.model;

public class KATEGORI_ASET {
    public int Id_kategori_aset;
    public String Nama_kategori_aset;
    public String Jenis;

    public KATEGORI_ASET(int Id_kategori_aset_in,
                         String Nama_kategori_aset_in,
                         String Jenis_in) {
        Id_kategori_aset = Id_kategori_aset_in;
        Nama_kategori_aset = Nama_kategori_aset_in;
        Jenis = Jenis_in;
    }

    public int getId_kategori_aset() {
        return Id_kategori_aset;
    }

    public String getNama_kategori_aset() {
        return Nama_kategori_aset;
    }

    public String getJenis() {
        return Jenis;
    }
}
