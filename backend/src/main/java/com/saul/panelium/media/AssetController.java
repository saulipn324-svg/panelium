package com.saul.panelium.media;
import org.springframework.core.io.InputStreamResource; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;
@RestController public class AssetController{
 private final ObjectStorage storage; AssetController(ObjectStorage storage){this.storage=storage;}
 @GetMapping("/api/assets/{*key}") ResponseEntity<InputStreamResource> get(@PathVariable String key)throws Exception{var object=storage.get(key.startsWith("/")?key.substring(1):key);return ResponseEntity.ok().contentType(MediaType.parseMediaType(object.contentType())).contentLength(object.size()).cacheControl(CacheControl.maxAge(java.time.Duration.ofDays(7))).body(new InputStreamResource(object.data()));}
}
