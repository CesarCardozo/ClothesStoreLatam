package com.experimentality.ClothesstoreLATAM.Logic;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.experimentality.ClothesstoreLATAM.DAO.ClothItemRepository;
import com.experimentality.ClothesstoreLATAM.DAO.ShoppingCartRepository;
import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.dtos.ShoppingCartDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ShoppingCart;
import com.experimentality.ClothesstoreLATAM.models.transformers.ClothItemTransformer;
import com.experimentality.ClothesstoreLATAM.models.transformers.ShoppingCartTransformer;
import com.experimentality.ClothesstoreLATAM.models.utils.ValidatorOperations;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesExceptions;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseExceptions;

/**
 * Represent all the logic operations for shopping cart
 * @author ccardozo
 *
 */
@Service
public class ShoppingCartLogic implements AbstractValidator<ShoppingCartDTO, ShoppingCart> {

	/**
	 * shopping cart repository
	 */
	@Autowired
	private ShoppingCartRepository shoppingCarRepository;
	/**
	 * cloth item repository
	 */
	@Autowired
	private ClothItemRepository clothItemRepository;
	/**
	 * shopping cart transformer
	 */
	@Autowired
	private ShoppingCartTransformer shoppingCarTransformer;
	/**
	 * cloth item transformer
	 */
	@Autowired
	private ClothItemTransformer clothItemTransformer;
	
	/**
	 * method that adds a cloth item to a cart, if the cart id is not given, it will be created a new one
	 * @param idShoppingCar (can be null, on wich case it will be created a new one)
	 * @param idClothItem (id of the cloth item to be added to the car)
	 * @return the created or foun shoping cart inside
	 * @throws DataBaseException
	 * @throws BussinesException
	 */
	public ShoppingCartDTO addClothItemToShoppingCar(Long idShoppingCar, Long idClothItem) throws DataBaseException, BussinesException {
		if(idShoppingCar!=null) {
			return addClothItemToExistingShoppingCar(idShoppingCar, idClothItem);
		}else {
			return addClothItemToNewShoppingCar(idClothItem);
		}
	}
	
	/**
	 * Method that creates a new shopping cart an adds to it the cloth item 
	 * @param idClothItem id of the cloth item to be aded
	 * @return the new shopping cart
	 * @throws BussinesException
	 * @throws DataBaseException
	 */
	private ShoppingCartDTO addClothItemToNewShoppingCar(Long idClothItem) throws BussinesException, DataBaseException {
		ShoppingCartDTO shoppingCarDTO = new ShoppingCartDTO();
		ArrayList<ClothItemDTO> clothItems = new ArrayList<ClothItemDTO>();
		clothItems.add(clothItemTransformer.toDTO(clothItemRepository.findById(idClothItem).get()));
		shoppingCarDTO.setClothItems(clothItems);
		validateBussinesRules(shoppingCarDTO, ValidatorOperations.CREATION);
		ShoppingCart entity = shoppingCarTransformer.toEntity(shoppingCarDTO);
		validateDataRules(entity, ValidatorOperations.CREATION);	
		shoppingCarDTO = shoppingCarTransformer.toDTO(shoppingCarRepository.save(entity));
		return shoppingCarDTO;
	}
	
	/**
	 * Method that finds a shopping cart an adds the cloth item to it 
	 * @param idShoppingCar
	 * @param idClothItem
	 * @return
	 * @throws DataBaseException
	 * @throws BussinesException
	 */
	private ShoppingCartDTO addClothItemToExistingShoppingCar(Long idShoppingCar, Long idClothItem) throws DataBaseException, BussinesException {
		validateParameterBussinesRules(idShoppingCar, ValidatorOperations.SEARCH);
		ShoppingCart shoppingCar = new ShoppingCart();
		shoppingCar.setIdshoppingCart(idShoppingCar);
		validateDataRules(shoppingCar, ValidatorOperations.SEARCH);	
		shoppingCar = shoppingCarRepository.findById(idShoppingCar).get();
		validateDataRules(shoppingCar, ValidatorOperations.CREATION);	
		shoppingCar.getClothItems().add(clothItemRepository.findById(idClothItem).get());
		shoppingCarRepository.save(shoppingCar);
		//validar que no exista en el carrito el elemento
		return shoppingCarTransformer.toDTO(shoppingCar);

	}

	/**
	 * method that retrieves the shopping cart according to the given id
	 * @param idShoppingCar id of the shopping cart
	 * @return the shopping cart found
	 * @throws BussinesException
	 * @throws DataBaseException
	 */
	public ShoppingCartDTO checkShoppingCar(Long idShoppingCar) throws BussinesException, DataBaseException {
		validateParameterBussinesRules(idShoppingCar, ValidatorOperations.SEARCH);
		ShoppingCart shoppingCar = new ShoppingCart();
		shoppingCar.setIdshoppingCart(idShoppingCar);
		validateDataRules(shoppingCar, ValidatorOperations.SEARCH);
		shoppingCar = shoppingCarRepository.findById(idShoppingCar).get();
		return shoppingCarTransformer.toDTO(shoppingCar);
	}
	
	/**
	 * {@inheritdoc}
	 */
	@Override
	public void validateBussinesRules(ShoppingCartDTO dto, ValidatorOperations operation) throws BussinesException {
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
	
	/**
	 * {@inheritdoc}
	 */
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

	/**
	 * {@inheritdoc}
	 */
	@Override
	public void validateDataRules(ShoppingCart entity, ValidatorOperations operation) throws DataBaseException {
		switch (operation) {
		case CREATION:
			if(entity.getClothItems()==null || entity.getClothItems().size()<1
			|| entity.getClothItems().get(entity.getClothItems().size()-1).getIdClothItem()==null 
			|| !clothItemRepository.existsById(entity.getClothItems().get(entity.getClothItems().size()-1).getIdClothItem())) {
				throw new DataBaseException(DataBaseExceptions.NOT_FOUND);
			}
		break;
		case SEARCH:
			if(!shoppingCarRepository.existsById(entity.getIdshoppingCart())) {
				throw new DataBaseException(DataBaseExceptions.NOT_FOUND);
			}
		break;
		default:
			break;
		}
	}


	
}
