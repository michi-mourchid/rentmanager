package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.utils.IOUtils;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_ALL_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {

		try{
			int id = 0;
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);


			ps.setString(1, reservation.getClient_id());
			ps.setString(2, reservation.getVehicle_id());
			ps.setDate(3, reservation.getDebut());
			ps.setDate(4, reservation.getFin());

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
	
	public boolean delete(Reservation reservation) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);


			ps.setString(1, String.valueOf(reservation.getId()));

			ps.execute();
			ps.close();
			connection.close();

			return true;

		} catch (SQLException e){
			IOUtils.print("Un problème est survenu, la suppression n'a pas eu lieu");
			return false;
		}
	}

	public Reservation findById(long reservationId){
		try {
			Reservation reservation = null;
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION_QUERY);

			ps.setLong(1, reservationId);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservation = new Reservation(id,  client_id,  vehicle_id,  debut, fin);
			}

			ps.close();
			connection.close();
			return reservation;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Reservation reservation;
			ArrayList<Reservation> reservations = new ArrayList<>();
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			ps.setLong(1, clientId);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservation = new Reservation(id,  client_id,  vehicle_id,  debut, fin);
				reservations.add(reservation);
			}

			ps.close();
			connection.close();
			return reservations;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
        try {
			Reservation reservation;
			ArrayList<Reservation> reservations = new ArrayList<>();
            Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			ps.setLong(1, vehicleId);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservation = new Reservation(id,  client_id,  vehicle_id,  debut, fin);
				reservations.add(reservation);
			}

			ps.close();
			connection.close();
			return reservations;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Reservation reservation;
			ArrayList<Reservation> reservations = new ArrayList<>();
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				int id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int vehicle_id = rs.getInt("vehicle_id");
				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				reservation = new Reservation(id,  client_id,  vehicle_id,  debut, fin);
				reservations.add(reservation);
			}

			ps.close();
			connection.close();
			return reservations;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count(){
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_ALL_RESERVATIONS_QUERY);
			ps.execute();

			int nbReservations=-1;
			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				nbReservations = rs.getInt("count");
			}

			ps.close();
			connection.close();
			System.out.println(nbReservations);
			return nbReservations;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
