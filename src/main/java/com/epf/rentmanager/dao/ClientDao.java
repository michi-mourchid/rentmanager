package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_ALL_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	
	public long create(Client client) throws DaoException {
		int id = 0;
		try{
			System.out.println("ICIIIII1");
			Connection connection = ConnectionManager.getConnection();
			System.out.println("ICIIIII1.5");
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			System.out.println("ICIIIII2");
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, client.getNaissance());

			System.out.println("ICIIIII3");
			ResultSet resultSet = ps.getGeneratedKeys();

			System.out.println("ICIIIII4");
			if (resultSet.next()){
				id = resultSet.getInt("id");
			}

			System.out.println("ICIIIII5");
			ps.execute();
			ps.close();
			connection.close();
			System.out.println("ICIIIII");
			return id;

		} catch (SQLException e){
			throw new DaoException();
		}


	}
	
	public String delete(Client client) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY);


			ps.setString(1, String.valueOf(client.getId()));

			ps.execute();
			ps.close();
			connection.close();

			return client.getNom();

		} catch (SQLException e){
			throw new DaoException();
		}
	}

	public Client findById(long id) throws DaoException {
		Client client = new Client();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);
			ps.setLong(1, id);
			ps.execute();

			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();

				client = new Client((int) id, nom, prenom, email, naissance);
			}

			ps.close();
			connection.close();
			return  client;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> clients = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ps.execute();
			Client client;
			ResultSet rs = ps.getResultSet();


			while (rs.next()){
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate naissance = rs.getDate("naissance").toLocalDate();

				client = new Client(id, nom, prenom, email, naissance);
				clients.add(client);
			}
			ps.execute();
			ps.close();
			connection.close();
			return  clients;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count(){
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_ALL_CLIENTS_QUERY);
			ps.execute();

			int nbClients=-1;
			ResultSet rs = ps.getResultSet();

			while (rs.next()){
				nbClients = rs.getInt("count");
			}

			ps.close();
			connection.close();
			System.out.println(nbClients);
			return nbClients;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
