package com.experimentality.ClothesstoreLATAM.DAO;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

/**
 * Interface for generic CRUD operations on a repository for {@link ClothItem}
 *
 */
public interface ClothItemRepository extends CrudRepository<ClothItem, Long>, ClothItemRepositoryCustom{

	/**
	 * adds 1 to CLOTH_ITEM_LOOKUPS for every id inside clothItems
	 * @param clothItems set of id of cloth items
	 * @return number of modified rows.
	 */
	@Modifying
	@Query(value = "UPDATE CLOTH_ITEMS SET CLOTH_ITEM_LOOKUPS = CLOTH_ITEM_LOOKUPS + 1"
			+ "WHERE ID_CLOTH_ITEM IN (:clothItems) ;", nativeQuery = true)
	int updateItemsLookups(Set<Long> clothItems);
	
	/**
	 * enables pagination for all the cloth items on the DB
	 * @param pageable
	 * @return
	 */
	List<ClothItem> findAll(Pageable pageable);
	
	/**
	 * enables sorting for all the cloth items on the DB
	 * @param by
	 * @return
	 */
	List<ClothItem> findAll(Sort by);
}
