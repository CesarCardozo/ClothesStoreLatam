package com.experimentality.ClothesstoreLATAM.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;

/**
 * implementation of ClothItemRepositoryCustom
 * @author ccardozo
 *
 */
public class ClothItemRepositoryCustomImpl implements ClothItemRepositoryCustom {

	/**
	 * Entity manager to build and excecute query
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Map<Long, ClothItem> searchByKeywords(List<String> keyWords) {
		CriteriaQuery<ClothItem> query = createQuery(keyWords);
		return (HashMap<Long,ClothItem>) entityManager.createQuery(query).getResultStream()
				.collect(Collectors.toMap(clothItem->clothItem.getIdClothItem(),clothItem->clothItem));
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Map<Long, ClothItem> searchByKeywords(List<String> keyWords, Integer pageIndex, Integer paginationSize) {
		CriteriaQuery<ClothItem> query = createQuery(keyWords);
		return (HashMap<Long,ClothItem>)entityManager.createQuery(query).setMaxResults(paginationSize).setFirstResult(pageIndex * paginationSize).getResultStream()
				.collect(Collectors.toMap(clothItem->clothItem.getIdClothItem(),clothItem->clothItem));
	}
	
	/**
	 * method that builds the basic query to search on clothItems by keywords
	 * @param keyWords 
	 * @return CriteriaBuilder query 
	 */
	private CriteriaQuery<ClothItem> createQuery(List<String> keyWords) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ClothItem> query = cb.createQuery(ClothItem.class);
		Root<ClothItem> clothItem = query.from(ClothItem.class);
		Path<String> clothItemName = clothItem.get("clothItemName");
		List<Predicate> predicates = new ArrayList<>();
		for (String keyWord : keyWords) {
			predicates.add(cb.like(clothItemName, "%"+keyWord+"%"));
		}
		query.select(clothItem).where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
		return query;
	}
}
