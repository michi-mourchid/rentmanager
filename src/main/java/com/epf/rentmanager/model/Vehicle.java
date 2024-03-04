package com.epf.rentmanager.model;

public class Vehicle {

    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle() {
    }

    public Vehicle(int id, String constructeur, String modele, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }

    public Vehicle(String constructeur, String modele, int nb_places) {
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getNb_places() {
        return String.valueOf(nb_places);
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
}
