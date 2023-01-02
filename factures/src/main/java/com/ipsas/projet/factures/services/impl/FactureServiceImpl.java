package com.ipsas.projet.factures.services.impl;

import java.util.Collection;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ipsas.projet.factures.entities.Client;
import com.ipsas.projet.factures.entities.Facture;
import com.ipsas.projet.factures.entities.LigneFacture;
import com.ipsas.projet.factures.entities.Produit;
import com.ipsas.projet.factures.repositories.FactureRepository;
import com.ipsas.projet.factures.services.ClientService;
import com.ipsas.projet.factures.services.FactureService;
import com.ipsas.projet.factures.services.LigneFactureService;
import com.ipsas.projet.factures.services.ProduitService;

@Service
@Transactional
public class FactureServiceImpl implements FactureService {

    @Autowired
    FactureRepository factureRepository;
    
    @Autowired
    ClientService clientService;
    
    @Autowired
    ProduitService produitService;
    
    @Autowired
    LigneFactureService ligneFactureService;

    @Override
    public Facture save(Facture facture) {
        // TODO Auto-generated method stub
    	var wrapper = new Object(){ float  tva = 0; float ht = 0; float ttc = 0; };

    	facture.getLignes().stream().forEach(ligne -> {
    		
    		wrapper.tva += ligne.getPrixTVA();
    		wrapper.ht += ligne.getPrixHT();
    		wrapper.ttc += ligne.getPrixTTC();
    	});
    	
    	facture.setPrixHT(wrapper.ht);
    	facture.setPrixTVA(wrapper.tva);
    	facture.setPrixTTC(wrapper.ttc);
    	
    	Collection<LigneFacture> ligneFactures = facture.getLignes();
	   	 Facture saved = this.factureRepository.save(facture);
	   	
	   	ligneFactures.forEach(ligne -> {
	   		ligne.setFacture(saved);
	   		this.ligneFactureService.save(ligne);
	   	});
        return saved;
    }

    @Override
    public Facture update(Long id, Facture facture) {
        // TODO Auto-generated method stub
    	var wrapper = new Object(){ float  tva = 0; float ht = 0; float ttc = 0; };

    	facture.getLignes().stream().forEach(ligne -> {
    		
    		wrapper.tva += ligne.getPrixTVA();
    		wrapper.ht += ligne.getPrixHT();
    		wrapper.ttc += ligne.getPrixTTC();
    	});
    	
    	facture.setPrixHT(wrapper.ht);
    	facture.setPrixTVA(wrapper.tva);
    	facture.setPrixTTC(wrapper.ttc);
    	facture.setId(id);
        return this.factureRepository.save(facture);
    }

    @Override
    public Page<Facture> findAll(int pageNo, int pageSize, String sortBy) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        
        Page<Facture> factures = this.factureRepository.findAll(pageable);
        factures.forEach(facture -> {
        	Client client = this.clientService.findClientById( facture.getClientId() );
        	facture.setClient(client);
        	
        	facture.getLignes().forEach(ligne -> {
        		Produit produit = this.produitService.findProduitById(ligne.getProduitId());
        		ligne.setProduit(produit);
        	});
        	
        });
        
    	

        return this.factureRepository.findAll(pageable);
    }

    @Override
    public Optional<Facture> findOne(Long id) {
        // TODO Auto-generated method stub
    	Optional<Facture> facture = this.factureRepository.findById(id);
    	Client client = this.clientService.findClientById( facture.get().getClientId() );
    	facture.get().setClient(client);
    	
    	facture.get().getLignes().forEach(ligne -> {
    		Produit produit = this.produitService.findProduitById(ligne.getProduitId());
    		ligne.setProduit(produit);
    	});
        return facture;
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        this.factureRepository.deleteById(id);
    }

}
