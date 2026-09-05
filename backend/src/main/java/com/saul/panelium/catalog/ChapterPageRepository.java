package com.saul.panelium.catalog;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ChapterPageRepository extends JpaRepository<ChapterPage,Long>{List<ChapterPage> findByChapterIdOrderByPageNumber(Long chapterId); long deleteByChapterId(Long chapterId);}
