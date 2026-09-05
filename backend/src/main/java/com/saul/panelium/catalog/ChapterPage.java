package com.saul.panelium.catalog;
import jakarta.persistence.*;
@Entity @Table(name="chapter_page") public class ChapterPage{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="chapter_id") private Chapter chapter;
 @Column(name="page_number") private int pageNumber;
 @Column(name="object_key") private String objectKey;
 @Column(name="content_type") private String contentType;
 protected ChapterPage(){} public ChapterPage(Chapter chapter,int pageNumber,String objectKey,String contentType){this.chapter=chapter;this.pageNumber=pageNumber;this.objectKey=objectKey;this.contentType=contentType;}
 public int getPageNumber(){return pageNumber;} public String getObjectKey(){return objectKey;} public String getContentType(){return contentType;}
}
