package fr.diginamic.entites;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MARQUE")
public class Marque {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="NOM", length=255, nullable=false)
	private String nom;
	
	@OneToMany(mappedBy = "marque")
	private Set<Produit> produits;

	public Marque() {
		super();
	}

	@Override
	public String toString() {
		return "Marque " + id + " [nom=" + nom + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Set<Produit> produits) {
		this.produits = produits;
	}
	
}
