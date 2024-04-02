package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.Exceptions.ServiceException;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
		// TODO: créer un client
		try {
			if(client.getPrenom()==null || client.getPrenom().length()==0 || client.getNom()==null || client.getNom().length()==0){
				throw new ServiceException();
			}
			client.setNom(client.getNom().toUpperCase());
			return this.clientDao.create(client);
		} catch (DaoException e) {
			throw new RuntimeException(e);
		} catch (ServiceException serviceException){
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException, DaoException {
		// TODO: récupérer un client par son id
		return this.clientDao.findById(id);
	}

	public List<Client> findAll() throws ServiceException, DaoException {
		// TODO: récupérer tous les clients
        try {
            return this.clientDao.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

	public String delete(long id) throws ServiceException, DaoException {
		Client client = findById(id);
		return this.clientDao.delete(client);
	}

	public int count() throws ServiceException, DaoException{
		return this.clientDao.count();
	}
	
}
