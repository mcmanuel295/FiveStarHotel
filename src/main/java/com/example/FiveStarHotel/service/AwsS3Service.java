package com.example.FiveStarHotel.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.FiveStarHotel.exception.OurException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class AwsS3Service {

    String bucketName ="five-star-images";

    @Value("${bucket-name= five-star-images}")
    String accessKey;

    @Value("${bucket-name= five-star-images}")
    String secretKey;

    public String saveImageToS3(MultipartFile photo){

        String s3Location;
        String s3Filename;
        try {
            s3Filename= photo.getOriginalFilename();
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey,secretKey);
            AmazonS3 amazonS3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withRegion(Regions.AF_SOUTH_1)
                    .build();

            InputStream inputStream = photo.getInputStream();
            ObjectMetadata objectMetadata= new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,s3Filename,inputStream,objectMetadata);
            amazonS3.putObject(putObjectRequest);

            s3Location ="https://"+bucketName+".s3.amazonaws.com"+s3Filename;

            return s3Location;
        }
        catch (Exception e){
            throw new OurException("unable ot upload image to S3 bucket "+e.getMessage());
        }
    }
}