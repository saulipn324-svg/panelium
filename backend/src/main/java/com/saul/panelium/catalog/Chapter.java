package com.saul.panelium.catalog;
import jakarta.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="chapter") public class Chapter {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="work_id") private Work work;
 private BigDecimal number; private String title; @Column(name="page_count") private int pageCount; private String direction;
 public Long getId(){return id;} public Work getWork(){return work;} public BigDecimal getNumber(){return number;} public String getTitle(){return title;} public int getPageCount(){return pageCount;} public String getDirection(){return direction;}
 protected Chapter(){}
 public Chapter(Work work,BigDecimal number,String title,int pageCount,String direction){this.work=work;this.number=number;this.title=title;this.pageCount=pageCount;this.direction=direction;}
 public void setPageCount(int pageCount){this.pageCount=pageCount;}
}
