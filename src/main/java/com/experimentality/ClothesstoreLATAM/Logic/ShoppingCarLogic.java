package com.experimentality.ClothesstoreLATAM.Logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimentality.ClothesstoreLATAM.DAO.ClothItemRepository;
import com.experimentality.ClothesstoreLATAM.DAO.ShoppingCarRepository;
import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.dtos.ShoppingCarDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ShoppingCar;
import com.experimentality.ClothesstoreLATAM.models.transformers.ClothItemTransformer;
import com.experimentality.ClothesstoreLATAM.models.transformers.ShoppingCarTransformer;
import com.experimentality.ClothesstoreLATAM.models.utils.ValidatorOperations;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesExceptions;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseExceptions;

@Service
public class ShoppingCarLogic implements AbstractValidator<ShoppingCarDTO, ShoppingCar> {

	@Autowired
	private ShoppingCarRepository shoppingCarRepository;
	@Autowired
	private ClothItemRepository clothItemRepository;
	@Autowired
	private ShoppingCarTransformer shoppingCarTransformer;
	@Autowired
	private ClothItemTransformer clothItemTransformer;
	
	public ShoppingCarDTO addClothItemToShoppingCar(Long idShoppingCar, Long idClothItem) throws DataBaseException, BussinesException {
		if(idShoppingCar!=null) {
			return addClothItemToExistingShoppingCar(idShoppingCar, idClothItem);
		}else {
			return addClothItemToNewShoppingCar(idClothItem);
		}
	}
	
	private ShoppingCarDTO addClothItemToNewShoppingCar(Long idClothItem) throws BussinesException, DataBaseException {
		ShoppingCarDTO shoppingCarDTO = new ShoppingCarDTO();
		ArrayList<ClothItemDTO> clothItems = new ArrayList<ClothItemDTO>();
		clothItems.add(clothItemTransformer.toDTO(clothItemRepository.findById(idClothItem).get()));
		shoppingCarDTO.setClothItems(clothItems);
		validateBussinesRules(shoppingCarDTO, ValidatorOperations.CREATION);
		ShoppingCar entity = shoppingCarTransformer.toEntity(shoppingCarDTO);
		validateDataRules(entity, ValidatorOperations.CREATION);	
		shoppingCarDTO = shoppingCarTransformer.toDTO(shoppingCarRepository.save(entity));
		return shoppingCarDTO;
	}
	
	private ShoppingCarDTO addClothItemToExistingShoppingCar(Long idShoppingCar, Long idClothItem) throws DataBaseException, BussinesException {
		validateParameterBussinesRules(idShoppingCar, ValidatorOperations.SEARCH);
		ShoppingCar shoppingCar = new ShoppingCar();
		shoppingCar.setIdshoppingCar(idShoppingCar);
		validateDataRules(shoppingCar, ValidatorOperations.SEARCH);	
		shoppingCar = shoppingCarRepository.findById(idShoppingCar).get();
		validateDataRules(shoppingCar, ValidatorOperations.CREATION);	
		shoppingCar.getClothItems().add(clothItemRepository.findById(idClothItem).get());
		shoppingCarRepository.save(shoppingCar);
		//validar que no exista en el carrito el elemento
		return shoppingCarTransformer.toDTO(shoppingCar);

	}
	
	public ShoppingCarDTO checkShoppingCar(Long idShoppingCar) throws BussinesException, DataBaseException {
		validateParameterBussinesRules(idShoppingCar, ValidatorOperations.SEARCH);
		ShoppingCar shoppingCar = new ShoppingCar();
		shoppingCar.setIdshoppingCar(idShoppingCar);
		validateDataRules(shoppingCar, ValidatorOperations.SEARCH);
		shoppingCar = shoppingCarRepository.findById(idShoppingCar).get();
		return shoppingCarTransformer.toDTO(shoppingCar);
	}
	
	@Override
	public void validateBussinesRules(ShoppingCarDTO dto, ValidatorOperations operation) throws BussinesException {
		switch (operation) {
		case CREATION:
			if(dto.getClothItems()==null || dto.getClothItems().size()<1 || dto.getClothItems().get(0).getClothItemId()==null 
			|| dto.getClothItems().get(0).getClothItemId()<1) {
				throw new BussinesException(BussinesExceptions.ID_NOT_ACCEPTABLE);
			}
		break;
		default:
			break;
		}
	}

	@Override
	public void validateParameterBussinesRules(Object o, ValidatorOperations operation) throws BussinesException {
		switch (operation) {
		case SEARCH:
			if(o == null || (Long)o<0L) {
				throw new BussinesException(BussinesExceptions.OBLIGATORY_FIELDS_MISSING_REQUEST);
			}
		break;
		default:
			break;
		}
	}

	@Override
	public void validateDataRules(ShoppingCar entity, ValidatorOperations operation) throws DataBaseException {
		switch (operation) {
		case CREATION:
			if(entity.getClothItems()==null || entity.getClothItems().size()<1
			|| entity.getClothItems().get(entity.getClothItems().size()-1).getIdClothItem()==null 
			|| !clothItemRepository.existsById(entity.getClothItems().get(entity.getClothItems().size()-1).getIdClothItem())) {
				throw new DataBaseException(DataBaseExceptions.NOT_FOUND);
			}
		break;
		case SEARCH:
			if(!shoppingCarRepository.existsById(entity.getIdshoppingCar())) {
				throw new DataBaseException(DataBaseExceptions.NOT_FOUND);
			}
		break;
		default:
			break;
		}
	}


	
}
