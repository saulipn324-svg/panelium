package com.saul.panelium.library;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.security.Principal;
@RestController @RequestMapping("/api/progress") public class ProgressController{
 private final ProgressRepository repo; public ProgressController(ProgressRepository repo){this.repo=repo;}
 @GetMapping("/{workId}") ProgressView get(Principal principal,@PathVariable Long workId){return repo.findByReaderIdAndWorkId(principal.getName(),workId).map(ProgressView::from).orElse(null);}
 @PutMapping("/{workId}") @Transactional ProgressView save(Principal principal,@PathVariable Long workId,@Valid @RequestBody ProgressRequest body){String readerId=principal.getName();var p=repo.findByReaderIdAndWorkId(readerId,workId).orElseGet(()->new ReadingProgress(readerId,workId,body.chapterId(),body.pageNumber()));p.update(body.chapterId(),body.pageNumber());return ProgressView.from(repo.save(p));}
 public record ProgressRequest(@NotNull Long chapterId,@Min(1) int pageNumber){} public record ProgressView(Long workId,Long chapterId,int pageNumber,Instant updatedAt){static ProgressView from(ReadingProgress p){return new ProgressView(p.getWorkId(),p.getChapterId(),p.getPageNumber(),p.getUpdatedAt());}}
}
