package com.ipsas.projet.factures.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ipsas.projet.factures.entities.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

	@Query("SELECT SUM(f.reste) FROM Facture f")
	float getUnpaidTotal();
	
	@Query("SELECT SUM(f.reste) FROM Facture f WHERE f.clientId = ?1")
	float getClientUnpaid(long clientId);
	
	@Query("SELECT SUM(f.prixTTC) FROM Facture f")
	float getAllAffaires();
	
	@Query("SELECT SUM(f.prixTTC) FROM Facture f where YEAR(f.date) = ?1")
	float getAllAffairesByYear();
	
	@Query("SELECT SUM(f.prixTTC) FROM Facture f where clientId = ?1")
	float getAllAffairesByClient(long clientId);
	
	@Query("SELECT SUM(f.prixTTC) FROM Facture f where clientId = ?1 and  YEAR(f.date) = ?2")
	float getAllAffairesByClientAndYear(long clientId, int year);
	
	@Query("SELECT f FROM Facture f where reste = 0")
	List<Facture> getAllPaid();
	
	@Query("SELECT u FROM Facture f where reste > 0")
	List<Facture> getAllUnpaid();
}
