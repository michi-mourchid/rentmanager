package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
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
import java.util.List;

@WebServlet("/users")
public class ClientListServlet extends HttpServlet {

    @Autowired
    ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Client> clients = this.clientService.findAll();

            request.setAttribute("clients", clients);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute("localisation", "Lors de l'obtention de la liste d'utilisateurs");
            request.setAttribute("type_erreur", "ServiceException");
            request.setAttribute("message_erreur", e.getMessage());
            request.setAttribute("path", request.getServletPath()+"?"+(request.getQueryString()==null ? "":request.getQueryString()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        } catch (DaoException e) {
            request.setAttribute("localisation", "Lors de l'obtention de la liste d'utilisateurs");
            request.setAttribute("type_erreur", "DaoException");
            request.setAttribute("message_erreur", e.getMessage());
            request.setAttribute("path", request.getServletPath()+"?"+(request.getQueryString()==null ? "":request.getQueryString()));
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }

    }

}
