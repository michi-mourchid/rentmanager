package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.service.Exceptions.ServiceException;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			if (vehicle.getConstructeur()==null || vehicle.getConstructeur().isEmpty() || Integer.parseInt(vehicle.getNb_places())==1){
				throw new ServiceException();
			}

			return this.vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public int delete(long id) throws ServiceException, DaoException {
		Vehicle client = findById(id);
		return this.vehicleDao.delete(client);
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		// TODO: récupérer un véhicule par son id
		return this.vehicleDao.findById(id);
	}

	public List<Vehicle> findAll() throws ServiceException, DaoException {
		// TODO: récupérer tous les clients
		return this.vehicleDao.findAll();
	}
	
}
