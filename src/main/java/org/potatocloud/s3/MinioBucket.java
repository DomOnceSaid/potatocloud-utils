package org.potatocloud.s3;

import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.potatocloud.media.Media;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioBucket {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public MinioBucket(String endpoint, String accessKey, String secretKey, String bucket) {
        this.accessKey = accessKey;
        this.endpoint = endpoint;
        this.secretKey = secretKey;
        this.bucket = bucket;
    }

    public String endpoint() {
        return endpoint;
    }

    public String bucket() {
        return bucket;
    }

    public String accessKey() {
        return accessKey;
    }

    public String secretKey() {
        return secretKey;
    }

    public MinioBucket endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public MinioBucket bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public MinioBucket accessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public MinioBucket secretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    private MinioClient initMinioClient() {
        try {
            return MinioClient.builder()
                    .endpoint(this.endpoint)
                    .credentials(this.accessKey, this.secretKey)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Boolean createBucket(String bucketName) throws Exception {
        MinioClient minioClient = this.initMinioClient();
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return true;
    }


    public String upload(InputStream file,String path, String contentType) {
        try {
            MinioClient minioClient = this.initMinioClient();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(this.bucket)
                            .contentType(contentType)
                            .stream(file, -1, -1)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return "";
    }

    public String retrieve(String path) {
        try {
            MinioClient minioClient = this.initMinioClient();
            minioClient.getObject(
                    GetObjectArgs.builder()
                            .object(path)
                            .bucket(this.bucket)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
