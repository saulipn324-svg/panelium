package com.saul.panelium.library;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="reading_progress",uniqueConstraints=@UniqueConstraint(columnNames={"reader_id","work_id"})) public class ReadingProgress{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @Column(name="reader_id") private String readerId; @Column(name="work_id") private Long workId; @Column(name="chapter_id") private Long chapterId; @Column(name="page_number") private int pageNumber; @Column(name="updated_at") private Instant updatedAt;
 protected ReadingProgress(){} public ReadingProgress(String r,Long w,Long c,int p){readerId=r;workId=w;chapterId=c;pageNumber=p;updatedAt=Instant.now();} public void update(Long c,int p){chapterId=c;pageNumber=p;updatedAt=Instant.now();}
 public Long getWorkId(){return workId;} public Long getChapterId(){return chapterId;} public int getPageNumber(){return pageNumber;} public Instant getUpdatedAt(){return updatedAt;}
}
