package id.aryad.sipasar.model;

public class PERIODE_LAPORAN{
    public int Id_periode_laporan;
    public String Nama_periode;
    public String Tanggal_awal_periode;
    public String Tanggal_akhir_periode;
    public int id_laporan_sebelumnya;
    public int uang_kas;

    public PERIODE_LAPORAN(int Id_periode_laporan_in,
                           String Nama_periode_in,
                           String Tanggal_awal_periode_in,
                           String Tanggal_akhir_periode_in,
                           int id_laporan_sebelumnya_in,
                           int uang_kas_in) {
        Id_periode_laporan = Id_periode_laporan_in;
        Nama_periode = Nama_periode_in;
        Tanggal_awal_periode = Tanggal_awal_periode_in;
        Tanggal_akhir_periode = Tanggal_akhir_periode_in;
        id_laporan_sebelumnya = id_laporan_sebelumnya_in;
        uang_kas = uang_kas_in;
    }

    public int getId_periode_laporan() {
        return Id_periode_laporan;
    }

    @Override
    public String toString() {
        return Nama_periode;
    }

    public String getTanggal_awal_periode() {
        return Tanggal_awal_periode;
    }

    public String getTanggal_akhir_periode() {
        return Tanggal_akhir_periode;
    }

    public int getid_laporan_sebelumnya() {
        return id_laporan_sebelumnya;
    }

    public int getuang_kas() {
        return uang_kas;
    }
}
