package com.example.suivi_des_employes;


import java.text.SimpleDateFormat;


public class Employe {
    private String nom;
    private String prenom;
    private String tele;
    private String mission;
    private String dateDepart;
    private String dateFin;
    private String latitude;
    private String longitude;

    public Employe() {
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getMission() {
        return mission;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }


}
