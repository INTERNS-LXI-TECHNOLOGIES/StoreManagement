package com.lxisoft.store.service.impl;

import com.lxisoft.store.service.StockService;
import com.lxisoft.store.domain.Stock;
import com.lxisoft.store.repository.StockRepository;
import com.lxisoft.store.service.dto.StockDTO;
import com.lxisoft.store.service.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Stock}.
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

    private final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Save a stock.
     *
     * @param stockDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public StockDTO save(StockDTO stockDTO) {
        log.debug("Request to save Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    /**
     * Get all the stocks.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockDTO> findAll() {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll().stream()
            .map(stockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the stocks where Product is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<StockDTO> findAllWhereProductIsNull() {
        log.debug("Request to get all stocks where Product is null");
        return StreamSupport
            .stream(stockRepository.findAll().spliterator(), false)
            .filter(stock -> stock.getProduct() == null)
            .map(stockMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one stock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StockDTO> findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findById(id)
            .map(stockMapper::toDto);
    }

    /**
     * Delete the stock by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }
}
