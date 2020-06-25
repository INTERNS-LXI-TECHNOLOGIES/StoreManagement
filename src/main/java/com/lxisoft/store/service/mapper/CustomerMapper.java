package com.lxisoft.store.service.mapper;

import com.lxisoft.store.domain.*;
import com.lxisoft.store.service.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {SaleMapper.class, StoreMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(source = "sale.id", target = "saleId")
    @Mapping(source = "store.id", target = "storeId")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "saleId", target = "sale")
    @Mapping(source = "storeId", target = "store")
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
