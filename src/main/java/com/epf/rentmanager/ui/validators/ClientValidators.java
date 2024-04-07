package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.Exceptions.ServiceException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

public class ClientValidators {
    ClientDao clientDao = new ClientDao();
    public ClientValidators(){}

    public void check_if_age_is_under_18(Client client) throws ServiceException {
        LocalDate today = LocalDate.now();
        if (client.getNaissance() != null) {
            int age = Period.between(client.getNaissance().toLocalDate(), today).getYears();
            if (age < 18) throw new ServiceException("L'utilisateur saisie doit etre majeur pour être inscrit");
        } else throw new ServiceException("La date de naissance saisie est nulle");
    }

    public void check_if_naissance_is_not_before_today(Client client) throws ServiceException {
        Date today = Date.valueOf(LocalDate.now());
        if (client.getNaissance().after(today)) throw new ServiceException("La date de naissance saisie est ultérieure à la date d'aujourd'hui");
    }

    public void check_if_firstName_lastName_length_is_lessThan_3(Client client) throws ServiceException {
        if ((client.getNom().length() < 3) || (client.getPrenom().length() < 3)) throw new ServiceException("Le nom et le prenom doivent avoir moins 3 caractères chacun");
    }

    public void check_if_user_is_not_in_db(Client client) throws ServiceException, DaoException {
        if (client==null) throw new ServiceException("Aucun client trouvee");
        if (client.getId()==0 || client.getId()<0) throw new ServiceException("ID Client saisi invalide");
        if ((this.clientDao.findById(client.getId())) == null) throw new ServiceException("Le client recherché n'existe pas");
    }

    public void check_if_mail_is_invalid(Client client) throws ServiceException {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!client.getEmail().matches(regex)) throw new ServiceException("L'adresse email saisie n'est pas conforme");
    }

    public void check_if_names_are_invalid(Client client) throws ServiceException {
        String regex = ".*\\d.*";
        if (client.getNom().matches(regex) || client.getPrenom().matches(regex)) throw new ServiceException("Les nom et prenom ne doivent pas contenir de chiffre");
        if (client.getNom()=="" || client.getPrenom()=="") throw new ServiceException("Nom ou Prenom vide");
    }

    public void validate_edit(Client client) throws ServiceException, DaoException {
        /**
         * Permet d'effectuer tous les tests nécessaires lors de la modification d'un utilisateur
         */
        check_if_user_is_not_in_db(client);
        check_if_mail_is_invalid(client);
        check_if_names_are_invalid(client);
        check_if_naissance_is_not_before_today(client);
        check_if_age_is_under_18(client);
        check_if_firstName_lastName_length_is_lessThan_3(client);
    }

    public void validate_create(Client client) throws ServiceException {
        /**
         * Permet d'effectuer tous les tests nécessaires lors de la création d'un utilisateur
         */

        check_if_naissance_is_not_before_today(client);
        check_if_age_is_under_18(client);
        check_if_names_are_invalid(client);
        check_if_mail_is_invalid(client);
        check_if_firstName_lastName_length_is_lessThan_3(client);
    }




}
