package com.lxisoft.store.service;

import com.lxisoft.store.service.dto.CartDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.lxisoft.store.domain.Cart}.
 */
public interface CartService {

    /**
     * Save a cart.
     *
     * @param cartDTO the entity to save.
     * @return the persisted entity.
     */
    CartDTO save(CartDTO cartDTO);

    /**
     * Get all the carts.
     *
     * @return the list of entities.
     */
    List<CartDTO> findAll();


    /**
     * Get the "id" cart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartDTO> findOne(Long id);

    /**
     * Delete the "id" cart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
