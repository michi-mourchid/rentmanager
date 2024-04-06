package com.epf.rentmanager.ui.servlets.vehicle;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet {
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
            this.vehicleService.delete(id);
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/cars");
    }
}
