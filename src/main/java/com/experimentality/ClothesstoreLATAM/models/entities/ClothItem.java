package com.experimentality.ClothesstoreLATAM.models.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.sun.istack.NotNull;

/**
 * @author ccardozo
 *
 */
@Entity
@Table(name="cloth_items")
public class ClothItem {
	
	/**
	 * id de usuario automaticamente generado, no se debe proporcionar
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequence_cloth_items")
	private Long idClothItem;
	
	@NotNull
	@Column(length = 60)
	private String clothItemName;
	
	@NotNull
	@Column(length = 1024)
	private String clothItemDescription;
	
	@NotNull
	private BigDecimal clothItemPrice;
	
	@NotNull
	private Double clothItemDiscount;
	
	@ElementCollection
    @CollectionTable(name = "clothItemImageUrls", joinColumns = @JoinColumn(name = "idClothItem"))
    @Column(name = "clotheItemImageUrl")
	private List<String> clothItemImageUrls;
	
	private Long clothItemLookups;

	public Long getIdClothItem() {
		return idClothItem;
	}

	public void setIdClothItem(Long idClothItem) {
		this.idClothItem = idClothItem;
	}

	public String getClothItemName() {
		return clothItemName;
	}

	public void setClothItemName(String clothItemName) {
		this.clothItemName = clothItemName;
	}

	public String getClothItemDescription() {
		return clothItemDescription;
	}

	public void setClothItemDescription(String clothItemDescription) {
		this.clothItemDescription = clothItemDescription;
	}

	public BigDecimal getClothItemPrice() {
		return clothItemPrice;
	}

	public void setClothItemPrice(BigDecimal clothItemPrice) {
		this.clothItemPrice = clothItemPrice;
	}

	public Double getClothItemDiscount() {
		return clothItemDiscount;
	}

	public void setClothItemDiscount(Double clothItemDiscount) {
		this.clothItemDiscount = clothItemDiscount;
	}

	public List<String> getClothItemImageUrls() {
		return clothItemImageUrls;
	}

	public void setClothItemImageUrls(List<String> clothItemImageUrls) {
		this.clothItemImageUrls = clothItemImageUrls;
	}

	public Long getClothItemLookups() {
		return clothItemLookups;
	}

	public void setClothItemLookups(Long clothItemLookups) {
		this.clothItemLookups = clothItemLookups;
	}
}
