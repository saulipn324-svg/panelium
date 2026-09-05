package com.saul.panelium.catalog;
import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface WorkRepository extends JpaRepository<Work,Long>{
 @EntityGraph(attributePaths="chapters") Optional<Work> findBySlug(String slug);
 @Query("select w from Work w where (:q='' or lower(w.title) like lower(concat('%',:q,'%')) or lower(w.author) like lower(concat('%',:q,'%'))) and (:format='' or w.format=:format) order by w.title")
 List<Work> search(@Param("q") String q,@Param("format") String format);
}
