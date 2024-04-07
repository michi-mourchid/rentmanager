package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import org.springframework.stereotype.Service;
import com.epf.rentmanager.ui.validators.ClientValidators;

@Service
public class ClientService {

	private ClientDao clientDao;
	private ClientValidators clientValidator = new ClientValidators();

	public ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
		this.clientValidator.validate_create(client);
		try {
			client.setNom(client.getNom().toUpperCase());
			return this.clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Une erreur s'est produite au niveau de la base de donnée");
		}
	}

	public Client findById(long id) throws ServiceException, DaoException {
		Client client = clientDao.findById(id);
		clientValidator.check_if_user_is_not_in_db(client);
		return client;
	}

	public List<Client> findAll() throws ServiceException, DaoException {
        try {
            return this.clientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Une erreur s'est produite au niveau de la base de donnée");
        }
    }

	public String delete(long id) throws ServiceException, DaoException {
		Client client = findById(id);
		this.clientValidator.check_if_user_is_not_in_db(client);
		return this.clientDao.delete(client);
	}

	public int count() throws ServiceException, DaoException{
		return this.clientDao.count();
	}

	public void update(Client client) throws ServiceException{
        try {
			this.clientValidator.validate_edit(client);
            this.clientDao.update(client);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur s'est produite au niveau de la base de donnée");
        }
    }
	
}
