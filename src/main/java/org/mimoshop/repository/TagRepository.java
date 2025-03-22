package org.mimoshop.repository;

import org.mimoshop.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name); // Tìm tag theo name
    List<Tag> findByNameIn(List<String> tagNames);
}