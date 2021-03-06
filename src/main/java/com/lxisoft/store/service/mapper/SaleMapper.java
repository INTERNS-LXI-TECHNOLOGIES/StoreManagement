package com.lxisoft.store.service.mapper;


import com.lxisoft.store.domain.*;
import com.lxisoft.store.service.dto.SaleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sale} and its DTO {@link SaleDTO}.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class, ProductMapper.class, StoreMapper.class})
public interface SaleMapper extends EntityMapper<SaleDTO, Sale> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "store.id", target = "storeId")
    SaleDTO toDto(Sale sale);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "storeId", target = "store")
    Sale toEntity(SaleDTO saleDTO);

    default Sale fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sale sale = new Sale();
        sale.setId(id);
        return sale;
    }
}
