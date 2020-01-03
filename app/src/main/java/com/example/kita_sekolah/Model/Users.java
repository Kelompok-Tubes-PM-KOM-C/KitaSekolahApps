package com.example.kita_sekolah.Model;

public class Users {

    private String nama, no_hp, username, email, password;

    public Users(){

    }

    public Users(String nama, String no_hp, String username, String email, String password) {
        this.nama = nama;
        this.username = username;
        this.no_hp = no_hp;
        this.email = email;
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp() {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }




}
