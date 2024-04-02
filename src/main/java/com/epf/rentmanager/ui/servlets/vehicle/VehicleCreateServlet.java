package com.epf.rentmanager.ui.servlets.vehicle;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.VehicleService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final VehicleService vehicleService = VehicleService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nbPlaces = Integer.parseInt(request.getParameter("seats"));

        Vehicle vehicle = new Vehicle(constructeur,modele, nbPlaces);
        try {
            this.vehicleService.create(vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
