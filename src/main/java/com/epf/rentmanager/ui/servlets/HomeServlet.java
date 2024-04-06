package com.epf.rentmanager.ui.servlets;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    private static final ClientService clientService = context.getBean(ClientService.class);
    private static final ReservationService reservationService = context.getBean(ReservationService.class);
    private static final VehicleService vehicleService = context.getBean(VehicleService.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("nbVehicles",this.vehicleService.count());
            request.setAttribute("nbRents",this.reservationService.count());
            request.setAttribute("nbClients",this.clientService.count());
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

}