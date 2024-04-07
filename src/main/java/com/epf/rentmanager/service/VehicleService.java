package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.ui.validators.VehicleValidators;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private VehicleValidators vehicleValidator = new VehicleValidators();
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	
	public long create(Vehicle vehicle) throws ServiceException {
		this.vehicleValidator.check_if_vehicle_informations_are_not_good(vehicle);
		try {
			if (vehicle.getConstructeur()==null || vehicle.getConstructeur().isEmpty() || Integer.parseInt(vehicle.getNb_places())==1){
				throw new ServiceException();
			}

			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur s'est produite au niveau de la base de donnée");
		}
	}

	public boolean delete(long id) throws ServiceException, DaoException {
		Vehicle vehicle = findById(id);
		this.vehicleValidator.check_if_vehicle_is_not_in_db(vehicle);
		return this.vehicleDao.delete(vehicle);
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		Vehicle vehicle = vehicleDao.findById(id);
		this.vehicleValidator.check_if_vehicle_is_not_in_db(vehicle);
		return vehicle;
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		return this.vehicleDao.findAll();
	}

	public int count() throws ServiceException, DaoException{
		return this.vehicleDao.count();
	}

	public void update(Vehicle vehicle) throws ServiceException, DaoException {
		this.vehicleValidator.validate_edit(vehicle);
		this.vehicleDao.update(vehicle);
	}
	
}
