package com.epf.rentmanager.ui.servlets.vehicle;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/cars/edit")
public class VehicleEditServlet extends HttpServlet {
    @Autowired
    VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Vehicle vehicle = this.vehicleService.findById(id);

            request.setAttribute("vehicle", vehicle);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        int nbPlaces = Integer.parseInt(request.getParameter("seats"));

        Vehicle vehicle = new Vehicle(id, constructeur,modele, nbPlaces);

        this.vehicleService.update(vehicle);
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}