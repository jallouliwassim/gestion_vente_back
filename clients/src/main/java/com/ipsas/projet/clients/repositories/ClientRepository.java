package com.ipsas.projet.clients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import com.ipsas.projet.clients.entities.Client;

@RepositoryRestController
public interface ClientRepository extends JpaRepository<Client, Long> {

}
