package com.experimentality.ClothesstoreLATAM.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author ccardozo
 *
 */
@Entity
@Table(name="shoping_car")
public class ShoppingCar {
	
	/**
	 * id de usuario automaticamente generado, no se debe proporcionar
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence_shopping_car")
	private Long idshoppingCar;
	
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<ClothItem> clothItems;

	public Long getIdshoppingCar() {
		return idshoppingCar;
	}

	public void setIdshoppingCar(Long idshoppingCar) {
		this.idshoppingCar = idshoppingCar;
	}

	public List<ClothItem> getClothItems() {
		return clothItems;
	}

	public void setClothItems(List<ClothItem> clothItems) {
		this.clothItems = clothItems;
	}
}
