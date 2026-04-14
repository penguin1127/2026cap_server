package com.expansion.server.domain.common.repository;

import com.expansion.server.domain.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByTypeOrderBySortOrderAsc(String type);

    List<Category> findByParentIsNullAndTypeOrderBySortOrderAsc(String type);
}
