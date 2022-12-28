package org.potatocloud.s3;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.potatocloud.basic.Print;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MinioBucket {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

    public MinioBucket() {}
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
                    .endpoint(new URL(this.endpoint))
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


    public void upload(InputStream file, String path, String fileName, String contentType) {
        try {
            MinioClient minioClient = this.initMinioClient();
            Print.ln("init minioClient" + minioClient);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object(fileName)
                            .bucket(this.bucket)
                            .contentType(contentType)
                            .stream(file, -1, 600000000)
                            .build()
            );
        } catch (Exception e) {
            Print.ln("error upload: " + e.getMessage());
        }
    }

    public List<Bucket> listBucket() {
        MinioClient minioClient = this.initMinioClient();
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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

    @Override
    public String toString() {
        return "MinioBucket{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", bucket='" + bucket + '\'' +
                '}';
    }
}
