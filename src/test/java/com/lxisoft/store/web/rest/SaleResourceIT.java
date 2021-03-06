package com.lxisoft.store.web.rest;

import com.lxisoft.store.StoreManagementApp;
import com.lxisoft.store.domain.Sale;
import com.lxisoft.store.repository.SaleRepository;
import com.lxisoft.store.service.SaleService;
import com.lxisoft.store.service.dto.SaleDTO;
import com.lxisoft.store.service.mapper.SaleMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SaleResource} REST controller.
 */
@SpringBootTest(classes = StoreManagementApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SaleResourceIT {

    private static final Long DEFAULT_NO_OF_PRODUCT = 1L;
    private static final Long UPDATED_NO_OF_PRODUCT = 2L;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_UNIT_COST = 1D;
    private static final Double UPDATED_UNIT_COST = 2D;

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private SaleService saleService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleMockMvc;

    private Sale sale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createEntity(EntityManager em) {
        Sale sale = new Sale()
            .noOfProduct(DEFAULT_NO_OF_PRODUCT)
            .date(DEFAULT_DATE)
            .amount(DEFAULT_AMOUNT)
            .unitCost(DEFAULT_UNIT_COST)
            .productName(DEFAULT_PRODUCT_NAME)
            .status(DEFAULT_STATUS);
        return sale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createUpdatedEntity(EntityManager em) {
        Sale sale = new Sale()
            .noOfProduct(UPDATED_NO_OF_PRODUCT)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .unitCost(UPDATED_UNIT_COST)
            .productName(UPDATED_PRODUCT_NAME)
            .status(UPDATED_STATUS);
        return sale;
    }

    @BeforeEach
    public void initTest() {
        sale = createEntity(em);
    }

    @Test
    @Transactional
    public void createSale() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();
        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);
        restSaleMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isCreated());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate + 1);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNoOfProduct()).isEqualTo(DEFAULT_NO_OF_PRODUCT);
        assertThat(testSale.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSale.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSale.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testSale.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testSale.isStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSaleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();

        // Create the Sale with an existing ID
        sale.setId(1L);
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleMockMvc.perform(post("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSales() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get all the saleList
        restSaleMockMvc.perform(get("/api/sales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfProduct").value(hasItem(DEFAULT_NO_OF_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get the sale
        restSaleMockMvc.perform(get("/api/sales/{id}", sale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sale.getId().intValue()))
            .andExpect(jsonPath("$.noOfProduct").value(DEFAULT_NO_OF_PRODUCT.intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.unitCost").value(DEFAULT_UNIT_COST.doubleValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSale() throws Exception {
        // Get the sale
        restSaleMockMvc.perform(get("/api/sales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale
        Sale updatedSale = saleRepository.findById(sale.getId()).get();
        // Disconnect from session so that the updates on updatedSale are not directly saved in db
        em.detach(updatedSale);
        updatedSale
            .noOfProduct(UPDATED_NO_OF_PRODUCT)
            .date(UPDATED_DATE)
            .amount(UPDATED_AMOUNT)
            .unitCost(UPDATED_UNIT_COST)
            .productName(UPDATED_PRODUCT_NAME)
            .status(UPDATED_STATUS);
        SaleDTO saleDTO = saleMapper.toDto(updatedSale);

        restSaleMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNoOfProduct()).isEqualTo(UPDATED_NO_OF_PRODUCT);
        assertThat(testSale.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSale.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSale.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testSale.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testSale.isStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc.perform(put("/api/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeDelete = saleRepository.findAll().size();

        // Delete the sale
        restSaleMockMvc.perform(delete("/api/sales/{id}", sale.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
