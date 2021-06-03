package com.experimentality.ClothesstoreLATAM.DAO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

public interface ClothItemRepository extends CrudRepository<ClothItem, Long>, ClothItemRepositoryCustom{

	@Modifying
	@Query(value = "UPDATE CLOTH_ITEMS SET CLOTH_ITEM_LOOKUPS = CLOTH_ITEM_LOOKUPS + 1"
			+ "WHERE ID_CLOTH_ITEM IN (:clothItems) ;", nativeQuery = true)
	int updateItemsLookups(Set<Long> clothItems);
	
	List<ClothItem> findAll(Pageable pageable);
	
	List<ClothItem> findAll(Sort by);
}
