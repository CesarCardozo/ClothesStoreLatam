package com.experimentality.ClothesstoreLATAM.Logic;

import com.experimentality.ClothesstoreLATAM.models.utils.ValidatorOperations;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

/**
 * interface that stablishes the basic validations of a logic module
 * @author ccardozo
 *
 * @param <T> DTO of the object of who the logic belongs
 * @param <G> Entity of the object of who the logic belongs
 */
public interface AbstractValidator <T,G>{

	/**
	 * Validate all the bussines rules on the DTO on wich is not necesary to have acces to de DB
	 * @param dto to be validated
	 * @param operation to ve validated
	 * @throws BussinesException: exception encapsulated 
	 */
	public void validateBussinesRules (T dto, ValidatorOperations operation) throws BussinesException;
	
	/**
	 * Validate all the bussines rules on an primitive object wich is not necesary to have acces to de DB
	 * @param o object to be validated
	 * @param operation to ve validated
	 * @throws BussinesException: exception encapsulated
	 */
	public void validateParameterBussinesRules (Object o, ValidatorOperations operation) throws BussinesException;
	
	/**
	 * Validate all the data rules on an entity object in wich is necesary to have acces to de DB
	 * @param entity to be validated
	 * @param operation to be validated
	 * @throws DataBaseException: exception encapsulated
	 */
	public void validateDataRules(G entity, ValidatorOperations operation) throws DataBaseException;
	
}
