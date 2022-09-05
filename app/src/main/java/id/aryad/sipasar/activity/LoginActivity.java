package id.aryad.sipasar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.aryad.sipasar.LoginManager;
import id.aryad.sipasar.R;

public class LoginActivity extends AppCompatActivity {

    public static final String KIRIM_NAMA ="id.smaraputra.tugaskedua.kirimnama";
    public static final String KIRIM_ROLE ="id.smaraputra.tugaskedua.kirimrole";

    private EditText username, password;
    private Button login;
    private TextView signup;
    private String username_in;
    private String password_in;
    private String status;
    private String usernamesudahlogin, passwordsudahlogin, rolesudahlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("loginasethutang", Context.MODE_PRIVATE);

        //Login Automatis Ketika Buka Aplikasi (Apabila Sudah Login Sebelumnya dan Tidak Melakukan Logout)
        if(!sharedPreferences.getString("username","defaultValue").equals("defaultValue")
                && !sharedPreferences.getString("password","defaultValue").equals("defaultValue")
                && !sharedPreferences.getString("role","defaultValue").equals("defaultValue")) {

            usernamesudahlogin = sharedPreferences.getString("username","defaultValue");
            passwordsudahlogin = sharedPreferences.getString("password","defaultValue");
            rolesudahlogin = sharedPreferences.getString("role","defaultValue");
            Intent intent = new Intent(LoginActivity.this, DashboardManagerActivity.class);
            intent.putExtra(KIRIM_NAMA, usernamesudahlogin);
            intent.putExtra(KIRIM_ROLE, rolesudahlogin);
            startActivity(intent);
        }

        LoginManager managerlogin = new LoginManager();

        username = (EditText) findViewById(R.id.inusername);
        password = (EditText) findViewById(R.id.inpassword);

        signup = (TextView) findViewById(R.id.tombolsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        login = (Button) findViewById(R.id.tombollogin);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username_in = username.getText().toString();
                password_in = password.getText().toString();
                status="";
                status=managerlogin.getStatus(username_in, password_in);

                if(status.equals("DOUBLEEMPTY")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast kurangusernamepassword = Toast.makeText(context, "Username dan Password belum dimasukkan.", duration);
                    kurangusernamepassword.show();
                }else if(status.equals("PASSEMPTY")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast kurangpassword = Toast.makeText(context, "Password belum dimasukkan.", duration);
                    kurangpassword.show();
                }else if(status.equals("USEREMPTY")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast kurangusername = Toast.makeText(context, "Username belum dimasukkan.", duration);
                    kurangusername.show();
                }else if(status.equals("NOTEXIST")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast akuntidakada = Toast.makeText(context, "Akun tidak terdaftar.", duration);
                    akuntidakada.show();
                }else if(status.equals("BADSTATUS")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast statusakunmati = Toast.makeText(context, "Akun tidak aktif.", duration);
                    statusakunmati.show();
                }else if(status.equals("WRONGROLE")){
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast roleakunsalah = Toast.makeText(context, "Anda bukan manager.", duration);
                    roleakunsalah.show();
                }else if(status.equals("SUCCESS")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username_in);
                    editor.putString("password", password_in);
                    editor.putString("role", managerlogin.getRole());
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, DashboardManagerActivity.class);
                    intent.putExtra(KIRIM_NAMA, managerlogin.getNama());
                    intent.putExtra(KIRIM_ROLE, managerlogin.getRole());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    private void showDialog(){
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
                        LoginActivity.this.finishAffinity();
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
}