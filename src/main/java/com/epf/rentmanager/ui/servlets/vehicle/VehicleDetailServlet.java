package com.epf.rentmanager.ui.servlets.vehicle;

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

@WebServlet("/cars/details")
public class VehicleDetailServlet extends HttpServlet {

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
            Vehicle vehicle = this.vehicleService.findById(id);
            List<Reservation> reservations = this.reservationService.findResaByVehicleId(id);

            request.setAttribute("vehicle", vehicle);
            request.setAttribute("rents", reservations);

            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute("localisation", "Lors de l'affichage des infos vehicule");
            request.setAttribute("type_erreur", "ServiceException");
            request.setAttribute("message_erreur", e.getMessage());
            request.setAttribute("path", request.getServletPath()+"?"+(request.getQueryString()==null ? "":request.getQueryString()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (DaoException e) {
            request.setAttribute("localisation", "Lors de l'affichage des infos vehicule");
            request.setAttribute("type_erreur", "DaoException");
            request.setAttribute("message_erreur", e.getMessage());
            request.setAttribute("path", request.getServletPath()+"?"+(request.getQueryString()==null ? "":request.getQueryString()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

    }
}
