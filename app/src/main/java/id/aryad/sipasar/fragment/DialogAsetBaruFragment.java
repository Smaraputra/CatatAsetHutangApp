package id.aryad.sipasar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.aryad.sipasar.R;

public class DialogAsetBaruFragment extends AppCompatDialogFragment {

    String TAG = "DialogAsetBaruFragment";

    public interface SimpanDitekanTambah{
        void TambahData (int kategori, int periode, String tanggal, int nilai, String keterangan);
    }
    public SimpanDitekanTambah simpanDitekanTambah;

    private EditText nilaitext;
    private EditText keterangantext;
    private TextView asettext;
    private Button simpan;
    private Button batal;

    private int nilai;
    private int kategoriaset;
    private int periodelaporan;
    private String keterangan;
    private String tanggalcatat;
    private String namaaset;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle1 = getArguments();
        if(bundle1!=null) {
            periodelaporan = bundle1.getInt("idlaporan");
            kategoriaset = bundle1.getInt("idkategori");
            namaaset = bundle1.getString("namakategori");
        }
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        tanggalcatat = df.format(c);

        View view = inflater.inflate(R.layout.popupwindoweditdantambahaset, container, false);
        nilaitext = (EditText) view.findViewById(R.id.inputnilai) ;
        keterangantext = (EditText) view.findViewById(R.id.inputketerangan);
        asettext = (TextView) view.findViewById(R.id.judulasetpop);
        batal = (Button) view.findViewById(R.id.batalinsert);
        simpan = (Button) view.findViewById(R.id.simpaninsert);
        asettext.setText(namaaset);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nilaitext.getText().toString())) {
                    nilaitext.setError("Masukkan Nilai yang Benar!");
                }else{
                    nilai = Integer.parseInt(nilaitext.getText().toString());
                }
                if(TextUtils.isEmpty(keterangantext.getText().toString())){
                    keterangantext.setError("Masukkan Keterangan yang Benar!");

                }else{
                    keterangan = keterangantext.getText().toString();
                }
                if(nilai<=0){
                    nilaitext.setError("Masukkan Nilai yang Benar!");
                }
                if(nilai>0 && !TextUtils.isEmpty(keterangan)){
                    simpanDitekanTambah.TambahData(kategoriaset, periodelaporan, tanggalcatat, nilai, keterangan);
                    getDialog().dismiss();
                }

            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            simpanDitekanTambah = (SimpanDitekanTambah) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastExeption : " + e.getMessage());
        }
    }
}