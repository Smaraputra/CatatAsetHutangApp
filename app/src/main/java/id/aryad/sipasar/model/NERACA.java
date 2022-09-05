package id.aryad.sipasar.model;

import android.app.Application;

import java.util.ArrayList;

public class NERACA extends Application {
    public int Id_neraca;
    public int Id_kategori_aset;
    public int Id_periode_laporan;
    public String Tanggal_catat;
    public int Nilai;
    public String Keterangan;

    public NERACA(int Id_neraca_in,
                  int Id_kategori_aset_in,
                  int Id_periode_laporan_in,
                  String Tanggal_catat_in,
                  int Nilai_in,
                  String Keterangan_in) {
        Id_neraca = Id_neraca_in;
        Id_kategori_aset = Id_kategori_aset_in;
        Id_periode_laporan = Id_periode_laporan_in;
        Tanggal_catat = Tanggal_catat_in;
        Nilai = Nilai_in;
        Keterangan = Keterangan_in;
    }

    public int getId_neraca() {
        return Id_neraca;
    }

    public int getId_kategori_aset() {
        return Id_kategori_aset;
    }

    public int getId_periode_laporan() {
        return Id_periode_laporan;
    }

    public String getTanggal_catat() {
        return Tanggal_catat;
    }

    public int getNilai() {
        return Nilai;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setNilai(int nilaiupdate){
        this.Nilai = nilaiupdate;
    }

    public void setKeterangan(String keteranganupdate){
        this.Keterangan = keteranganupdate;
    }

    public void setTanggal_catat(String tanggalcatatupdate){
        this.Tanggal_catat = tanggalcatatupdate;
    }
}
