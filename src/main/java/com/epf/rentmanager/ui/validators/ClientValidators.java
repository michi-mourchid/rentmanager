package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import java.time.LocalDate;
import java.time.Period;

public class ClientValidators {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    ClientService clientService = context.getBean(ClientService.class);
    public boolean check_if_age_is_under_18(LocalDate dob) throws ServletException {
        LocalDate today = LocalDate.now();
//calculates the amount of time between two dates and returns the years
        if (dob != null) {
            int age = Period.between(dob, today).getYears();
            if (age < 18) throw new ServletException("L'utilisateur saisie doit etre majeur pour être inscrit");
            else return false;
        }
        throw new ServletException("La date de naissance saisie est nulle");
    }

    public boolean check_if_firstName_lastName_length_is_lessThan_3(String nom, String prenom) throws ServletException {
        boolean response = true;
        response = (nom.length() < 3);
        response = (prenom.length() < 3);
        if (response){
            throw new ServletException("Le nom et le prenom doivent avoir moins 3 caractères chacun");
        } else {
            return false;
        }
    }

    public boolean check_if_user_is_not_in_db(int id) throws ServiceException, DaoException {
        return ((this.clientService.findById(id)) == null);
    }



}
