package com.experimentality.ClothesstoreLATAM.DAO;

import java.util.List;
import java.util.Map;

import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

public interface ClothItemRepositoryCustom {

	Map<Long, ClothItem> searchByKeywords(List<String> keyWords);
	Map<Long, ClothItem> searchByKeywords(List<String> keyWords, Integer pageIndex, Integer paginationSize);
}
