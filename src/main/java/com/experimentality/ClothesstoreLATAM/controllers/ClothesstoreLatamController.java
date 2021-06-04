package com.experimentality.ClothesstoreLATAM.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.experimentality.ClothesstoreLATAM.Logic.ClothItemLogic;
import com.experimentality.ClothesstoreLATAM.Logic.ShoppingCartLogic;
import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

/**
 * Contains the endpoints for the clothes store
 * @author ccardozo
 *
 */
@RestController
public class ClothesstoreLatamController {

	@GetMapping(value = "/holi")
	public String  hello() {
		return "HelloWorld";
	}
	
	/**
	 * logic of the cloth items
	 */
	@Autowired
	private ClothItemLogic clothItemLogic;
	/**
	 * logic of the shopping cart
	 */
	@Autowired
	private ShoppingCartLogic shoppingCarLogic;


	/**
	 * Get service that retrieves the more searched for cloth items first
	 * @param paginationSize (can be null, on witch case, retrieves all the clothe items)
	 * @param currentPage (can be null, on witch case, retrieves all the clothe items)
	 * @return list of more searched cloth items
	 */
	@GetMapping(value = "/clothItems/moreSearchedFor")
	public ResponseEntity<?>  getClothItemsByLookups(@RequestParam(required = false) Integer paginationSize, @RequestParam(required = false) Integer currentPage) {
		return new ResponseEntity<>(clothItemLogic.getClothItemsByLookups(paginationSize, currentPage),HttpStatus.OK);
	}
	
	/**
	 * Get service that retrieves all the cloth items on which the name matches the keywords given
	 * @param keywords to filter for the cloth items
	 * @param paginationSize (can be null, on witch case, retrieves all the clothe items)
	 * @param currentPage (can be null, on witch case, retrieves all the clothe items)
	 * @return list of cloth items that matches the keywords
	 */
	@GetMapping(value = "/clothItems")
	public ResponseEntity<?> getClothItems(@RequestParam String keywords, @RequestParam(required = false) Integer paginationSize, @RequestParam(required = false) Integer currentPage) {  
		try {
			return new ResponseEntity<>(clothItemLogic.getClothItemsByKeywords(keywords, paginationSize, currentPage), HttpStatus.OK);
		} catch (BussinesException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Get service that retrieves the cloth item by the id given
	 * @param idClothItem id of the cloth item
	 * @return cloth item found
	 */
	@GetMapping(value = "/clothItem")
	public ResponseEntity<?>  getClothItemById(@RequestParam Long idClothItem) {
		try {
			return new ResponseEntity<>(clothItemLogic.getClothItemById(idClothItem),HttpStatus.OK);
		} catch (BussinesException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Post service that adds a cloth item to the DB
	 * @param clothItemDTO to be added
	 * @return Http status
	 */
	@PostMapping(value = "/clothItem")
	public ResponseEntity<?> addClothItem(@RequestBody ClothItemDTO clothItemDTO) {
		try {
			clothItemLogic.addClothItem(clothItemDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BussinesException | DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}	
	
	/**
	 * Get service that add a cloth item to a shopping cart
	 * @param idShoppingCar (can be null, on which case it will be created a new one)
	 * @param idClothItem id of the item to be added to the cart
	 * @return the cart created/found with all its items inside
	 */
	@GetMapping(value = "/addToCart")
	public ResponseEntity<?> addClothItemToCart(@RequestParam(required = false) Long idShoppingCar, @RequestParam Long idClothItem) {
		try {
			return new ResponseEntity<>(shoppingCarLogic.addClothItemToShoppingCar(idShoppingCar, idClothItem), HttpStatus.OK);
		} catch (BussinesException | DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Get service that check the status of a shopping cart
	 * @param idShopingCart id of the shopping cart
	 * @return the shopping cart found
	 */
	@GetMapping(value = "/shoppingCart")
	public ResponseEntity<?>  checkShoppingCart(@RequestParam Long idShopingCart) {
		try {
			return new ResponseEntity<>(shoppingCarLogic.checkShoppingCar(idShopingCart),HttpStatus.OK);
		} catch (BussinesException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		} catch (DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
