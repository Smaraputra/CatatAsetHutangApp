package id.aryad.sipasar.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

import id.aryad.sipasar.R;

public class DialogShowAsetFragment extends AppCompatDialogFragment {

    private TextView idneracatext, kategoriasettext, periodetext, tanggalcatattext, nilaitext, keterangantext;
    private Button batal;

    private int idneraca;
    private int nilai;
    private String periodelaporan;
    private String keterangan;
    private String tanggalcatat;
    private String namaaset;

    //Format Integer Nilai Jadi Rupiah
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle3 = getArguments();
        if(bundle3!=null) {
            periodelaporan = bundle3.getString("periode");
            idneraca = bundle3.getInt("idneracas");
            namaaset = bundle3.getString("aset");
            nilai = bundle3.getInt("nilai");
            keterangan = bundle3.getString("keterangan");
            tanggalcatat = bundle3.getString("tanggal");
        }

        View view = inflater.inflate(R.layout.popupwindowshowaset, container, false);
        batal = (Button) view.findViewById(R.id.button);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        idneracatext = (TextView) view.findViewById(R.id.isiidneraca);
        kategoriasettext = (TextView) view.findViewById(R.id.isikategoriaset);
        periodetext = (TextView) view.findViewById(R.id.isiperiode);
        tanggalcatattext = (TextView) view.findViewById(R.id.isitanggalcatat);
        nilaitext = (TextView) view.findViewById(R.id.isinilai);
        keterangantext = (TextView) view.findViewById(R.id.isiketerangan);

        idneracatext.setText(String.valueOf(idneraca));
        kategoriasettext.setText(namaaset);
        periodetext.setText(periodelaporan);
        tanggalcatattext.setText(tanggalcatat);
        nilaitext.setText(formatRupiah.format(nilai));
        keterangantext.setText(keterangan);

        return view;
    }

}