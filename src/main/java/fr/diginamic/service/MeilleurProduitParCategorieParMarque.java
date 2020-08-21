package fr.diginamic.service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entites.Categorie;
import fr.diginamic.entites.Marque;
import fr.diginamic.entites.Produit;
import fr.diginamic.exceptions.ExceptionCategorie;
import fr.diginamic.exceptions.ExceptionMarque;

public class MeilleurProduitParCategorieParMarque extends Service {

	@Override
	public void traiter(Scanner scanner) throws Exception {
		
		EntityManagerFactory factory = null;
		factory = Persistence.createEntityManagerFactory("open-food");
		EntityManager em = factory.createEntityManager();
		
		//Réalisez une requête qui permet d’extraire les marques
				TypedQuery<Marque> queryMrq = em.createQuery("SELECT m FROM Marque m", Marque.class);
				List<Marque> listMrq = queryMrq.getResultList();
				
		//Réalisez une requête qui permet d’extraire les marques
				TypedQuery<Categorie> queryCat = em.createQuery("SELECT c FROM Categorie c", Categorie.class);
				List<Categorie> listCat = queryCat.getResultList();
		
		System.out.println("Veuillez saisir une catégorie:");
		String nomCat = scanner.nextLine();
		System.out.println("Veuillez saisir une marque:");
		String nomMrq = scanner.nextLine();
		
		if (!((Categorie) listCat).getLibelle().contains(nomCat)) {
			throw new ExceptionCategorie();
		}
		
		if (!((Marque) listMrq).getNom().contains(nomMrq)) {
			throw new ExceptionMarque();
		}
		
		//Réalisez une requête qui permet d’extraire les produits
				TypedQuery<Produit> queryPrd = em.createQuery("SELECT p FROM Produits p", Produit.class);
				List<Produit> listPrd = queryPrd.getResultList();

		//Récupérer l'ID Cat
		String idCat = null;
		for (Categorie cat: listCat) {
			if (nomCat.equals(cat.getLibelle())) {
				idCat = cat.getLibelle();
			}
		}
		
		//Récupérer l'ID Mrq
				String idMrq = null;
				for (Marque mrq: listMrq) {
					if (nomMrq.equals(mrq.getNom())) {
						idMrq = mrq.getNom();
					}
				}
				
		//close connection
		em.close();
		factory.close();
		
		for (Produit p: listPrd) {
			if (p.getCategorie().getLibelle().equals(idCat) && p.getMarque().getNom().equals(idMrq) && p.getScoreNutritionnel().equals("a")) {
				System.out.println("Le produit " + p.getNom() + " de la marque " + p.getMarque().getNom() + " et de la catégorie " + p.getCategorie().getLibelle() + " a un score nutritionnel de " + p.getScoreNutritionnel() +".");
			}
		}
	}

}
