package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_ALL_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";
	
	public long create(Client client) throws DaoException {
		int id = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, client.getNaissance());

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()){
				id = resultSet.getInt("id");
			}

			ps.execute();
			ps.close();
			connection.close();
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
			if(rs==null){
				return null;
			}

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

	public void update(Client client){
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(UPDATE_CLIENT_QUERY);
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, client.getNaissance());
			ps.setInt(5, client.getId());


			ps.execute();
			ps.close();
			connection.close();

		} catch (SQLException e){
		}
	}

}
