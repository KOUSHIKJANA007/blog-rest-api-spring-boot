package com.koushik.blog.service;

import java.util.List;

import com.koushik.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	void deletecategory(Integer categoryId);
	CategoryDto getSingleCategory(Integer categoryId);
	List<CategoryDto> getCategories();
}
