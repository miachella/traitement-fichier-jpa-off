package fr.diginamic.entites;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="INGREDIENT")
public class Ingredient {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="LIBELLE", length=255, nullable=false)
	private String libelle;
	
	@ManyToMany
	@JoinTable(name="PRD_IGR",
			joinColumns=@JoinColumn(name="ID_IGR", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="ID_PRD", referencedColumnName="ID"))
	private Set<Produit> produits;

	public Ingredient() {
		super();
	}

	@Override
	public String toString() {
		return "Ingredient " + id + " [libelle=" + libelle + "]";
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
