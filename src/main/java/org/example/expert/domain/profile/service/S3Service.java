package org.example.expert.domain.profile.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import jakarta.annotation.PostConstruct;

@Service
public class S3Service {

	@Value("${cloud.aws.credentials.access-key}")
	private String accessKeyId;

	@Value("${cloud.aws.credentials.secret-key}")
	private String secretAccessKey;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	private AmazonS3 s3Client;


	@PostConstruct
	public void init() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
		this.s3Client = AmazonS3ClientBuilder.standard()
			.withRegion(region)
			.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
			.build();
	}

	public String uploadFile(MultipartFile file) throws IOException {


		if (file.isEmpty()) {
			System.out.println("File is empty");
			throw new IllegalArgumentException("파일이 비어있습니다.");
		}

		File tempFile = covertToFile(file);
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, tempFile));

		return s3Client.getUrl(bucketName, fileName).toString();
	}

	private File covertToFile(MultipartFile file) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String[] nameParts = originalFilename.split("\\.");
		String prefix = nameParts[0];
		String suffix = nameParts.length > 1 ? "." + nameParts[1] : ".tmp";

		File tempFile = File.createTempFile(prefix, suffix);
		file.transferTo(tempFile);

		tempFile.deleteOnExit();

		return tempFile;
	}
}
