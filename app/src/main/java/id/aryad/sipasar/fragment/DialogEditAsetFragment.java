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

public class DialogEditAsetFragment extends AppCompatDialogFragment {

    String TAG = "DialogEditAsetFragment";

    public interface SimpanDitekanEdit{
        void EditData (int kategori, int periode, String tanggal, int nilai, String keterangan, int position, int idneraca);
    }
    public SimpanDitekanEdit simpanDitekanEdit;

    private EditText nilaitext;
    private EditText keterangantext;
    private TextView asettext;
    private Button simpan;
    private Button batal;

    private int position;
    private int idneraca;

    private int nilai;
    private int kategoriaset;
    private int periodelaporan;
    private String keterangan;
    private String tanggalcatat;
    private String namaaset;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle2 = getArguments();
        if(bundle2!=null) {
            periodelaporan = bundle2.getInt("periode");
            idneraca = bundle2.getInt("idneracas");
            position = bundle2.getInt("position");
            namaaset = bundle2.getString("aset");
            nilai = bundle2.getInt("nilai");
            keterangan = bundle2.getString("keterangan");
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
        nilaitext.setText(String.valueOf(nilai));
        keterangantext.setText(keterangan);

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
                    simpanDitekanEdit.EditData(kategoriaset, periodelaporan, tanggalcatat, nilai, keterangan, position, idneraca);
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
            simpanDitekanEdit = (SimpanDitekanEdit) getTargetFragment();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastExeption : " + e.getMessage());
        }
    }
}