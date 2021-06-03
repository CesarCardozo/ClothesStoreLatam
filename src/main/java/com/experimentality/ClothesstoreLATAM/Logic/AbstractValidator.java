package com.experimentality.ClothesstoreLATAM.Logic;

import com.experimentality.ClothesstoreLATAM.models.utils.ValidatorOperations;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

public interface AbstractValidator <T,G>{

	public void validateBussinesRules (T dto, ValidatorOperations operation) throws BussinesException;
	public void validateParameterBussinesRules (Object o, ValidatorOperations operation) throws BussinesException;
	public void validateDataRules(G entity, ValidatorOperations operation) throws DataBaseException;
	
}
