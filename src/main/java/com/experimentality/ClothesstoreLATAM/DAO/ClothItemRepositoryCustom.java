package com.experimentality.ClothesstoreLATAM.DAO;

import java.util.List;
import java.util.Map;

import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

/**
 * defines the methids to build dinamic querys
 * @author ccardozo
 *
 */
public interface ClothItemRepositoryCustom {

	/**
	 * create and excecute query to look for all the keywords found on the name of the cloth items
	 * @param keyWords words separated by ' ' 
	 * @return map with the result of the query
	 */
	Map<Long, ClothItem> searchByKeywords(List<String> keyWords);
	/**
	 * create and excecute query to look for all the keywords found on the name of the cloth items
	 * having into account pagination
	 * @param keyWords words separated by ' '
	 * @param pageIndex number of the page (starts from 0)
	 * @param paginationSize size of the page
	 * @return map with the result of the query
	 */
	Map<Long, ClothItem> searchByKeywords(List<String> keyWords, Integer pageIndex, Integer paginationSize);
}
