package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationValidators {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    ClientService clientService = context.getBean(ClientService.class);
    VehicleService vehicleService = context.getBean(VehicleService.class);
    ReservationService reservationService = context.getBean(ReservationService.class);
    public ReservationValidators(){}

    /*
- une voiture ne peux pas être réservé 30 jours de suite sans pause
- si un client ou un véhicule est supprimé, alors il faut supprimer les
    réservations associées
- une voiture doit avoir un modèle et un constructeur, son nombre de place doit
    être compris entre 2 et 9*/

    public boolean check_if_vehicle_have_already_been_booked_in_period_by_user(Reservation rent) throws ServiceException, DaoException, ServletException {
        int user_id= Integer.parseInt(rent.getClient_id());
        int vehicle_id= Integer.parseInt(rent.getVehicle_id());
        Date debutResa = rent.getDebut();
        Date finResa = rent.getFin();
        List<Reservation> reservations = this.reservationService.findResaByClientId(user_id);
        boolean result = true;
        for(Reservation reservation: reservations){
            if(Integer.parseInt(reservation.getVehicle_id())==vehicle_id){
                if (reservation.getDebut()==debutResa ||
                        (debutResa.after( reservation.getDebut()) && debutResa.before(reservation.getFin())) ||
                        (finResa.after(reservation.getDebut()) && finResa.before(reservation.getFin()))   ){
                    throw new ServletException("Vous ne pouvez pas réserver un même véhicule dans des périodes qui se chevauchent : conflits");
                } else {result = false;}
            }
        }
        return result;
    }

    public void check_if_vehicle_is_booked_more_than_7_days_in_continuous_by_user(Reservation rent) throws ServiceException, DaoException, ServletException {
        long nbJourResa = ChronoUnit.DAYS.between(rent.getDebut().toLocalDate(), rent.getFin().toLocalDate());
        if (nbJourResa>7) throw new ServletException("Vous ne pouvez pas réserver un véhicule plus de 7 jours de suite");
    }

    public void check_if_vehicle_is_booked_continuously_for_more_than_30_days(Reservation rent) throws ServiceException, DaoException, ServletException {
        List<Reservation> reservations = this.reservationService.findResaByVehicleId(Long.parseLong(rent.getVehicle_id()));
        Date debutSerie = null;
        Date finSerie = null;
        long nbJourResa = 0;
        long nbJourResaDeBase = 0;
        for(Reservation resa_i: reservations){
            debutSerie = resa_i.getDebut();
            finSerie = resa_i.getFin();
            for (Reservation resa_j: reservations){
                Date debutResa = resa_j.getDebut();
                Date finResa = resa_j.getFin();
                if (Date.valueOf(finResa.toLocalDate().plusDays(1)).equals(debutSerie)) debutSerie = debutResa;
                else if (Date.valueOf(finSerie.toLocalDate().plusDays(1)).equals(debutResa)) finSerie = finResa;
            }
            nbJourResaDeBase = ChronoUnit.DAYS.between(rent.getDebut().toLocalDate(), rent.getFin().toLocalDate());
            if (Date.valueOf(rent.getFin().toLocalDate().plusDays(1)).equals(debutSerie)) debutSerie = rent.getDebut();
            else if (Date.valueOf(finSerie.toLocalDate().plusDays(1)).equals(rent.getDebut())) finSerie = rent.getFin();
            nbJourResa = ChronoUnit.DAYS.between(debutSerie.toLocalDate(), finSerie.toLocalDate());

            if (nbJourResa>30) throw new ServletException("Véhicule impossible à réserver : ce véhicule a été réservé " + nbJourResaDeBase + " jours de suite, vous ne pouvez pas dépasser les 30 jours de suite");
        }
    }
}
