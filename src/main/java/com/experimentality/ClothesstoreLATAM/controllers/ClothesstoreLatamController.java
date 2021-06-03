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
import com.experimentality.ClothesstoreLATAM.Logic.ShoppingCarLogic;
import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

@RestController
public class ClothesstoreLatamController {

	@Autowired
	private ClothItemLogic clothItemLogic;	
	@Autowired
	private ShoppingCarLogic shoppingCarLogic;


	@GetMapping(value = "/clothItems/moreSearchedFor")
	public ResponseEntity<?>  getClothItemsByLookups(@RequestParam(required = false) Integer paginationSize, @RequestParam(required = false) Integer currentPage) {
		return new ResponseEntity<>(clothItemLogic.getClothItemsByLookups(paginationSize, currentPage),HttpStatus.OK);
	}
	
	@GetMapping(value = "/clothItems")
	public ResponseEntity<?> getClothItems(@RequestParam String keywords, @RequestParam(required = false) Integer paginationSize, @RequestParam(required = false) Integer currentPage) {  
		try {
			return new ResponseEntity<>(clothItemLogic.getClothItems(keywords, paginationSize, currentPage), HttpStatus.OK);
		} catch (BussinesException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

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

	@PostMapping(value = "/clothItem")
	public ResponseEntity<?> addClothItem(@RequestBody ClothItemDTO clothItemDTO) {
		try {
			clothItemLogic.addClothItem(clothItemDTO);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (BussinesException | DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping(value = "/addToCart")
	public ResponseEntity<?> addClothItemToCart(@RequestParam(required = false) Long idShoppingCar, @RequestParam Long idClothItem) {
		try {
			return new ResponseEntity<>(shoppingCarLogic.addClothItemToShoppingCar(idShoppingCar, idClothItem), HttpStatus.OK);
		} catch (BussinesException | DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
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
