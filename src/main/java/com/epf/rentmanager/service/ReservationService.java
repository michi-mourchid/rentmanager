package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer un véhicule
        try {
        // Data validation à ajouter
            return this.reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(long id) throws ServiceException, DaoException {
        Reservation reservation = this.reservationDao.findById(id);
        return this.reservationDao.delete(reservation);
    }

    public List<Reservation> findResaByVehicleId(long clientId) throws ServiceException, DaoException {
        // TODO: récupérer un véhicule par son id
        return this.reservationDao.findResaByVehicleId(clientId);
    }

    public List<Reservation> findResaByClientId(long clientId) throws ServiceException, DaoException {
        // TODO: récupérer un véhicule par son id
        return this.reservationDao.findResaByClientId(clientId);
    }

    public List<Reservation> findAll() throws ServiceException, DaoException {
        // TODO: récupérer tous les clients
        return this.reservationDao.findAll();
    }

    public Reservation findById(long id){
        return this.reservationDao.findById(id);
    }

    public int count() throws ServiceException, DaoException{
        return this.reservationDao.count();
    }

    public void update(Reservation reservation) throws DaoException {
        this.reservationDao.update(reservation);
    }
}
