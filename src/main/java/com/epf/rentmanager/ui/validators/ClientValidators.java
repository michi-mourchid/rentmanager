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
/*- un client n'ayant pas 18ans ne peut pas être créé
            - un client ayant une adresse mail déjà prise ne peut pas être créé
- le nom et le prénom d'un client doivent faire au moins 3 caractères
            - une voiture ne peux pas être réservé 2 fois le même jour
- une voiture ne peux pas être réservé plus de 7 jours de suite par le même
    utilisateur
- une voiture ne peux pas être réservé 30 jours de suite sans pause
- si un client ou un véhicule est supprimé, alors il faut supprimer les
    réservations associées
- une voiture doit avoir un modèle et un constructeur, son nombre de place doit
    être compris entre 2 et 9*/
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    ClientService clientService = context.getBean(ClientService.class);
    public boolean check_if_age_is_under_18(LocalDate dob) throws ServletException {
        LocalDate curDate = LocalDate.now();
//calculates the amount of time between two dates and returns the years
        if ((dob != null) && (curDate != null)) {
            int age = Period.between(dob, curDate).getYears();
            if (age < 18) {
                throw new ServletException("L'utilisateur saisie doit etre majeur pour être inscrit");
            } else {
                return false;
            }
        }
        else{
            throw new ServletException("La date de naissance saisie est nulle");
        }
    }

    public boolean check_if_firstName_lastName_length_is_lessThan_3(String nom, String prenom) throws ServletException {
        boolean response = true;
        response = ((nom.length()<3) ? true : false);
        response = ((prenom.length()<3) ? true : false);
        if (response==true){
            throw new ServletException("Le nom et le prenom doivent avoir moins 3 caractères chacun");
        } else {
            return false;
        }
    }

    public boolean check_if_user_is_not_in_db(int id) throws ServiceException, DaoException {
        boolean result = ((this.clientService.findById(id))==null ? true : false );
        return result;
    }



}
