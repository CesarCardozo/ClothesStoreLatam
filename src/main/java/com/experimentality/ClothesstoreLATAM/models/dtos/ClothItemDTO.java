package com.experimentality.ClothesstoreLATAM.models.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO of the cloth items in the store
 * @author ccardozo
 *
 */
public class ClothItemDTO {

	/**
	 * id of the item
	 */
	private Long clothItemId;
	/**
	 * name of the item
	 */
	private String clothItemName;
	/**
	 * description of the item
	 */
	private String clothItemDescription;
	/**
	 * price of the item
	 */
	private BigDecimal clothItemPrice;
	/**
	 * discount of the item
	 */
	private Double clothItemDiscount;
	/**
	 * calculated discounted price of the item 
	 */
	private BigDecimal clothItemDiscountedPrice;
	/**
	 * list of images urls
	 */
	private List<String> clothItemImageUrls;
	
	//Getters&&Setters-----------                                                                                                     
	public Long getClothItemId() {
		return clothItemId;
	}
	public void setClothItemId(Long clothItemId) {
		this.clothItemId = clothItemId;
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
	public BigDecimal getClothItemDiscountedPrice() {
		clothItemDiscountedPrice = clothItemPrice.multiply(BigDecimal.valueOf(1-clothItemDiscount));
		return clothItemDiscountedPrice;
	}
	public List<String> getClothItemImageUrls() {
		return clothItemImageUrls;
	}
	public void setClothItemImageUrls(List<String> clothItemImageUrls) {
		this.clothItemImageUrls = clothItemImageUrls;
	}
}
