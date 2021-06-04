package com.experimentality.ClothesstoreLATAM.models.transformers;

/**
 * Interface that determines the basic functions a transformer must have
 * @author ccardozo
 *
 * @param <T> dto of the object
 * @param <G> entity (PoJo) of the object
 */
public interface AbstractTransformer <T,G>{
	
	/**
	 * transforms a dto to an entity
	 * @param dto to transform
	 * @return entity transformed
	 */
	public G toEntity(T dto);
	
	/**
	 * transforms a dto to an entity
	 * @param entity to transform
	 * @return dto transformed
	 */
	public T toDTO(G entity);
}
