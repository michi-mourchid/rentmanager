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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents")
public class RentListServlet extends HttpServlet {

    private ReservationService reservationService;
    private ClientService clientService;
    private VehicleService vehicleService;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Reservation> reservations = this.reservationService.findAll();
            List<ReservationView> reservationViews = new ArrayList<>();
            for(Reservation reservation: reservations){
                int clientId = Integer.parseInt(reservation.getClient_id());
                int vehicleId = Integer.parseInt(reservation.getVehicle_id());

                Client client = this.clientService.findById(clientId);
                Vehicle vehicle = this.vehicleService.findById(vehicleId);

                String description_client = ""+client.getPrenom()+ " "+ client.getNom();
                String description_vehicle = ""+vehicle.getConstructeur()+ " "+ vehicle.getModele();

                LocalDate debut = reservation.getDebut().toLocalDate();
                LocalDate fin = reservation.getFin().toLocalDate();

                reservationViews.add(new ReservationView(reservation.getId(),clientId,description_client,vehicleId,description_vehicle,debut,fin));
            }

            request.setAttribute("rents", reservationViews);
            this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);
        } catch (DaoException | ServiceException e) {
            throw new RuntimeException(e);
        }

    }

    private class ReservationView {
        private int id;
        private int client_id;
        private String description_client;
        private int vehicle_id;
        private String description_vehicle;
        private LocalDate debut;
        private LocalDate fin;

        public ReservationView(int id, int client_id, String description_client, int vehicle_id, String description_vehicle, LocalDate debut, LocalDate fin) {
            this.id = id;
            this.client_id = client_id;
            this.description_client = description_client;
            this.vehicle_id = vehicle_id;
            this.description_vehicle = description_vehicle;
            this.debut = debut;
            this.fin = fin;
        }
    }

}
