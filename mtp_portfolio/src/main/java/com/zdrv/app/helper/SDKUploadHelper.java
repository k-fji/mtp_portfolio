package com.zdrv.app.helper;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class SDKUploadHelper {

      private static final String ACCESS_KEY = "";
      private static final String SECRET_KEY = "";
      private static final String ENDPOINT_URL = "https://s3.ap-northeast-1.amazonaws.com";
      private static final String REGION = "ap-northeast-1";

      @Value("${bucket.name}")
      private String bucketName;

    public void saveFile(MultipartFile multipartFile){

    	AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTPS);
        clientConfig.setConnectionTimeout(10000);

        EndpointConfiguration endpointConfiguration = new EndpointConfiguration(ENDPOINT_URL, REGION);

    	AmazonS3 s3Client = AmazonS3ClientBuilder
    						.standard()
    						.withCredentials(new AWSStaticCredentialsProvider(credentials))
    						.withClientConfiguration(clientConfig)
    						.withEndpointConfiguration(endpointConfiguration)
    						.build();

    	try(InputStream inputStream = multipartFile.getInputStream();){

    	ObjectMetadata objectmetadata = new ObjectMetadata();
	    objectmetadata.setContentType(multipartFile.getContentType());
	    objectmetadata.setContentLength(multipartFile.getSize());

	    final PutObjectRequest putRequest = new PutObjectRequest(bucketName, multipartFile.getOriginalFilename(), inputStream, objectmetadata);

	    s3Client.putObject(putRequest);


    	}catch (IOException e){
	        e.printStackTrace();
	    }

    }

}
