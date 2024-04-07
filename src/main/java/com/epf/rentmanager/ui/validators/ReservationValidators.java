package com.epf.rentmanager.ui.validators;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.Exceptions.ServiceException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationValidators {
    ReservationDao reservationService = new ReservationDao();
    public ReservationValidators(){}

    public void check_if_vehicle_have_already_been_booked_in_period_by_user(Reservation rent) throws ServiceException, DaoException {
        int user_id= Integer.parseInt(rent.getClient_id());
        int vehicle_id= Integer.parseInt(rent.getVehicle_id());
        Date debutResa = rent.getDebut();
        Date finResa = rent.getFin();
        List<Reservation> reservations = this.reservationService.findResaByClientId(user_id);
        for(Reservation reservation: reservations){
            if (rent.getId()!=reservation.getId()) {
                if (Integer.parseInt(reservation.getVehicle_id()) == vehicle_id) {
                    if (reservation.getDebut().equals(debutResa) || reservation.getFin().equals(debutResa) || reservation.getDebut().equals(finResa)||
                            (debutResa.after(reservation.getDebut()) && debutResa.before(reservation.getFin())) ||
                            (finResa.after(reservation.getDebut()) && finResa.before(reservation.getFin()))) {
                        throw new ServiceException("Vous ne pouvez pas réserver un même véhicule dans des périodes qui se chevauchent : conflits");
                    }
                }
            }
        }
    }

    public void check_if_vehicle_is_booked_more_than_7_days_in_continuous_by_user(Reservation rent) throws ServiceException {
        long nbJourResa = ChronoUnit.DAYS.between(rent.getDebut().toLocalDate(), rent.getFin().toLocalDate());
        if (nbJourResa>7) throw new ServiceException("Vous ne pouvez pas réserver un véhicule plus de 7 jours de suite");
    }

    public void check_if_vehicle_is_booked_continuously_for_more_than_30_days(Reservation rent) throws ServiceException, DaoException {
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
            nbJourResaDeBase = ChronoUnit.DAYS.between(debutSerie.toLocalDate(), rent.getDebut().toLocalDate());
            if (Date.valueOf(rent.getFin().toLocalDate().plusDays(1)).equals(debutSerie)) debutSerie = rent.getDebut();
            else if (Date.valueOf(finSerie.toLocalDate().plusDays(1)).equals(rent.getDebut())) finSerie = rent.getFin();
            nbJourResa = ChronoUnit.DAYS.between(debutSerie.toLocalDate(), finSerie.toLocalDate());

            if (nbJourResa>30) throw new ServiceException("Véhicule impossible à réserver : ce véhicule a été réservé " + (nbJourResaDeBase-1) + " jours de suite, vous ne pouvez pas dépasser les 30 jours de suite");
        }
    }

    public void check_if_reservation_is_not_in_db(Reservation reservation) throws ServiceException, DaoException {
        if (reservation==null) throw new ServiceException("Aucune reservation trouvee");
        if (reservation.getId()==0 || reservation.getId()<0) throw new ServiceException("ID Reservation saisi invalide");
        if ((this.reservationService.findById(reservation.getId())) == null) throw new ServiceException("La reservation recherchee n'est pas trouvee");
    }

    public void check_if_reservation_is_past(Reservation reservation) throws ServiceException {
        if (reservation.getDebut().before(Date.valueOf(LocalDate.now()))) throw new ServiceException("La date de reservation est anterieure a la date d'aujourd'hui");
        if (reservation.getFin().before(Date.valueOf(LocalDate.now()))) throw new ServiceException("La date de reservation est anterieure a la date d'aujourd'hui");
    }

    public void check_if_dates_are_invalid(Reservation reservation) throws ServiceException {
        if (reservation.getFin().before(reservation.getDebut())) throw new ServiceException("La date de debut de reservation doit etre avant la date de fin");
    }

    public void validate_edit(Reservation reservation) throws ServiceException, DaoException {
        /**
         * Permet d'effectuer tous les tests nécessaires lors de la modification d'une réservation
         */
        check_if_reservation_is_not_in_db(reservation);
        check_if_dates_are_invalid(reservation);
        check_if_vehicle_have_already_been_booked_in_period_by_user(reservation);
        check_if_vehicle_is_booked_more_than_7_days_in_continuous_by_user(reservation);
        check_if_vehicle_is_booked_continuously_for_more_than_30_days(reservation);
    }

    public void validate_create(Reservation reservation) throws ServiceException, DaoException {
        /**
         * Permet d'effectuer tous les tests nécessaires lors de la création d'une réservation
         */
        check_if_vehicle_have_already_been_booked_in_period_by_user(reservation);
        check_if_reservation_is_past(reservation);
        check_if_dates_are_invalid(reservation);
        check_if_vehicle_is_booked_more_than_7_days_in_continuous_by_user(reservation);
        check_if_vehicle_is_booked_continuously_for_more_than_30_days(reservation);
    }
}
