package com.saul.panelium.library;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface ProgressRepository extends JpaRepository<ReadingProgress,Long>{Optional<ReadingProgress> findByReaderIdAndWorkId(String readerId,Long workId);}
