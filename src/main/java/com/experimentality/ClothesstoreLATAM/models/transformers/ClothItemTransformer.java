package com.experimentality.ClothesstoreLATAM.models.transformers;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

@Service
public class ClothItemTransformer implements AbstractTransformer<ClothItemDTO, ClothItem> {

	@Override
	public ClothItem toEntity(ClothItemDTO dto) {
		ClothItem entity = new ClothItem();
		entity.setIdClothItem(dto.getClothItemId());
		entity.setClothItemDescription(dto.getClothItemDescription());
		entity.setClothItemDiscount(dto.getClothItemDiscount());
		entity.setClothItemName(dto.getClothItemName());
		entity.setClothItemPrice(dto.getClothItemPrice());
		entity.setClothItemLookups(0L);
		if(dto.getClothItemImageUrls()!=null&&!dto.getClothItemImageUrls().isEmpty()) {
			entity.setClothItemImageUrls(dto.getClothItemImageUrls());	
		}else {
			entity.setClothItemImageUrls(new ArrayList<>());
		}
		return entity;
	}

	@Override
	public ClothItemDTO toDTO(ClothItem entity) {
		ClothItemDTO dto = new ClothItemDTO();
		dto.setClothItemId(entity.getIdClothItem());
		dto.setClothItemDescription(entity.getClothItemDescription());
		dto.setClothItemDiscount(entity.getClothItemDiscount());
		dto.setClothItemImageUrls(entity.getClothItemImageUrls());
		dto.setClothItemName(entity.getClothItemName());
		dto.setClothItemPrice(entity.getClothItemPrice());
		if(entity.getClothItemImageUrls()!=null&&!entity.getClothItemImageUrls().isEmpty()) {
			dto.setClothItemImageUrls(entity.getClothItemImageUrls());	
		}else {
			dto.setClothItemImageUrls(new ArrayList<>());
		}
		return dto;
	}
	
	public ClothItemDTO toDTOSimple(ClothItem entity) {
		ClothItemDTO dto = new ClothItemDTO();
		dto.setClothItemId(entity.getIdClothItem());
		dto.setClothItemName(entity.getClothItemName());
		dto.setClothItemPrice(entity.getClothItemPrice());
		dto.setClothItemDiscount(entity.getClothItemDiscount());
		dto.setClothItemImageUrls(entity.getClothItemImageUrls());
		if(entity.getClothItemImageUrls()!=null&&!entity.getClothItemImageUrls().isEmpty()) {
			dto.setClothItemImageUrls(entity.getClothItemImageUrls().subList(0, 2));	
		}else {
			dto.setClothItemImageUrls(new ArrayList<>());
		}
		return dto;
	}
}
