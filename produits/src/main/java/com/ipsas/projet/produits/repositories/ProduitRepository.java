package com.ipsas.projet.produits.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ipsas.projet.produits.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
