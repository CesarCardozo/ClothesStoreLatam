package com.experimentality.ClothesstoreLATAM.models.dtos;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCarDTO {

	private Long shoppingCarId;
	private List<ClothItemDTO> clothItems;
	private BigDecimal totalPrice;
	private BigDecimal totalDiscountedPrice;
	
	public Long getShoppingCarId() {
		return shoppingCarId;
	}
	public void setShoppingCarId(Long shoppingCarId) {
		this.shoppingCarId = shoppingCarId;
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
