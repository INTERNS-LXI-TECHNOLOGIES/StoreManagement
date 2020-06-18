package com.lxisoft.store.service.mapper;

import com.lxisoft.store.domain.*;
import com.lxisoft.store.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "store.id", target = "storeId")
    ProductDTO toDto(Product product);

    @Mapping(source = "storeId", target = "store")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
