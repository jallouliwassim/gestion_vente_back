package com.ipsas.projet.factures.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ipsas.projet.factures.entities.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

}
