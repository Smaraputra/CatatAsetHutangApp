package id.aryad.sipasar.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.aryad.sipasar.R;
import id.aryad.sipasar.activity.DashboardManagerActivity;
import id.aryad.sipasar.activity.LoginActivity;
import id.aryad.sipasar.model.KATEGORI_ASET;
import id.aryad.sipasar.model.NERACA;

public class NeracaAdapter extends RecyclerView.Adapter<NeracaAdapter.MyViewHolder>{

    private ArrayList<KATEGORI_ASET> kategoriasetsorted;
    private ArrayList<NERACA> neracasorted;
    private ArrayList<NERACA> neraca;

    //Format Integer Nilai Jadi Rupiah
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    private Context context;

    public NeracaAdapter(Context ct, ArrayList<NERACA> neracas, ArrayList<NERACA> neracasorteds, ArrayList<KATEGORI_ASET> kategoriasetsorteds){
        this.context = ct;
        this.neraca = neracas;
        this.neracasorted = neracasorteds;
        this.kategoriasetsorted = kategoriasetsorteds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycleraset, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Set text dari arraylist kategori
        holder.aset.setText(kategoriasetsorted.get(position).getNama_kategori_aset());
        holder.kategoriasettext.setText(kategoriasetsorted.get(position).getJenis());

        //Set text dari arraylist neracasorted
        holder.nilaitext.setText(formatRupiah.format((neracasorted.get(position).getNilai())));
        holder.keterangantext.setText(neracasorted.get(position).getKeterangan());
        holder.tanggalcatat.setText(neracasorted.get(position).getTanggal_catat());
    }

    @Override
    public int getItemCount() {
        return neracasorted.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView aset, tanggalcatat;
        TextView nilaitext, keterangantext, kategoriasettext;
        Button hapusaset;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            aset = (TextView) itemView.findViewById(R.id.textnamaaset);
            tanggalcatat = (TextView) itemView.findViewById(R.id.tglcatat);
            nilaitext = (TextView) itemView.findViewById(R.id.TextNilaiAset);
            keterangantext = (TextView) itemView.findViewById(R.id.TextKeterangan);
            kategoriasettext = (TextView) itemView.findViewById(R.id.textjenisaset);
            hapusaset = (Button) itemView.findViewById(R.id.tombolhapusaset);

            itemView.findViewById(R.id.tombolhapusaset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title dialog
                    alertDialogBuilder.setTitle("Apakah anda ingin menghapus data ini?");

                    // set pesan dari dialog
                    alertDialogBuilder
                            .setMessage("Pilih 'IYA' untuk menghapus data.")
                            .setIcon(R.mipmap.ic_launcher)
                            .setCancelable(false)
                            .setPositiveButton("Iya",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // jika tombol diklik, maka akan menghapus data
                                    for(int a = 0; a<neraca.size(); a++){
                                        if(neraca.get(a).getId_neraca()==neracasorted.get(getAdapterPosition()).getId_neraca()){
                                            tombolAdapterDitekan.OperasiAdapter(a, 1);
                                            break;
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // jika tombol ini diklik, akan menutup dialog
                                    // dan tidak terjadi apa2
                                    dialog.cancel();
                                }
                            });

                    // membuat alert dialog dari builder
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // menampilkan alert dialog
                    alertDialog.show();
                }
            });

            itemView.findViewById(R.id.tomboleditaset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int a = 0; a<neraca.size(); a++){
                        if(neraca.get(a).getId_neraca()==neracasorted.get(getAdapterPosition()).getId_neraca()){
                            tombolAdapterDitekan.OperasiAdapter(a, 2);
                            break;
                        }
                    }
                }
            });

            itemView.findViewById(R.id.tomboldetailaset).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int a = 0; a<neraca.size(); a++){
                        if(neraca.get(a).getId_neraca()==neracasorted.get(getAdapterPosition()).getId_neraca()){
                            tombolAdapterDitekan.OperasiAdapter(a, 3);
                            break;
                        }
                    }
                }
            });

        }
    }

    public interface TombolAdapterDitekan {
        void OperasiAdapter(int position, int jenisoperasi);
    }

    TombolAdapterDitekan tombolAdapterDitekan;

    public void setClickEvent(TombolAdapterDitekan event) {
        this.tombolAdapterDitekan = event;
    }
}