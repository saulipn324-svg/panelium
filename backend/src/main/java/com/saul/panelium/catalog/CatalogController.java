package com.saul.panelium.catalog;
import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import org.springframework.http.HttpStatus; import java.util.*;
@RestController @RequestMapping("/api/works") public class CatalogController {
 private final WorkRepository works; public CatalogController(WorkRepository works){this.works=works;}
 @GetMapping public List<WorkCard> list(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String format){return works.search(q.trim(),format.trim().toUpperCase()).stream().map(WorkCard::from).toList();}
 @GetMapping("/{slug}") public WorkDetail detail(@PathVariable String slug){return WorkDetail.from(works.findBySlug(slug).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Obra no encontrada")));}
 public record WorkCard(Long id,String slug,String title,String author,String format,String status,String coverKey,String accent){public static WorkCard from(Work w){return new WorkCard(w.getId(),w.getSlug(),w.getTitle(),w.getAuthor(),w.getFormat(),w.getStatus(),w.getCoverKey(),w.getAccent());}}
 public record ChapterItem(Long id,String number,String title,int pageCount,String direction){static ChapterItem from(Chapter c){return new ChapterItem(c.getId(),c.getNumber().stripTrailingZeros().toPlainString(),c.getTitle(),c.getPageCount(),c.getDirection());}}
 public record WorkDetail(Long id,String slug,String title,String author,String synopsis,String format,String status,String coverKey,String accent,List<ChapterItem> chapters){static WorkDetail from(Work w){return new WorkDetail(w.getId(),w.getSlug(),w.getTitle(),w.getAuthor(),w.getSynopsis(),w.getFormat(),w.getStatus(),w.getCoverKey(),w.getAccent(),w.getChapters().stream().map(ChapterItem::from).toList());}}
}
