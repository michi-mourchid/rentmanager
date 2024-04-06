package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.epf.rentmanager.ui.cli.ClientCLI.*;
import static com.epf.rentmanager.ui.cli.VehicleCLI.*;
import static com.epf.rentmanager.ui.cli.ReservationCLI.*;

public class Interface {


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
            IOUtils.print("8. Supprimer une réservation");
            IOUtils.print("9. Lister toutes les réservations");
            IOUtils.print("10. Lister toutes les réservations d'un client");
            IOUtils.print("11. Lister toutes les réservations d'un véhicule");
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
                case 7:
                    createReservation();
                    break;
                case 8:
                    deleteReservation();
                    break;
                case 9:
                    listReservations();
                    break;
                case 10:
                    listReservationClient();
                    break;
                case 11:
                    listReservationVehicle();
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
}