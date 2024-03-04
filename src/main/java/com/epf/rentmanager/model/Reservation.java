package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.sql.Date;

public class Reservation {
    private int id;
    private int client_id;
    private int vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation() {
    }

    public Reservation(int id, int client_id, int vehicle_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation(int client_id, int vehicle_id, LocalDate debut, LocalDate fin) {
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_id() {
        return String.valueOf(client_id);
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getVehicle_id() {
        return String.valueOf(vehicle_id);
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public Date getDebut() {
        return Date.valueOf(debut);
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return Date.valueOf(fin);
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }
}
