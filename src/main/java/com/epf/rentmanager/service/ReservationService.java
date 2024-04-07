package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.ui.validators.ReservationValidators;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDao reservationDao;
    private ReservationValidators reservationValidator = new ReservationValidators();

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException, DaoException {
        this.reservationValidator.validate_create(reservation);
        try {
        // Data validation à ajouter
            return this.reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("Une erreur s'est produite au niveau de la base de donnée");
        }
    }

    public boolean delete(long id) throws ServiceException, DaoException {
        Reservation reservation = this.reservationDao.findById(id);
        this.reservationValidator.check_if_reservation_is_not_in_db(reservation);
        return this.reservationDao.delete(reservation);
    }

    public List<Reservation> findResaByVehicleId(long clientId) throws ServiceException, DaoException {
        return this.reservationDao.findResaByVehicleId(clientId);
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException, DaoException {
        return this.reservationDao.findResaByClientId(clientId);
    }

    public List<Reservation> findAll() throws ServiceException, DaoException {
        return this.reservationDao.findAll();
    }

    public Reservation findById(long id) throws ServiceException, DaoException {
        Reservation reservation = reservationDao.findById(id);
        reservationValidator.check_if_reservation_is_not_in_db(reservation);
        return this.reservationDao.findById(id);
    }

    public int count() throws ServiceException, DaoException{
        return this.reservationDao.count();
    }

    public void update(Reservation reservation) throws DaoException, ServiceException {
        this.reservationValidator.validate_edit(reservation);
        this.reservationDao.update(reservation);
    }
}
