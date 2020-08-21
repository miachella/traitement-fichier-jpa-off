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

import fr.diginamic.entites.Allergene;
import fr.diginamic.entites.Produit;

public class AllergeneCourant extends Service {

	@Override
	public void traiter(Scanner scanner) throws Exception {
		
		EntityManagerFactory factory = null;
		factory = Persistence.createEntityManagerFactory("open-food");
		EntityManager em = factory.createEntityManager();
		
		//Réalisez une requête qui permet d’extraire les produits
		TypedQuery<Produit> queryPrd = em.createQuery("SELECT p FROM Produits p", Produit.class);
		List<Produit> listPrd = queryPrd.getResultList();
		
		HashMap<String, Integer> mapAllergene = new HashMap<String, Integer>();
		
		/*
		 * Création de la map d'allergènes avec décompte
		 */
		
		Iterator<Produit> iter = listPrd.iterator();
		while (iter.hasNext()){
			List<Allergene> allList = iter.next().getAllergenes();
			
			for(Allergene all: allList) {
				Integer counter = mapAllergene.get(all.getLibelle());
				if (counter==null) {
					counter = 1;
					mapAllergene.put(all.getLibelle(), counter);
				} else {
					counter = counter + 1;
					mapAllergene.put(all.getLibelle(), counter);
				}
			}	
		}
		
		//close connection
				em.close();
				factory.close();
		
		System.out.println("Veuillez saisir le nombre des allergènes les plus courants:");
		String nombreAll = scanner.nextLine();
		
		if (!NumberUtils.isDigits(nombreAll) || Integer.parseInt(nombreAll) < 0 || Integer.parseInt(nombreAll) > mapAllergene.size()) {
			throw new Exception();
		}
		
		/*
		 * 
		 */
		int i = 0;
		do {
			Integer nbMax = 0;
			String allMax = null;
			Iterator<String> keysIte = mapAllergene.keySet().iterator();
			while (keysIte.hasNext()){
				String all = keysIte.next();
				if (mapAllergene.get(all) > nbMax) {
					nbMax = mapAllergene.get(all);
					allMax = all;
				}
			}
			System.out.println("Le " + (i + 1) + "e allergène le plus courant est " + allMax + ".");
			mapAllergene.remove(allMax);
			nbMax = 0;
			allMax = null;
			i++;
			
		} while (i < Integer.parseInt(nombreAll));
	
	}

}
