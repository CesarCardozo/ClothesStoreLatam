package com.experimentality.ClothesstoreLATAM.DAO;

import org.springframework.data.repository.CrudRepository;

import com.experimentality.ClothesstoreLATAM.models.entities.ShoppingCart;

/**
 * Interface for generic CRUD operations on a repository for {@link ShoppingCart}
 * @author ccardozo
 *
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{
	
}
