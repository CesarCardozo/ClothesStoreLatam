package com.experimentality.ClothesstoreLATAM.models.transformers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.dtos.ShoppingCarDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;
import com.experimentality.ClothesstoreLATAM.models.entities.ShoppingCar;

@Service
public class ShoppingCarTransformer implements AbstractTransformer<ShoppingCarDTO, ShoppingCar> {

	@Autowired
	private ClothItemTransformer clothItemTransformer;

	@Override
	public ShoppingCar toEntity(ShoppingCarDTO dto) {
		ShoppingCar entity = new ShoppingCar();
		entity.setIdshoppingCar(dto.getShoppingCarId());
		ArrayList<ClothItem> items = new ArrayList<ClothItem>();
		dto.getClothItems().stream().forEach((clothItemDTO)->{
			items.add(clothItemTransformer.toEntity(clothItemDTO));
			});
		entity.setClothItems(items);
		return entity;
	}

	@Override
	public ShoppingCarDTO toDTO(ShoppingCar entity) {
		ShoppingCarDTO dto = new ShoppingCarDTO();
		dto.setShoppingCarId(entity.getIdshoppingCar());
		ArrayList<ClothItemDTO> items = new ArrayList<ClothItemDTO>();
		entity.getClothItems().stream().forEach((clothItem)->{
			items.add(clothItemTransformer.toDTO(clothItem));
			});
		dto.setClothItems(items);
		return dto;
	}
}
