package com.experimentality.ClothesstoreLATAM.odels.exceptions;

/**
 * represents an exception found evaluating the bussiness rules that dont need acces to the DB to be validated
 * @author ccardozo
 *
 */
public class BussinesException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public BussinesException(String message) {
		super(message);
	}

}
