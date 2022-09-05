package id.aryad.sipasar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import id.aryad.sipasar.model.ADMIN;

public class LoginManager {
        public String status;
        public ArrayList<ADMIN> admin = new ArrayList<>();
        public String nama;
        public String pass;
        public String role;

        public String getStatus(String username, String password){
                status="";

                admin.add(new ADMIN(1, 1, "aryapegawai", "pass123", "PEGAWAI", 1));
                admin.add(new ADMIN(2, 2, "aryaadmin", "pass123", "ADMIN", 1));
                admin.add(new ADMIN(3, 3, "aryamanager", "pass123", "MANAGER", 1));
                admin.add(new ADMIN(4, 4, "aryapegawainonaktif", "pass123", "PEGAWAI", 0));
                admin.add(new ADMIN(5, 5, "aryaadminnonaktif", "pass123", "ADMIN", 0));
                admin.add(new ADMIN(6, 6, "aryamanagernonaktif", "pass123", "MANAGER", 0));

                if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                        status="DOUBLEEMPTY";
                }else if (TextUtils.isEmpty(username)){
                        status="USEREMPTY";
                }else if(TextUtils.isEmpty(password)){
                        status="PASSEMPTY";
                }else{
                        for (int a = 0; a<admin.size(); a++) {
                                if (username.equals(admin.get(a).getusername()) && password.equals(admin.get(a).getpassword())){
                                        if(admin.get(a).getrole().equals("MANAGER")){
                                                if(admin.get(a).getstatus() == 1){
                                                        status="SUCCESS";
                                                        role=admin.get(a).getrole();
                                                        pass=admin.get(a).getpassword();
                                                        nama=admin.get(a).getusername();
                                                        Log.d("Login", status);
                                                        break;
                                                }else{
                                                        status="BADSTATUS";
                                                        Log.d("Login", status);
                                                }
                                                break;
                                        }else{
                                                status="WRONGROLE";
                                                Log.d("Login", status);
                                        }
                                        break;
                                }else{
                                        if(a==admin.size()-1){
                                                status="NOTEXIST";
                                                Log.d("Login", status);
                                        }
                                }
                        }
                }
                return status;
        }
        public String getNama(){
                return nama;
        }
        public String getPass(){
                return pass;
        }
        public String getRole(){
                return role;
        }
}
