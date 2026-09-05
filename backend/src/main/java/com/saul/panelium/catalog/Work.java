package com.saul.panelium.catalog;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="work") public class Work {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String slug; private String title; private String author; @Column(columnDefinition="text") private String synopsis;
 private String format; private String status; @Column(name="cover_key") private String coverKey; private String accent;
 @OneToMany(mappedBy="work",fetch=FetchType.LAZY) @OrderBy("number asc") private List<Chapter> chapters=new ArrayList<>();
 protected Work(){}
 public Work(String slug,String title,String author,String synopsis,String format,String status,String coverKey,String accent){this.slug=slug;this.title=title;this.author=author;this.synopsis=synopsis;this.format=format;this.status=status;this.coverKey=coverKey;this.accent=accent;}
 public void setCoverKey(String coverKey){this.coverKey=coverKey;}
 public Long getId(){return id;} public String getSlug(){return slug;} public String getTitle(){return title;} public String getAuthor(){return author;} public String getSynopsis(){return synopsis;} public String getFormat(){return format;} public String getStatus(){return status;} public String getCoverKey(){return coverKey;} public String getAccent(){return accent;} public List<Chapter> getChapters(){return chapters;}
}
