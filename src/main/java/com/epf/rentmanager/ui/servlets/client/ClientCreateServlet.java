package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;


@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final ClientService clientService = ClientService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        LocalDate date_of_birth = java.time.LocalDate.parse(request.getParameter("date_of_birth"));

        Client client = new Client(last_name,first_name,email,date_of_birth);
        try {
            this.clientService.create(client);
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException(e);
        }
    }
}
