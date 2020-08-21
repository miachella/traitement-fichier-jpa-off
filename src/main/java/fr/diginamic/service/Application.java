package fr.diginamic.service;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.diginamic.entites.Additif;
import fr.diginamic.entites.Allergene;
import fr.diginamic.entites.Categorie;
import fr.diginamic.entites.Ingredient;
import fr.diginamic.entites.Marque;
import fr.diginamic.entites.Produit;
import fr.diginamic.exceptions.ExceptionMarque;
import fr.diginamic.utils.Lecteur;



public class Application {

	public static void main(String[] args) throws Exception, ExceptionMarque {
		Scanner scanner = new Scanner(System.in);
		EntityManagerFactory factory = null;
		factory = Persistence.createEntityManagerFactory("open-food");
		EntityManager em = factory.createEntityManager();


		String filePath = ClassLoader.getSystemClassLoader().getResource("open-food-facts.csv").getFile();
		List<String> lignes = Lecteur.lire(filePath);

		if (lignes == null) {
			System.out.println("L'application doit s'arrêter en raison d'une erreur d'exécution.");
			System.exit(-1);
		} 
		
		Set<Categorie> setCategorie = Lecteur.ajouterCategorie(lignes);
		Iterator<Categorie> iterCat = setCategorie.iterator();
		while (iterCat.hasNext()) {
			Categorie cat = iterCat.next();
			
			//maj bdd
			em.getTransaction().begin();
			em.persist(cat);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();
		}
		
		Set<Marque> setMarque = Lecteur.ajouterMarque(lignes);
		Iterator<Marque> iterMrq = setMarque.iterator();
		while (iterMrq.hasNext()) {
			Marque mrq = iterMrq.next();

			//maj bdd
			em.getTransaction().begin();
			em.persist(mrq);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();
		}
		
		Set<Ingredient> setIngredient = Lecteur.ajouterIngredient(lignes);
		Iterator<Ingredient> iterIgr = setIngredient.iterator();
		while (iterIgr.hasNext()) {
			Ingredient igr = iterIgr.next();

			//maj bdd
			em.getTransaction().begin();
			em.persist(igr);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();		}
		
		Set<Allergene> setAllergene = Lecteur.ajouterAllergene(lignes);
		Iterator<Allergene> iterAll = setAllergene.iterator();
		while (iterAll.hasNext()) {
			Allergene all = iterAll.next();

			//maj bdd
			em.getTransaction().begin();
			em.persist(all);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();		}
		
		Set<Additif> setAdditif = Lecteur.ajouterAdditif(lignes);
		Iterator<Additif> iterAdd = setAdditif.iterator();
		while (iterAdd.hasNext()) {
			Additif add = iterAdd.next();

			//maj bdd
			em.getTransaction().begin();
			em.persist(add);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();
		}
		
		Set<Produit> setProduit = Lecteur.ajouterProduit(lignes);
		Iterator<Produit> iterPrd = setProduit.iterator();
		while (iterPrd.hasNext()) {
			Produit prd = iterPrd.next();

			//maj bdd
			em.getTransaction().begin();
			em.persist(prd);
			em.getTransaction().commit();
			
			//close connection
			em.close();
			factory.close();		}

		// Menu
		int choix = 0;
		do {

			// Affichage du menu
			afficherMenu();

			// Poser une question à l'utilisateur
			String choixMenu = scanner.nextLine();

			// Conversion du choix utilisateur en int
			choix = Integer.parseInt(choixMenu);

			// On exécute l'option correspondant au choix de l'utilisateur
			switch (choix) {
			case 1:
				MeilleurProduitParMarque meillPrdMrq = new MeilleurProduitParMarque();
				meillPrdMrq.traiter(scanner);
				break;
			case 2:
				MeilleurProduitParCategorie meillPrdCat = new MeilleurProduitParCategorie();
				meillPrdCat.traiter(scanner);
				break;
			case 3:
				MeilleurProduitParCategorieParMarque meillPrdCatMrq = new MeilleurProduitParCategorieParMarque();
				meillPrdCatMrq.traiter(scanner);
				break;
			case 4:
				AllergeneCourant allCourant = new AllergeneCourant();
				allCourant.traiter(scanner);
				break;
			case 5:
				AdditifCourant addCourant = new AdditifCourant();
				addCourant.traiter(scanner);
				break;
			}

		} while (choix != 99);

		scanner.close();

	}

	/**
	 * Affichage du menu
	 */
	private static void afficherMenu() {
		System.out.println("***** Application Open Food Facts *****");
		System.out.println("1. Rechercher les meilleurs produits pour une marque donnée");
		System.out.println("2. Rechercher les meilleurs produits pour une catégorie donnée");
		System.out.println("3. Rechercher les meilleurs produits par marque et par catégorie");
		System.out.println("4. Rechercher les allergènes les plus courants");
		System.out.println("5. Rechercher les additifs les plus courants");
		System.out.println("99. Sortir");
	}

}
