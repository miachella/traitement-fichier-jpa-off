package fr.diginamic.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.entites.Additif;
import fr.diginamic.entites.Produit;

public class AdditifCourant extends Service {

	@Override
	public void traiter(Scanner scanner) throws Exception {
		
		EntityManagerFactory factory = null;
		factory = Persistence.createEntityManagerFactory("open-food");
		EntityManager em = factory.createEntityManager();
		
		//Réalisez une requête qui permet d’extraire les produits
				TypedQuery<Produit> queryPrd = em.createQuery("SELECT p FROM Produits p", Produit.class);
				List<Produit> listPrd = queryPrd.getResultList();
		
		HashMap<String, Integer> mapAdditif = new HashMap<String, Integer>();
		
		/*
		 * Création de la map d'Additifs avec décompte
		 */
		
		Iterator<Produit> iter = listPrd.iterator();
		while (iter.hasNext()){
			List<Additif> addList = iter.next().getAdditifs();
			
			for(Additif add: addList) {
				Integer counter = mapAdditif.get(add.getLibelle());
				if (counter==null) {
					counter = 1;
					mapAdditif.put(add.getLibelle(), counter);
				} else {
					counter = counter + 1;
					mapAdditif.put(add.getLibelle(), counter);
				}
			}	
		}
		
		//close connection
				em.close();
				factory.close();
		
		System.out.println("Veuillez saisir le nombre des allergènes les plus courants:");
		String nombreAdd = scanner.nextLine();
		
		if (!NumberUtils.isDigits(nombreAdd) || Integer.parseInt(nombreAdd) < 0 || Integer.parseInt(nombreAdd) > mapAdditif.size()) {
			throw new Exception();
		}
		
		/*
		 * 
		 */
		int i = 0;
		do {
			Integer nbMax = 0;
			String addMax = null;
			Iterator<String> keysIte = mapAdditif.keySet().iterator();
			while (keysIte.hasNext()){
				String all = keysIte.next();
				if (mapAdditif.get(all) > nbMax) {
					nbMax = mapAdditif.get(all);
					addMax = all;
				}
			}
			System.out.println("Le " + (i + 1) + "e additif le plus courant est " + addMax + ".");
			mapAdditif.remove(addMax);
			nbMax = 0;
			addMax = null;
			i++;
			
		} while (i < Integer.parseInt(nombreAdd));
	
	}

}
