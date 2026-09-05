package com.saul.panelium.catalog;
import org.springframework.data.jpa.repository.*; import java.util.Optional;
public interface ChapterRepository extends JpaRepository<Chapter,Long>{@Override @EntityGraph(attributePaths="work") Optional<Chapter> findById(Long id);}
