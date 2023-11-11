package com.StoreBook.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.StoreBook.DTO.CategoryDTO;
import com.StoreBook.entity.Category;
import com.StoreBook.repository.CategoryRepository;

public interface CategoryService {
    void add(CategoryDTO c);

    void update(CategoryDTO c);

    void delete(int id);

    List<CategoryDTO> getAll();

    CategoryDTO getById(int id);
}

@Transactional
@Service
class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public void add(CategoryDTO c) {
        // TODO Auto-generated method stub
        Category category = modelMapper.map(c, Category.class);
        categoryRepository.save(category);
        c.setId(category.getId());

    }

    @Override
    public void update(CategoryDTO c) {
        // TODO Auto-generated method stub
        Category category = modelMapper.map(categoryRepository.getReferenceById((long) c.getId()), Category.class);
        if (category != null) {
            category.setName(c.getName());
            category.setDescription(c.getDescription());

            categoryRepository.save(category);
        }

    }

    @Override
    public void delete(int id) {
        Category category = categoryRepository.getReferenceById((long) id);
        if (category != null) {
            categoryRepository.delete(category);
        }
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<CategoryDTO> lst = new ArrayList<CategoryDTO>();
        categoryRepository.findAll().forEach((category) -> {
            lst.add(modelMapper.map(category, CategoryDTO.class));
        });
        return lst;
    }

    @Override
    public CategoryDTO getById(int id) {
        // TODO Auto-generated method stub
        Category category = categoryRepository.getReferenceById((long) id);
        if (category != null) {
            return modelMapper.map(category, CategoryDTO.class);
        } else {
            return null;
        }

    }
}
