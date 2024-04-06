package com.epf.rentmanager.ui.servlets.rents;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/rents/edit")
public class RentEditServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        try {
            Reservation reservation = this.reservationService.findById(Long.parseLong(request.getParameter("id")));
            List<Vehicle> vehicles = this.vehicleService.findAll();
            List<Client> clients = this.clientService.findAll();
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("clients", clients);
            request.setAttribute("rent", reservation);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        int client_id = Integer.parseInt(request.getParameter("client"));
        int vehicle_id = Integer.parseInt(request.getParameter("car"));
        System.out.println(vehicle_id);
        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));

        Reservation reservation = new Reservation(id, client_id,vehicle_id,debut,fin);
        System.out.println(reservation);
        try {
            this.reservationService.update(reservation);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }
}
