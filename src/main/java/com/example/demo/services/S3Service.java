package com.example.demo.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(@Value("${aws.accessKeyId}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public String uploadFile(byte[] fileBytes, String fileName, String contentType) throws IOException {
        try {
            try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(fileBytes.length);
                metadata.setContentType(contentType);

                String objectKey = "imagens-publicas/" + fileName;

                PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, inputStream, metadata);
                s3Client.putObject(request);

                return s3Client.getUrl(bucketName, objectKey).toString();
            } catch (IOException e) {
                throw new IOException("Erro ao fazer upload do arquivo para o Amazon S3: " + e.getMessage());
            }
        }
        catch (IOException e) {
            throw new IOException("Erro ao fazer upload do arquivo para o Amazon S3: " + e.getMessage());
        }
    }
}
