package com.saul.panelium.media;
import io.minio.*; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import org.springframework.web.multipart.MultipartFile; import java.io.InputStream;
@Service public class ObjectStorage{
 private final MinioClient client; private final String bucket;
 ObjectStorage(@Value("${panelium.storage.endpoint}")String endpoint,@Value("${panelium.storage.access-key}")String access,@Value("${panelium.storage.secret-key}")String secret,@Value("${panelium.storage.bucket}")String bucket){this.client=MinioClient.builder().endpoint(endpoint).credentials(access,secret).build();this.bucket=bucket;}
 private synchronized void ensureBucket()throws Exception{if(!client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build()))client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());}
 public void put(String key,MultipartFile file)throws Exception{ensureBucket();client.putObject(PutObjectArgs.builder().bucket(bucket).object(key).stream(file.getInputStream(),file.getSize(),-1).contentType(file.getContentType()).build());}
 public StoredObject get(String key)throws Exception{ensureBucket();var stat=client.statObject(StatObjectArgs.builder().bucket(bucket).object(key).build());InputStream data=client.getObject(GetObjectArgs.builder().bucket(bucket).object(key).build());return new StoredObject(data,stat.contentType(),stat.size());}
 public record StoredObject(InputStream data,String contentType,long size){}
}
