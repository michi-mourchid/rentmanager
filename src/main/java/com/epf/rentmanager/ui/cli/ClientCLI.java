package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.*;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientCLI {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    private static final ClientService clientService = context.getBean(ClientService.class);

    public static void createClient() {
        IOUtils.print("\n### Création d'un client ###");
        String nom = IOUtils.readString("Nom : ",true);
        String prenom = IOUtils.readString("Prénom : ", true);
        String email = IOUtils.readString("Email : ", true);
        LocalDate naissance = IOUtils.readDate("Date de naissance (format dd/MM/yyyy) : ", true);

        try {
            long clientId = clientService.create(new Client(nom, prenom, email, naissance));
            IOUtils.print("Client créé avec succès ! (ID : " + clientId + ")");
        } catch (ServiceException serviceException) {
            IOUtils.print("Erreur lors de la création du client : " + serviceException.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static void listClients() throws ServiceException, DaoException {
        List<Client> clients = clientService.findAll();
        if (!clients.isEmpty()) {
            for (Client client : clients) {
                IOUtils.print(client.toString());
            }
        } else {
            IOUtils.print("Aucun client trouvé.");
        }
    }

    public static void deleteClient() {
        IOUtils.print("\n### Suppression d'un client ###");
        int clientId = IOUtils.readInt("ID du client à supprimer : ");

        try {
            if (clientId > 0) {
                clientService.delete(clientId);
                IOUtils.print("Client supprimé avec succès !");
            } else {
                IOUtils.print("Aucun client trouvé avec l'ID spécifié.");
            }
        } catch (ServiceException e) {
            IOUtils.print("Erreur lors de la suppression du client : " + e.getMessage());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

}
