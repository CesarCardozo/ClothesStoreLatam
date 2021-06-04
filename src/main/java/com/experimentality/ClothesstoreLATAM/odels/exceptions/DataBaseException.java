package com.experimentality.ClothesstoreLATAM.odels.exceptions;

/**
 * represents an exception found evaluating the bussiness rules that need acces to the DB to be validated
 * @author ccardozo
 *
 */
public class DataBaseException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public DataBaseException(String message) {
		super(message);
	}

}
