package com.experimentality.ClothesstoreLATAM.models.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Entity that represents the shopping cart on the store
 * @author ccardozo
 *
 */
@Entity
@Table(name="shoping_cart")
public class ShoppingCart {
	
	/**
	 * id de usuario automaticamente generado, no se debe proporcionar
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence_shopping_car")
	private Long idshoppingCart;
	
	/**
	 * list of the items inside the car, 
	 */
    @ManyToMany(fetch = FetchType.EAGER)
	private List<ClothItem> clothItems;

	//Getters&&Setters-----------
	public Long getIdshoppingCart() {
		return idshoppingCart;
	}

	public void setIdshoppingCart(Long idshoppingCart) {
		this.idshoppingCart = idshoppingCart;
	}

	public List<ClothItem> getClothItems() {
		return clothItems;
	}

	public void setClothItems(List<ClothItem> clothItems) {
		this.clothItems = clothItems;
	}
}
