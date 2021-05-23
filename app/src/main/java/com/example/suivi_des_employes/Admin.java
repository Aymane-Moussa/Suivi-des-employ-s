package com.example.suivi_des_employes;

public class Admin {

    private String fullname;
    private String email;
    private String profession;
    private String image;

    public Admin(String fullname, String email, String profession, String image) {
        this.fullname = fullname;
        this.email = email;
        this.profession = profession;
        this.image = image;
    }

    public Admin() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
