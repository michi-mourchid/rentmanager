package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_ALL_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";
	public long create(Vehicle vehicle) throws DaoException {
		try{
			int id = 0;
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);


			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setString(3, vehicle.getNb_places());
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()){
				id = resultSet.getInt(1);
			}

			ps.execute();
			ps.close();
			connection.close();

			return id;

		} catch (SQLException e){
			throw new DaoException();
		}
	}

	public boolean delete(Vehicle vehicle) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);


			ps.setString(1, String.valueOf(vehicle.getId()));

			ps.execute();
			ps.close();
			connection.close();

			return true;

		} catch (SQLException e){
			return false;
		}
	}

	public Vehicle findById(long id) throws DaoException {
		Vehicle vehicle = new Vehicle();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_ALL_VEHICLES_QUERY);
			ps.setLong(1, id);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("modele");
				int nb_places = rs.getInt("nb_places");

				vehicle = new Vehicle((int) id, constructeur, modele, nb_places);
			}

			ps.close();
			connection.close();
			return vehicle;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> vehicles = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
			ps.execute();
			Vehicle vehicle;
			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				int id = rs.getInt("id");
				String constructeur = rs.getString("constructeur");
				String modele = rs.getString("modele");
				int nb_places = rs.getInt("nb_places");

				vehicle = new Vehicle(id, constructeur, modele, nb_places);
				vehicles.add(vehicle);
			}
			ps.close();
			connection.close();
			return  vehicles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	public int count(){
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY);
			ps.execute();

			int nbVehicles=-1;
			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				nbVehicles = rs.getInt("count");
			}

			ps.close();
			connection.close();
			System.out.println(nbVehicles);
			return nbVehicles;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
