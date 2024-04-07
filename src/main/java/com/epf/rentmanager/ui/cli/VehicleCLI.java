package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class VehicleCLI {
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    private static final VehicleService vehicleService = context.getBean(VehicleService.class);

    public static void createVehicle(){
        IOUtils.print("\n### Création d'un véhicule ###");
        String constructeur = IOUtils.readString("Constructeur : ", true);
        String modele = IOUtils.readString("Modele : ", true);
        int nbPlaces = IOUtils.readInt("Nombre de places : ");

        try {
            Vehicle vehicle = new Vehicle(constructeur, modele, nbPlaces);
            long vehicleId = vehicleService.create(vehicle);
            IOUtils.print("Véhicule créé avec succès ! (ID : " + vehicleId + ")");
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la création du véhicule : " + e.getMessage());
        }
    }

    public static void listVehicles(){
        IOUtils.print("\n### Liste des véhicules ###");
        try {
            List<Vehicle> vehicles = vehicleService.findAll();
            if (!vehicles.isEmpty()) {
                for (Vehicle vehicle : vehicles) {
                    IOUtils.print(vehicle.toString());
                }
            } else {
                IOUtils.print("Aucun véhicule trouvé.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la récupération des véhicules : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteVehicle(){
        IOUtils.print("\n### Suppression d'un véhicule ###");
        int vehicleId = IOUtils.readInt("ID du véhicule à supprimer : ");

        try {
            Vehicle vehicle = vehicleService.findById(vehicleId);
            if (vehicle != null) {
                vehicleService.delete(vehicleId);
                IOUtils.print("Véhicule supprimé avec succès !");
            } else {
                IOUtils.print("Aucun véhicule trouvé avec l'ID spécifié.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression du véhicule : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
