package com.saul.panelium.reader;
import com.saul.panelium.catalog.*; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import org.springframework.http.HttpStatus; import java.util.stream.*;
@RestController @RequestMapping("/api/chapters") public class ReaderController{
 private final ChapterRepository chapters; private final ChapterPageRepository pages; public ReaderController(ChapterRepository chapters,ChapterPageRepository pages){this.chapters=chapters;this.pages=pages;}
 @GetMapping("/{id}/manifest") Manifest manifest(@PathVariable Long id){Chapter c=chapters.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Capítulo no encontrado"));var uploaded=pages.findByChapterIdOrderByPageNumber(id);var urls=uploaded.isEmpty()?IntStream.rangeClosed(1,c.getPageCount()).mapToObj(i->"/reader/page-"+i+(i==1||i==6?".png":".jpg")).toList():uploaded.stream().map(p->"/api/assets/"+p.getObjectKey()).toList();return new Manifest(c.getId(),c.getWork().getId(),c.getWork().getTitle(),c.getTitle(),c.getDirection(),urls);}
 record Manifest(Long chapterId,Long workId,String workTitle,String chapterTitle,String direction,java.util.List<String> pages){}
}

