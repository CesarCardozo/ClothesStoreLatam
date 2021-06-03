package com.experimentality.ClothesstoreLATAM.models.transformers;

public interface AbstractTransformer <T,G>{
	
	public G toEntity(T dto);
	
	public T toDTO(G entity);
}
