package fr.diginamic.entites;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORIE")
public class Categorie {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="LIBELLE", length=255, nullable=false)
	private String libelle;
	
	@OneToMany(mappedBy = "categorie")
	private Set<Produit> produits;

	public Categorie() {
		super();
	}

	@Override
	public String toString() {
		return "Categorie " + id + " [libelle=" + libelle + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Set<Produit> getProduits() {
		return produits;
	}

	public void setProduits(Set<Produit> produits) {
		this.produits = produits;
	}
	
}
