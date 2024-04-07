package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.Exceptions.ServiceException;

public class VehicleValidators {
    VehicleDao vehicleDao = new VehicleDao();

    public VehicleValidators() {
    }

    public void check_if_vehicle_informations_are_not_good(Vehicle vehicle) throws ServiceException {
        if (vehicle.getModele().trim().isEmpty() || vehicle.getModele() == null) {
            throw new ServiceException("Veuillez saisir un modele de véhicule non vide");
        }
        if (vehicle.getConstructeur().trim().isEmpty() || vehicle.getConstructeur() == null){
            throw new ServiceException("Veuillez saisir un constructeur de véhicule non vide");
        }
        if (Integer.parseInt(vehicle.getNb_places())<2 || Integer.parseInt(vehicle.getNb_places())>9){
            throw new ServiceException("Veuillez saisir un nombre de places compris entre 2 et 9");
        }
    }
    public void check_if_vehicle_is_not_in_db(Vehicle vehicle) throws ServiceException, DaoException {
        if (vehicle==null) throw new ServiceException("Aucun véhicule trouvé");
        if (vehicle.getId()==0 || vehicle.getId()<0) throw new ServiceException("ID Véhicule saisi invalide");
        if ((this.vehicleDao.findById(vehicle.getId())) == null) throw new ServiceException("Le véhicule recherché n'est pas trouvé");
    }


    public void validate_edit(Vehicle vehicle) throws ServiceException, DaoException {
        /**
         * Permet d'effectuer tous les tests nécessaires lors de la modification d'un véhicule
         */
        check_if_vehicle_is_not_in_db(vehicle);
        check_if_vehicle_informations_are_not_good(vehicle);
    }



}
