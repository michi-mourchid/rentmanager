package com.epf.rentmanager.ui.servlets.rents;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@WebServlet("/rents/create")
public class RentCreateServlet extends HttpServlet {
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final ClientService clientService = ClientService.getInstance();
    private static final VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        try {
            List<Vehicle> vehicles = this.vehicleService.findAll();
            List<Client> clients = this.clientService.findAll();
            request.setAttribute("vehicles", vehicles);
            request.setAttribute("clients", clients);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        LocalDate date_of_birth = LocalDate.parse(request.getParameter("date_of_birth"));

        int client_id = Integer.parseInt(request.getParameter("client"));
        int vehicle_id = Integer.parseInt(request.getParameter("vehicle"));
        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));

        Reservation reservation = new Reservation(client_id,vehicle_id,debut,fin);
        try {
            this.reservationService.create(reservation);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
