package com.experimentality.ClothesstoreLATAM.models.transformers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.dtos.ShoppingCartDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;
import com.experimentality.ClothesstoreLATAM.models.entities.ShoppingCart;
/**
 * transformer of the object: shopping cart
 * @author ccardozo
 *
 */
@Service
public class ShoppingCartTransformer implements AbstractTransformer<ShoppingCartDTO, ShoppingCart> {

	/**
	 * transformer of the object: cloth item
	 */
	@Autowired
	private ClothItemTransformer clothItemTransformer;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public ShoppingCart toEntity(ShoppingCartDTO dto) {
		ShoppingCart entity = new ShoppingCart();
		entity.setIdshoppingCart(dto.getShoppingCartId());
		ArrayList<ClothItem> items = new ArrayList<ClothItem>();
		dto.getClothItems().stream().forEach((clothItemDTO)->{
			items.add(clothItemTransformer.toEntity(clothItemDTO));
			});
		entity.setClothItems(items);
		return entity;
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public ShoppingCartDTO toDTO(ShoppingCart entity) {
		ShoppingCartDTO dto = new ShoppingCartDTO();
		dto.setShoppingCartId(entity.getIdshoppingCart());
		ArrayList<ClothItemDTO> items = new ArrayList<ClothItemDTO>();
		entity.getClothItems().stream().forEach((clothItem)->{
			items.add(clothItemTransformer.toDTO(clothItem));
			});
		dto.setClothItems(items);
		return dto;
	}
}
