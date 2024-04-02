package com.epf.rentmanager.ui.servlets.vehicle;

import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.model.Vehicle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet {

    private VehicleDao vehicleDao;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Vehicle> vehicles = this.vehicleDao.findAll();

            request.setAttribute("vehicles", vehicles);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicle/list.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }

}
