package com.example.Blogging.Application.Repositories;

import com.example.Blogging.Application.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Integer> {

}
