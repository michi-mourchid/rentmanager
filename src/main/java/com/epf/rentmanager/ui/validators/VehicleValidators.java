package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;

public class VehicleValidators {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    ClientService clientService = context.getBean(ClientService.class);
    VehicleService vehicleService = context.getBean(VehicleService.class);
    ReservationService reservationService = context.getBean(ReservationService.class);

    /*
            - une voiture ne peux pas être réservé 2 fois le même jour
- une voiture ne peux pas être réservé plus de 7 jours de suite par le même
    utilisateur
- une voiture ne peux pas être réservé 30 jours de suite sans pause
- si un client ou un véhicule est supprimé, alors il faut supprimer les
    réservations associées
- une voiture doit avoir un modèle et un constructeur, son nombre de place doit
    être compris entre 2 et 9*/

    public void check_if_vehicle_informations_are_not_good(Vehicle vehicle) throws ServletException {
        if (vehicle.getModele().trim().isEmpty() || vehicle.getModele() == null) {
            throw new ServletException("Veuillez saisir un modele de véhicule non vide");
        }
        if (vehicle.getConstructeur().trim().isEmpty() || vehicle.getConstructeur() == null){
            throw new ServletException("Veuillez saisir un constructeur de véhicule non vide");
        }
        if (Integer.parseInt(vehicle.getNb_places())<2 && Integer.parseInt(vehicle.getNb_places())>9){
            throw new ServletException("Veuillez saisir un nombre de places compris entre 2 et 9");
        }
    }

    public boolean check_if_vehicle_is_not_in_db(int id) throws ServiceException, DaoException {
        return ((this.clientService.findById(id)) == null);
    }
}
