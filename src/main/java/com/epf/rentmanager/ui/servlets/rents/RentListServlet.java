package com.epf.rentmanager.ui.servlets.client;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class RentListServlet extends HttpServlet {

    private ClientService clientService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Client> clients = this.clientService.findAll();

            request.setAttribute("clients", clients);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (DaoException | ServiceException e) {
            throw new RuntimeException(e);
        }

    }

}
