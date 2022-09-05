package id.aryad.sipasar.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import id.aryad.sipasar.R;
import id.aryad.sipasar.fragment.CatatFragment;
import id.aryad.sipasar.fragment.ProfilFragment;

public class DashboardManagerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DashboardManagerActivity";

    DrawerLayout drawerLayout;
    TextView viewname;
    TextView viewrole;
    String nama, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardmanager);
        drawerLayout = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        nama = intent.getStringExtra(LoginActivity.KIRIM_NAMA);
        role = intent.getStringExtra(LoginActivity.KIRIM_ROLE);

        viewname = headerView.findViewById(R.id.viewnama);
        viewrole = headerView.findViewById(R.id.viewrole);

        viewname.setText(nama);
        viewrole.setText(role);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(DashboardManagerActivity.this, drawerLayout, toolbar, R.string.buka, R.string.tutup);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.tempatmunculfragment, new CatatFragment()).commit();
            navigationView.setCheckedItem(R.id.catat);
        }

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if((drawerLayout.isDrawerOpen(GravityCompat.START)!=true)){
            showDialogKeluar();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.catat:
                getSupportFragmentManager().beginTransaction().replace(R.id.tempatmunculfragment, new CatatFragment(), "CatatFragment").commit();
                break;
            case R.id.profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.tempatmunculfragment, new ProfilFragment()).commit();
                break;
            case R.id.logout:
                showDialogLogout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogKeluar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set title dialog
        alertDialogBuilder.setTitle("Apakah anda ingin keluar dari aplikasi?");

        // Set pesan dari dialog
        alertDialogBuilder
                .setMessage("Pilih 'Keluar' untuk keluar dari aplikasi.")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Keluar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Jika tombol diklik, maka akan menutup activity ini
                        DashboardManagerActivity.this.finishAffinity();
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Jika tombol ini diklik, akan menutup dialog
                        dialog.cancel();
                    }
                });

        // Membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Menampilkan alert dialog
        alertDialog.show();
    }

    private void showDialogLogout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // Set title dialog
        alertDialogBuilder.setTitle("Apakah anda yakin ingin logout?");

        // Set pesan dari dialog
        alertDialogBuilder
                .setMessage("Pilih 'IYA' untuk logout.")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("IYA",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Jika tombol diklik, maka akan melakukan logout
                        SharedPreferences sharedPreferences = getSharedPreferences("loginasethutang", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(DashboardManagerActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Jika tombol ini diklik, akan menutup dialog
                        dialog.cancel();
                    }
                });

        // Membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Menampilkan alert dialog
        alertDialog.show();
    }
}