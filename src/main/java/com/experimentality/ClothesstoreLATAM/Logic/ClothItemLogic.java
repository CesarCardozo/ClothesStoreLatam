package com.experimentality.ClothesstoreLATAM.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.experimentality.ClothesstoreLATAM.DAO.ClothItemRepository;
import com.experimentality.ClothesstoreLATAM.models.dtos.ClothItemDTO;
import com.experimentality.ClothesstoreLATAM.models.entities.ClothItem;
import com.experimentality.ClothesstoreLATAM.models.transformers.ClothItemTransformer;
import com.experimentality.ClothesstoreLATAM.models.utils.ValidatorOperations;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.BussinesExceptions;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseExceptions;

/**
 * Represent all the logic operations for cloth items
 * @author ccardozo
 *
 */
@Service
public class ClothItemLogic implements AbstractValidator<ClothItemDTO, ClothItem> {

	/**
	 * cloth item repository
	 */
	@Autowired
	private ClothItemRepository clothItemRepository;
	/**
	 * cloth item transformer
	 */
	@Autowired
	private ClothItemTransformer clothItemTransformer;
	
	/**
	 * Method that retrieve all the cloth items on the db, sorted by number of lookups and if necesary retrieves the results paginated
	 * @param paginationSize (can be null, on wich case return all the results)
	 * @param currentPage (can be null, on wich case return all the results)
	 * @return list of cloth items sorted by number of lookups
	 */
	public List<ClothItemDTO> getClothItemsByLookups(Integer paginationSize, Integer currentPage) {
		List<ClothItem> clothItems;
		if(paginationSize != null && paginationSize >= 0 && currentPage != null && currentPage >= 0) {
			Pageable page = PageRequest.of(currentPage, paginationSize, Sort.by("clothItemLookups").descending());
			clothItems = clothItemRepository.findAll(page);	
		}else {
			clothItems = clothItemRepository.findAll(Sort.by("clothItemLookups").descending());	
		}
		ArrayList<ClothItemDTO> dtos = new ArrayList<>();
		clothItems.stream().forEach((entity)->{
			dtos.add(clothItemTransformer.toDTOSimple(entity));
			});
		return dtos;
	}
	
	/**
	 * Method that retrieves all the cloth items on the db on wich the name matches with the given keywords
	 * @param keywords to look for
	 * @param paginationSize (can be null, on wich case return all the results) 
	 * @param currentPage (can be null, on wich case return all the results)
	 * @return list of cloth items found
	 * @throws BussinesException
	 */
	@Transactional
	public List<ClothItemDTO>getClothItemsByKeywords(String keywords, Integer paginationSize, Integer currentPage) throws BussinesException {  
		validateParameterBussinesRules(keywords, ValidatorOperations.SEARCH_BY_KEYWORDS);
		Map<Long,ClothItem> clotheItems;
		List<String> keyWordsList = Arrays.asList(keywords.split(" "));
		if(paginationSize != null && paginationSize >= 0 && currentPage != null && currentPage >= 0) {
			clotheItems = clothItemRepository.searchByKeywords(keyWordsList, currentPage, paginationSize);	
		}else {
			clotheItems = clothItemRepository.searchByKeywords(keyWordsList);	
		}
		clothItemRepository.updateItemsLookups(clotheItems.keySet());
		ArrayList<ClothItemDTO> dtos = new ArrayList<>();
		clotheItems.values().stream().forEach((entity)->{
					dtos.add(clothItemTransformer.toDTOSimple(entity));
					});
		return dtos;
	}
	
	/**
	 * Method that retrieves a detailed dto of the cloth item by the given id
	 * @param idClothItem id of the cloth item
	 * @return detailed {@link ClothItemDTO}
	 * @throws BussinesException
	 * @throws DataBaseException
	 */
	public ClothItemDTO getClothItemById(Long idClothItem) throws BussinesException, DataBaseException {
		validateParameterBussinesRules(idClothItem, ValidatorOperations.SEARCH);
		ClothItem clothItem = new ClothItem();
		clothItem.setIdClothItem(idClothItem);
		validateDataRules(clothItem, ValidatorOperations.SEARCH);
		clothItem = clothItemRepository.findById(idClothItem).get();
		return clothItemTransformer.toDTO(clothItem);
	}

	/**
	 * method that add a cloth item 
	 * @param clothItemDTO to be added
	 * @throws BussinesException
	 * @throws DataBaseException
	 */
	public void addClothItem(ClothItemDTO clothItemDTO) throws BussinesException, DataBaseException {
		validateBussinesRules(clothItemDTO, ValidatorOperations.CREATION);
		ClothItem entity = clothItemTransformer.toEntity(clothItemDTO);
		validateDataRules(entity, ValidatorOperations.CREATION);	
		clothItemRepository.save(entity);
	}
	

	/**
	 * {@inheritdoc}
	 */
	@Override
	public void validateBussinesRules(ClothItemDTO dto, ValidatorOperations operation) throws BussinesException{
		switch (operation) {
			case CREATION:
				if(dto == null || dto.getClothItemName() == null || 
				dto.getClothItemDescription() == null|| dto.getClothItemPrice()==null ||
				dto.getClothItemDiscount()==null || dto.getClothItemImageUrls() == null ||
				dto.getClothItemImageUrls().isEmpty() || dto.getClothItemImageUrls().size()<2) {
					throw new BussinesException(BussinesExceptions.OBLIGATORY_FIELDS_MISSING);
				}
				if(dto.getClothItemId()!=null) {
					throw new BussinesException(BussinesExceptions.ID_NOT_ACCEPTABLE);
				}
				if(dto.getClothItemName().length()>60 || dto.getClothItemDescription().length()>1024 ) {
					throw new BussinesException(BussinesExceptions.VALUE_TOO_LONG);
				}
			break;
		default:
			break;
		}
	}
	
	/**
	 * {@inheritdoc}
	 */
	@Override
	public void validateParameterBussinesRules(Object o, ValidatorOperations operation) throws BussinesException {
		switch (operation) {
		case SEARCH:
			if(o == null || (Long)o<0L) {
				throw new BussinesException(BussinesExceptions.OBLIGATORY_FIELDS_MISSING_REQUEST);
			}
		break;
		case SEARCH_BY_KEYWORDS:
			if(o == null || ((String) o).split(" ").length < 1) {
				throw new BussinesException(BussinesExceptions.OBLIGATORY_FIELDS_MISSING_REQUEST);
			}
		break;
		default:
			break;
		}
	}

	/**
	 * {@inheritdoc}
	 */
	@Override
	public void validateDataRules(ClothItem entity, ValidatorOperations operation) throws DataBaseException{
		switch (operation) {
		case SEARCH:
			if(!clothItemRepository.existsById(entity.getIdClothItem())){
				throw new DataBaseException(DataBaseExceptions.NOT_FOUND);
			}
		break;
		case CREATION:
			if(entity.getClothItemLookups()!=null && entity.getClothItemLookups()!=0) {
				throw new DataBaseException(DataBaseExceptions.INVALID_VALUE);
			}
		break;
		default:
			break;
		}
	}
}