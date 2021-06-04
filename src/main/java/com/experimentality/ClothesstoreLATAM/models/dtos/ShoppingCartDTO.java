package com.experimentality.ClothesstoreLATAM.models.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO of the shopping cart
 * @author ccardozo
 *
 */
public class ShoppingCartDTO {

	/**
	 * id of the shopping cart
	 */
	private Long shoppingCartId;
	/**
	 * list of items inside the cart
	 */
	private List<ClothItemDTO> clothItems;
	/**
	 * calculated total price of the items inside the cart
	 */
	private BigDecimal totalPrice;
	/**
	 * calculated discounted price of the items inside the cart
	 */
	private BigDecimal totalDiscountedPrice;
	
	//Getters&&Setters-----------                                                                                                     
	public Long getShoppingCartId() {
		return shoppingCartId;
	}
	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
	}
	public List<ClothItemDTO> getClothItems() {
		return clothItems;
	}
	public void setClothItems(List<ClothItemDTO> clothItems) {
		this.clothItems = clothItems;
	}
	public BigDecimal getTotalPrice() {
		this.totalPrice = BigDecimal.ZERO;
		for (ClothItemDTO clothItemDTO : clothItems) {
			totalPrice = totalPrice.add(clothItemDTO.getClothItemPrice());
		}
		return totalPrice;
	}
	public BigDecimal getTotalDiscountedPrice() {
		this.totalDiscountedPrice = BigDecimal.ZERO;
		for (ClothItemDTO clothItemDTO : clothItems) {
			totalDiscountedPrice = totalDiscountedPrice.add(clothItemDTO.getClothItemDiscountedPrice());
		}
		return totalDiscountedPrice;
	}
}
