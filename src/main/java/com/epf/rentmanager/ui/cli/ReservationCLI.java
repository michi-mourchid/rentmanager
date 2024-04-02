package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.util.List;
import java.time.LocalDate;

public class ReservationCLI {

    private static final ReservationService reservationService = ReservationService.getInstance();


    public static void createReservation() {
        IOUtils.print("\n### Création d'une Reservation ###");
        int clientId = IOUtils.readInt("ID du client : ");
        int vehicleId = IOUtils.readInt("ID du véhicule : ");
        LocalDate debut = IOUtils.readDate("Date de début (format dd/MM/yyyy) : ", true);
        LocalDate fin = IOUtils.readDate("Date de fin (format dd/MM/yyyy) : ", true);

        try {
            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
            long id = reservationService.create(reservation);
            IOUtils.print("Réservation créée avec succès. ID : " + id);
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création de la réservation : " + e.getMessage());
        }
    }

    public static void deleteReservation() {
        IOUtils.print("\n### Suppression d'une reservation ###");
        int reservationId = IOUtils.readInt("Entrez l'ID de la réservation à supprimer : ");

        try {
            if (reservationService.delete(reservationId)) {
                IOUtils.print("Réservation supprimée avec succès !");
            } else {
                IOUtils.print("Aucune réservation trouvée avec cet ID.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression de la réservation : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
    public static void listReservations() {
        IOUtils.print("\n### Liste des reservations ###");
        try {
            List<Reservation> reservations = reservationService.findAll();
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune reservation trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listReservationClient() {
        IOUtils.print("\n### Liste des reservations par client ###");
        int clientId = IOUtils.readInt("ID du client : ");
        try {
            List<Reservation> reservations = reservationService.findResaByClientId(clientId);
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune reservation trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listReservationVehicle() {
        IOUtils.print("\n### Liste des reservations par vehicule ###");
        int vehicleID = IOUtils.readInt("ID du vehicule : ");
        try {
            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleID);
            if (!reservations.isEmpty()) {
                for (Reservation reservation : reservations) {
                    IOUtils.print(reservation.toString());
                }
            } else {
                IOUtils.print("Aucune reservation trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }






}
