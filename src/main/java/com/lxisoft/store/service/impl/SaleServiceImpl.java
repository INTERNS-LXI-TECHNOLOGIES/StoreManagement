package com.lxisoft.store.service.impl;

import com.lxisoft.store.service.SaleService;
import com.lxisoft.store.domain.Sale;
import com.lxisoft.store.repository.SaleRepository;
import com.lxisoft.store.service.dto.SaleDTO;
import com.lxisoft.store.service.mapper.SaleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sale}.
 */
@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    private final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;

    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleMapper = saleMapper;
    }

    /**
     * Save a sale.
     *
     * @param saleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SaleDTO save(SaleDTO saleDTO) {
        log.debug("Request to save Sale : {}", saleDTO);
        Sale sale = saleMapper.toEntity(saleDTO);
        sale = saleRepository.save(sale);
        return saleMapper.toDto(sale);
    }

    /**
     * Get all the sales.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SaleDTO> findAll() {
        log.debug("Request to get all Sales");
        return saleRepository.findAll().stream()
            .map(saleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sale by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SaleDTO> findOne(Long id) {
        log.debug("Request to get Sale : {}", id);
        return saleRepository.findById(id)
            .map(saleMapper::toDto);
    }

    /**
     * Delete the sale by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sale : {}", id);
        saleRepository.deleteById(id);
    }
}
