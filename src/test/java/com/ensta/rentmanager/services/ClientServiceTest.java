package com.ensta.rentmanager.services;


import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.Exceptions.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.Exceptions.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class ClientServiceTest {

    @Mock
    private ClientDao clientDao = new ClientDao();
    @InjectMocks
    private ClientService clientService = new ClientService(clientDao);

    Client clientConforme = new Client(1,"MOUTUIDINE", "Mourchid", "mourchid.moutuidine@epfedu.fr", LocalDate.of(2002, 3, 24));
    Client clientAvecNomVide = new Client("", "prenom", "email@example.com", LocalDate.now().minusYears(19));
    Client clientAvecPrenomVide = new Client("nom", "", "email@example.com", LocalDate.now().minusYears(19)); // Prénom vide
    Client clientMineur = new Client("nom", "prenom", "email@example.com", LocalDate.now().minusYears(17)); // Mineur

    @Test
    void create_should_return_id() throws ServiceException, DaoException {
        assertThat(clientService.create(clientConforme), instanceOf(Long.class));
    }
    @Test
    void create_should_throw_ServiceException_when_nom_is_empty() {
        assertThrows(ServiceException.class, () -> {
            clientService.create(clientAvecNomVide);
        });
    }
    @Test
    void create_should_throw_ServiceException_when_prenom_is_empty() {
        assertThrows(ServiceException.class, () -> {
            clientService.create(clientAvecPrenomVide);
        });
    }
    @Test
    void create_should_throw_ServiceException_when_email_is_invalid() {
        assertThrows(ServiceException.class, () -> {
            clientConforme.setEmail("");
            clientService.create(clientConforme);
        });
        assertThrows(ServiceException.class, () -> {
            clientConforme.setEmail("mail");
            clientService.create(clientConforme);
        });
        assertThrows(ServiceException.class, () -> {
            clientConforme.setEmail("mail@am");
            clientService.create(clientConforme);
        });
    }
    @Test
    void create_should_throw_ServiceException_when_client_is_minor() {
        assertThrows(ServiceException.class, () -> {
            clientService.create(clientMineur);
        });
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            clientService.create(clientMineur);
        });
        assertTrue(exception.getMessage().contains("L'utilisateur saisie doit etre majeur pour être inscrit"));
    }

    @Test
    void findById_should_throw_ServiceException_when_id_is_negative() {
        assertThrows(ServiceException.class, () -> {
            clientService.findById(-1);
        });
    }




}
