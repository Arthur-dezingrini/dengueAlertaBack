package com.example.demo.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
            File tempFile = File.createTempFile("tempFile", null);
            FileOutputStream fos = new FileOutputStream(tempFile);
            fos.write(fileBytes);
            fos.close();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileBytes.length);
            metadata.setContentType(contentType);

            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, tempFile).withMetadata(metadata);
            PutObjectResult result = s3Client.putObject(request);

            return s3Client.getUrl(bucketName, fileName).toString();
        }
        catch (IOException e) {
            throw new IOException("Erro ao fazer upload do arquivo para o Amazon S3: " + e.getMessage());
        }
    }
}
