package fr.diginamic.service;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.entites.Categorie;
import fr.diginamic.entites.Produit;
import fr.diginamic.exceptions.ExceptionCategorie;

public class MeilleurProduitParCategorie extends Service {

	@Override
	public void traiter(Scanner scanner) throws ExceptionCategorie {

		EntityManagerFactory factory = null;
		factory = Persistence.createEntityManagerFactory("open-food");
		EntityManager em = factory.createEntityManager();
		
		//Réalisez une requête qui permet d’extraire les marques
				TypedQuery<Categorie> query = em.createQuery("SELECT c FROM Categorie c", Categorie.class);
				List<Categorie> listCat = query.getResultList();				
		
		System.out.println("Veuillez saisir une catégorie:");
		String nomCat = scanner.nextLine();
		
		if (!((Categorie) listCat).getLibelle().contains(nomCat)) {
			throw new ExceptionCategorie();
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
		
		//close connection
		em.close();
		factory.close();
		
		for (Produit p: listPrd) {
			if (p.getCategorie().getLibelle().equals(idCat) && p.getScoreNutritionnel().equals("a")) {
				System.out.println("Le produit " + p.getNom() + " de la catégorie " + p.getCategorie().getLibelle() + " a un score nutritionnel de " + p.getScoreNutritionnel() +".");
			}
		}
	}

}
