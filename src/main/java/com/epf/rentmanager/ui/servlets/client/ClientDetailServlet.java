package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailServlet extends HttpServlet {

    @Autowired
    VehicleService vehicleService;
    @Autowired
    ClientService clientService;
    @Autowired
    ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Client client = this.clientService.findById(id);
            List<Reservation> reservations = this.reservationService.findResaByClientId(id);
            List<Vehicle> vehicles = new ArrayList<>();

            for(Reservation reservation: reservations){
                vehicles.add(vehicleService.findById(Long.parseLong(reservation.getVehicle_id())));
            }

            request.setAttribute("client", client);
            request.setAttribute("rents", reservations);
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("nb_resa",reservations.size());
            request.setAttribute("nb_cars", vehicles.size());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
        } catch (DaoException | ServiceException e) {
            throw new RuntimeException(e);
        }

    }
}
