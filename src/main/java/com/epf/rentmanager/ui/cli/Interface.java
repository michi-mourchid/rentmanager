package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import com.epf.rentmanager.ui.cli.ClientCLI.*;
import com.epf.rentmanager.ui.cli.VehicleCLI.*;

import java.time.LocalDate;
import java.util.List;

import static com.epf.rentmanager.ui.cli.ClientCLI.*;
import static com.epf.rentmanager.ui.cli.VehicleCLI.*;

public class Interface {

    private static final ClientService clientService = ClientService.getInstance();
    private static final VehicleService vehicleService = VehicleService.getInstance();
    //private static final ReservationService reservationService = ReservationService.getInstance();


    public static void main(String[] args) throws ServiceException, DaoException {
        try {
            displayMainMenu();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayMainMenu() throws ServiceException, DaoException {
        boolean running = true;
        while (running) {
            IOUtils.print("\n### Menu principal ###");
            IOUtils.print("1. Créer un client");
            IOUtils.print("2. Lister tous les clients");
            IOUtils.print("3. Créer un véhicule");
            IOUtils.print("4. Lister tous les véhicules");
            IOUtils.print("5. Supprimer un client (bonus)");
            IOUtils.print("6. Supprimer un véhicule (bonus)");
            IOUtils.print("7. Créer une reservation");
            IOUtils.print("8. Lister toutes les réservations");
            IOUtils.print("9. Lister toutes les réservations d'un client");
            IOUtils.print("10. Lister toutes les réservations d'un véhicule");
            IOUtils.print("11. Supprimer une réservation");
            IOUtils.print("12. Quitter");

            int choice = IOUtils.readInt("Choisissez une option : ");

            switch (choice) {
                case 1:
                    createClient();
                    break;
                case 2:
                    listClients();
                    break;
                case 3:
                    createVehicle();
                    break;
                case 4:
                    listVehicles();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 6:
                    deleteVehicle();
                    break;
                case 12:
                    running = false;
                    IOUtils.print("Au revoir !");
                    break;
                default:
                    IOUtils.print("Option invalide. Veuillez réessayer.");
            }
        }
    }



//    private static void createVehicle() {
//        IOUtils.print("\n### Création d'un véhicule ###");
//        String constructeur = IOUtils.readString("Constructeur : ", true);
//        String modele = IOUtils.readString("Modele : ", true);
//        int nbPlaces = IOUtils.readInt("Nombre de places : ");
//
//        try {
//            long vehicleId = vehicleService.create(new Vehicle(constructeur, modele, nbPlaces));
//            IOUtils.print("Véhicule créé avec succès ! (ID : " + vehicleId + ")");
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la création du véhicule : " + e.getMessage());
//        }
//    }

//    private static void createReservation(){
//        IOUtils.print("\n### Création d'une Reservation ###");
//        int clientId = IOUtils.readInt("ID du client : ");
//        int vehicleId = IOUtils.readInt("ID du véhicule : ");
//        LocalDate debut = IOUtils.readDate("Date de début (format dd/MM/yyyy) : ", true);
//        LocalDate fin = IOUtils.readDate("Date de fin (format dd/MM/yyyy) : ", true);
//
//        try {
//            Reservation reservation = new Reservation(clientId, vehicleId, debut, fin);
//            long id = reservationService.create(reservation);
//            IOUtils.print("Réservation créée avec succès. ID : " + id);
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la création de la réservation : " + e.getMessage());
//        }
//    }



//    private static void listVehicles() {
//        IOUtils.print("\n### Liste des véhicules ###");
//        try {
//            List<Vehicle> vehicles = vehicleService.findAll();
//            if (!vehicles.isEmpty()) {
//                for (Vehicle vehicle : vehicles) {
//                    IOUtils.print(vehicle.toString());
//                }
//            } else {
//                IOUtils.print("Aucun véhicule trouvé.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la récupération des véhicules : " + e.getMessage());
//        }
//    }

//    private static void listReservation() {
//        IOUtils.print("\n### Liste des reservations ###");
//        try {
//            List<Reservation> reservations = reservationService.findAll();
//            if (!reservations.isEmpty()) {
//                for (Reservation reservation : reservations) {
//                    IOUtils.print(reservation.toString());
//                }
//            } else {
//                IOUtils.print("Aucune reservation trouvé.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
//        }
//    }

//    private static void listReservationClient() {
//        IOUtils.print("\n### Liste des reservations par client ###");
//        int clientId = IOUtils.readInt("ID du client : ");
//        try {
//            List<Reservation> reservations = reservationService.findResaByClientId(clientId);
//            if (!reservations.isEmpty()) {
//                for (Reservation reservation : reservations) {
//                    IOUtils.print(reservation.toString());
//                }
//            } else {
//                IOUtils.print("Aucune reservation trouvé.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
//        }
//    }

//    private static void listReservationVehicle() {
//        IOUtils.print("\n### Liste des reservations par vehicule ###");
//        int vehicleID = IOUtils.readInt("ID du vehicule : ");
//        try {
//            List<Reservation> reservations = reservationService.findResaByVehicleId(vehicleID);
//            if (!reservations.isEmpty()) {
//                for (Reservation reservation : reservations) {
//                    IOUtils.print(reservation.toString());
//                }
//            } else {
//                IOUtils.print("Aucune reservation trouvé.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la récupération des reservations : " + e.getMessage());
//        }
//    }


//    private static void deleteClient() {
//        IOUtils.print("\n### Suppression d'un client ###");
//        int clientId = IOUtils.readInt("ID du client à supprimer : ");
//
//        try {
//            Client client = clientService.findById(clientId);
//            if (client != null) {
//                clientService.delete(client);
//                IOUtils.print("Client supprimé avec succès !");
//            } else {
//                IOUtils.print("Aucun client trouvé avec l'ID spécifié.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la suppression du client : " + e.getMessage());
//        }
//    }

//    private static void deleteVehicle() {
//        IOUtils.print("\n### Suppression d'un véhicule ###");
//        int vehicleId = IOUtils.readInt("ID du véhicule à supprimer : ");
//
//        try {
//            Vehicle vehicle = vehicleService.findById(vehicleId);
//            if (vehicle != null) {
//                vehicleService.delete(vehicle);
//                IOUtils.print("Véhicule supprimé avec succès !");
//            } else {
//                IOUtils.print("Aucun véhicule trouvé avec l'ID spécifié.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la suppression du véhicule : " + e.getMessage());
//        }
//    }

//    private static void deleteReservation() {
//        IOUtils.print("\n### Suppression d'une reservation ###");
//        boolean running = true;
//        while (running) {
//            IOUtils.print("1. Identifier par Vehicule");
//            IOUtils.print("2. Identifier par client");
//            IOUtils.print("3. Identifier par Reservation");
//            int choice = IOUtils.readInt("Choisissez une option : ");
//            switch (choice) {
//                case 1:
//                    listReservationVehicle();
//                    running = false;
//                    break;
//                case 2:
//                    listReservationClient();
//                    running = false;
//                    break;
//                case 3:
//                    running = false;
//                    break;
//                default:
//                    IOUtils.print("Option invalide. Veuillez réessayer.");
//            }
//        }
//        int reservationId = IOUtils.readInt("Entrez l'ID de la réservation à supprimer : ");
//
//        try {
//            Reservation reservation = reservationService.getReservationById(reservationId);
//            if (reservation != null) {
//                reservationService.delete(reservation);
//                IOUtils.print("Réservation supprimée avec succès !");
//            } else {
//                IOUtils.print("Aucune réservation trouvée avec cet ID.");
//            }
//        } catch (ServiceException e) {
//            IOUtils.print("Erreur lors de la suppression de la réservation : " + e.getMessage());
//        }
//
//    }
}