package id.aryad.sipasar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.aryad.sipasar.R;
import id.aryad.sipasar.adapter.NeracaAdapter;
import id.aryad.sipasar.model.KATEGORI_ASET;
import id.aryad.sipasar.model.NERACA;
import id.aryad.sipasar.model.PERIODE_LAPORAN;

public class CatatFragment extends Fragment implements DialogAsetBaruFragment.SimpanDitekanTambah, DialogEditAsetFragment.SimpanDitekanEdit, NeracaAdapter.TombolAdapterDitekan{

    private ArrayList<PERIODE_LAPORAN> periode;

    private ArrayList<KATEGORI_ASET> kategoriaset;
    private ArrayList<KATEGORI_ASET> kategoriasetsorted;

    private ArrayList<NERACA> neraca;
    private ArrayList<NERACA> neracasorted;

    private RecyclerView recyclerViewShowEdit;
    private NeracaAdapter neracaAdapter;

    private Spinner spinnerPeriode;
    private Button tambahasetbarudalamperiode;

    private String pilihanperiode;
    private String namakategori;
    private int idperiodelaporan;
    private int idkategori;

    private TextView showkosongtext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.catatfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //Set New Arraylist
        periode = new ArrayList<>();

        kategoriaset = new ArrayList<>();
        kategoriasetsorted = new ArrayList<>();

        neraca = new ArrayList<>();
        neracasorted = new ArrayList<>();

        showkosongtext=(TextView) view.findViewById(R.id.showkosong);
        buatData();

        //Spinner
        spinnerPeriode = (Spinner) view.findViewById(R.id.spinnerPeriode);
        ArrayAdapter<PERIODE_LAPORAN> spinneradapter = new ArrayAdapter<PERIODE_LAPORAN>(getContext(), android.R.layout.simple_spinner_dropdown_item, periode);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriode.setAdapter(spinneradapter);

        tambahasetbarudalamperiode = (Button) view.findViewById(R.id.tomboltambahasetpadaperiode);
        PopupMenu popupmenuaset = new PopupMenu(getContext(), tambahasetbarudalamperiode);

        //Menambahkan pilihan dropdown berdasarkan kategori aset
        for(int r = 0; r<kategoriaset.size(); r++){
            popupmenuaset.getMenu().add(Menu.NONE, r, r, kategoriaset.get(r).getNama_kategori_aset());
        }

        //OnClick Memunculkan Dropdown Menu
        tambahasetbarudalamperiode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupmenuaset.show();
            }
        });

        spinnerPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Membaca pilihan periode
                pilihanperiode = spinnerPeriode.getSelectedItem().toString();

                //Mencari id_periode berdasarkan dari pilihan periode
                for(int a = 0; a < periode.size(); a++) {
                   if (pilihanperiode.equals(periode.get(a).toString())){
                       idperiodelaporan = periode.get(a).getId_periode_laporan();
                       sortData();
                       break;
                   }
                }
                mulaiAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        popupmenuaset.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Membaca pilihan periode
                pilihanperiode = spinnerPeriode.getSelectedItem().toString();
                int pilihanaset = item.getItemId()+1;

                //Mencari id_periode berdasarkan dari pilihan periode
                for(int a = 0; a < periode.size(); a++) {
                    if (pilihanperiode.equals(periode.get(a).toString())) {
                        idperiodelaporan = periode.get(a).getId_periode_laporan();
                    }
                }

                //Mencari id_kategori dan namanya berdasarkan dari pilihan user
                for(int b = 0; b<kategoriaset.size(); b++){
                    if (pilihanaset==kategoriaset.get(b).getId_kategori_aset()){
                        idkategori = kategoriaset.get(b).getId_kategori_aset();
                        namakategori = kategoriaset.get(b).getNama_kategori_aset();

                        //Passing data ke customdialog
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("namakategori", namakategori);
                        bundle1.putInt("idlaporan", idperiodelaporan);
                        bundle1.putInt("idkategori", idkategori);

                        DialogAsetBaruFragment dialogAsetBaruFragment = new DialogAsetBaruFragment();
                        dialogAsetBaruFragment.setArguments(bundle1);
                        dialogAsetBaruFragment.setTargetFragment(CatatFragment.this, 1);
                        dialogAsetBaruFragment.show(getFragmentManager(), "DialogTambahAset");
                    }
                }
                return false;
            }
        });
    }

    public void mulaiAdapter(){
        recyclerViewShowEdit = (RecyclerView) getView().findViewById(R.id.recylerviewaset);
        neracaAdapter = new NeracaAdapter(getContext(), neraca, neracasorted, kategoriasetsorted);
        neracaAdapter.setClickEvent((NeracaAdapter.TombolAdapterDitekan) this);
        recyclerViewShowEdit.setAdapter(neracaAdapter);
        recyclerViewShowEdit.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void TambahData(int kategori, int periode, String tanggal, int nilai, String keterangan) {
        //Mengecek Perubahan Jumlah Data
        Log.d("Data Sebelum (ADD)", String.valueOf(neraca.size()));

        //Event Ketika Neraca Ditambah
        int idbaru = 0;
        if(neraca.size()==0){
            idbaru = 1;
        }else{
            idbaru = neraca.get(neraca.size()-1).getId_neraca()+1;
        }
        neraca.add(new NERACA(idbaru, kategori, periode, tanggal, nilai, keterangan));
        sortData();
        neracaAdapter.notifyDataSetChanged();

        //Toast Add
        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast tambahdata = Toast.makeText(context, "Data berhasil ditambahkan.", duration);
        tambahdata.show();

        //Mengecek Perubahan Jumlah Data
        Log.d("Data Sesudah (ADD)", String.valueOf(neraca.size()));
    }

    @Override
    public void EditData(int kategori, int periode, String tanggal, int nilai, String keterangan, int position, int idneraca) {
        //Mengecek Perubahan Jumlah Data
        Log.d("Data Sebelum (EDIT)", String.valueOf(neraca.get(position).getNilai()));

        //Event Ketika Neraca Diedit
        for(int a = 0; a<neraca.size(); a++){
            if(neraca.get(a).getId_neraca()==idneraca){
                neraca.get(a).setNilai(nilai);
                neraca.get(a).setKeterangan(keterangan);
                neraca.get(a).setTanggal_catat(tanggal);
            }
        }
        sortData();
        neracaAdapter.notifyDataSetChanged();

        //Toast Edit
        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast editdata = Toast.makeText(context, "Data berhasil diedit.", duration);
        editdata.show();

        //Mengecek Perubahan Jumlah Data
        Log.d("Data Sesudah (EDIT)", String.valueOf(neraca.get(position).getNilai()));
    }

    @Override
    public void OperasiAdapter(int position, int jenisoperasi) {

        //Mengecek Perubahan Jumlah Data
        Log.d("Data Sebelum (REMOVE)", String.valueOf(neraca.size()));

        //Event Ketika Tombol Hapus Neraca Di Adapter Ditekan
        if(jenisoperasi==1){

            neraca.remove(position);
            sortData();
            neracaAdapter.notifyDataSetChanged();

            //Toast Delete
            Context context = getContext();
            int duration = Toast.LENGTH_SHORT;
            Toast deletedata = Toast.makeText(context, "Data berhasil dihapus.", duration);
            deletedata.show();

            //Mengecek Perubahan Jumlah Data
            Log.d("Data Sesudah (REMOVE)", String.valueOf(neraca.size()));

        //Event Ketika Tombol Edit Neraca Di Adapter Ditekan
        }else if(jenisoperasi==2){
            Bundle bundle2 = new Bundle();
            for(int a = 0; a<kategoriaset.size(); a++){
                if(neraca.get(position).getId_kategori_aset() == kategoriaset.get(a).getId_kategori_aset()) {
                    bundle2.putString("aset", kategoriaset.get(a).getNama_kategori_aset());
                    bundle2.putInt("nilai", neraca.get(position).getNilai());
                    bundle2.putString("keterangan", neraca.get(position).getKeterangan());
                    bundle2.putInt("periode", neraca.get(position).getId_periode_laporan());
                    bundle2.putInt("position", position);
                    bundle2.putInt("idneracas", neraca.get(position).getId_neraca());
                    break;
                }
            }

            DialogEditAsetFragment dialogEditAsetFragment = new DialogEditAsetFragment();
            dialogEditAsetFragment.setArguments(bundle2);
            dialogEditAsetFragment.setTargetFragment(CatatFragment.this, 2);
            dialogEditAsetFragment.show(getFragmentManager(), "DialogEditAset");

        //Event Ketika Tombol Show Neraca Di Adapter Ditekan
        }else if(jenisoperasi==3){
            Bundle bundle3 = new Bundle();
            for(int a = 0; a<kategoriaset.size(); a++){
                if(neraca.get(position).getId_kategori_aset() == kategoriaset.get(a).getId_kategori_aset()) {
                    bundle3.putString("aset", kategoriaset.get(a).getNama_kategori_aset());
                    bundle3.putInt("nilai", neraca.get(position).getNilai());
                    bundle3.putString("keterangan", neraca.get(position).getKeterangan());
                    bundle3.putString("tanggal", neraca.get(position).getTanggal_catat());
                    bundle3.putInt("idneracas", neraca.get(position).getId_neraca());
                    break;
                }
            }

            for(int x = 0; x<periode.size(); x++){
                if(neraca.get(position).getId_periode_laporan() == periode.get(x).getId_periode_laporan()) {
                    bundle3.putString("periode", periode.get(x).toString());
                    break;
                }
            }

            DialogShowAsetFragment dialogShowAsetFragment = new DialogShowAsetFragment();
            dialogShowAsetFragment.setArguments(bundle3);
            dialogShowAsetFragment.show(getFragmentManager(), "DialogShowAset");
        }
    }

    private void buatData(){
        //Clear Data
        kategoriaset.clear();
        neraca.clear();
        periode.clear();
        neracasorted.clear();
        kategoriasetsorted.clear();

        //Insert Data Dummy
        //Kategori
        kategoriaset.add(new KATEGORI_ASET(1, "Tanah", "Aset"));
        kategoriaset.add(new KATEGORI_ASET(2, "Bangunan", "Aset"));
        kategoriaset.add(new KATEGORI_ASET(3, "Hutang Bank", "Hutang"));
        kategoriaset.add(new KATEGORI_ASET(4, "Mobil", "Aset"));
        kategoriaset.add(new KATEGORI_ASET(5, "Piutang Bank", "Aset"));

        //Neraca
        neraca.add(new NERACA(1, 1, 1, "05-01-2021", 1500000, "Tanah di denpasar"));
        neraca.add(new NERACA(2, 1, 2, "05-04-2021", 2500000, "Tanah di tabanan"));
        neraca.add(new NERACA(3, 3, 2, "05-04-2021", 1500000, "Hutang beli tanah"));
        neraca.add(new NERACA(4, 2, 3, "05-08-2021", 1000000, "Bangunan di badung"));
        neraca.add(new NERACA(5, 3, 3, "05-08-2021", 500000, "Hutang membangun gedung"));

        //Periode
        periode.add(new PERIODE_LAPORAN(1, "Kuartal 1 2021", "01-01-2021 00:00:01", "31-03-2021 23:59:59", 0, 100000));
        periode.add(new PERIODE_LAPORAN(2, "Kuartal 2 2021", "01-04-2021 00:00:01", "30-06-2021 23:59:59", 1, 200000));
        periode.add(new PERIODE_LAPORAN(3, "Kuartal 3 2021", "01-07-2021 00:00:01", "30-09-2021 23:59:59", 2, 300000));
        periode.add(new PERIODE_LAPORAN(4, "Kuartal 4 2021", "01-10-2021 00:00:01", "31-12-2021 23:59:59", 3, 400000));
    }

    public void sortData(){
        //Reset setiap dipanggil
        neracasorted.clear();
        kategoriasetsorted.clear();

        //Menyortir data neraca berdasarkan pilihan periode
        for(int i = 0; i < neraca.size(); i++) {
            if (neraca.get(i).getId_periode_laporan() == idperiodelaporan) {
                neracasorted.add(new NERACA(
                        neraca.get(i).getId_neraca()
                        , neraca.get(i).getId_kategori_aset()
                        , neraca.get(i).getId_periode_laporan()
                        , neraca.get(i).getTanggal_catat()
                        , neraca.get(i).getNilai()
                        , neraca.get(i).getKeterangan()));

                //Menyortir data kategori berdasarkan neraca yang tersortir diatas
                for (int x = 0; x < kategoriaset.size(); x++) {
                    if (kategoriaset.get(x).getId_kategori_aset() == neraca.get(i).getId_kategori_aset()) {
                        kategoriasetsorted.add(new KATEGORI_ASET(
                                kategoriaset.get(x).getId_kategori_aset()
                                , kategoriaset.get(x).getNama_kategori_aset()
                                , kategoriaset.get(x).getJenis()));

                    }
                }
            }
        }
        if(neracasorted.size()==0){
            showkosongtext.setVisibility(View.VISIBLE);
        }else{
            showkosongtext.setVisibility(View.GONE);
        }
    }
}