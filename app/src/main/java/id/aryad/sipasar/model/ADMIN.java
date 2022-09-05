package id.aryad.sipasar.model;

public class ADMIN {
    public int id_admin;
    public int id_pegawai;
    public String username;
    public String password;
    public String role;
    public int status;

    public ADMIN(int id_admin_in,
                 int id_pegawai_in,
                 String username_in,
                 String password_in,
                 String role_in,
                 int status_in) {
        id_admin = id_admin_in;
        id_pegawai = id_pegawai_in;
        username = username_in;
        password = password_in;
        role = role_in;
        status = status_in;
    }



    public int getid_admin() {
        return id_admin;
    }

    public int getid_pegawai() {
        return id_pegawai;
    }

    public String getusername() {
        return username;
    }

    public String getpassword() {
        return password;
    }

    public String getrole() {
        return role;
    }

    public int getstatus() {
        return status;
    }
}
