package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDTO categoryDTO;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(1L);
        categoryDTO.setCategoryName("Electronics");

        categoryResponse = new CategoryResponse();
        categoryResponse.setContent(List.of(categoryDTO));
        categoryResponse.setPageNumber(0);
        categoryResponse.setPageSize(10);
        categoryResponse.setTotalElements(1L);
        categoryResponse.setTotalPages(1);
        categoryResponse.setLastPage(true);
    }

    @Test
    void testGetCategories() throws Exception {
        when(categoryService.getAllCategories(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(categoryResponse);

        mockMvc.perform(get("/api/public/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].categoryId").value(1L))
                .andExpect(jsonPath("$.content[0].categoryName").value("Electronics"));
    }

    @Test
    void testCreateCategory() throws Exception {
        when(categoryService.createCategory(any(CategoryDTO.class)))
                .thenReturn(categoryDTO);

        mockMvc.perform(post("/api/public/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryId").value(1L));
    }

    @Test
    void testDeleteCategory() throws Exception {
        when(categoryService.deleteCategory(1L))
                .thenReturn(categoryDTO);

        mockMvc.perform(delete("/api/admin/categories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryId").value(1L));
    }

    @Test
    void testUpdateCategory() throws Exception {
        when(categoryService.updateCategory(any(CategoryDTO.class), eq(1L)))
                .thenReturn(categoryDTO);

        mockMvc.perform(put("/api/admin/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryId").value(1L));
    }
}