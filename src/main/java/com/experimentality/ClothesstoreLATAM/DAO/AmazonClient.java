package com.experimentality.ClothesstoreLATAM.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
		this.s3client = new AmazonS3Client(credentials);
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException, DataBaseException {

		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
			file.delete();
		} /*
			 * catch (Exception e) { e.printStackTrace(); }
			 */
		catch (AmazonServiceException ase) {
			StringBuilder sb = new StringBuilder();
			sb.append("Caught an AmazonServiceException from GET requests, rejected reasons:");
			sb.append("Error Message:    " + ase.getMessage());
			sb.append("HTTP Status Code: " + ase.getStatusCode());
			sb.append("AWS Error Code:   " + ase.getErrorCode());
			sb.append("Error Type:       " + ase.getErrorType());
			sb.append("Request ID:       " + ase.getRequestId());
			throw new DataBaseException(sb.toString());
		} catch (AmazonClientException ace) {
			StringBuilder sb = new StringBuilder();
			sb.append("Caught an AmazonClientException: ");
			sb.append("Error Message: " + ace.getMessage());
			throw new DataBaseException(sb.toString());
		} catch (IOException ioe) {
			throw new DataBaseException("IOE Error Message: " + ioe.getMessage());
		}
		return fileUrl;
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
