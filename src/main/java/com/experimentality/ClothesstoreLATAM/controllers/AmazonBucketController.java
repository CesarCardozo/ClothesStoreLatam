package com.experimentality.ClothesstoreLATAM.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.experimentality.ClothesstoreLATAM.DAO.AmazonClient;
import com.experimentality.ClothesstoreLATAM.odels.exceptions.DataBaseException;

/**
 *  Contains the endpoints of the services of the S3 bucket
 * @author ccardozo
 *
 */
@RestController
@RequestMapping("/storage/")
public class AmazonBucketController {

	/**
	 * Amazon client
	 */
	private AmazonClient amazonClient;

	/**
	 * Init of the controller
	 * @param amazonClient
	 */
	@Autowired
	AmazonBucketController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	/**
	 * Service that uploads a file to the s3 bucket and returns the url of the saved file
	 * @param file to be saved
	 * @return url of the file
	 * @throws IOException
	 */
	@PostMapping("/uploadImage")
	public ResponseEntity<?> uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
		try {
			return new ResponseEntity<>(this.amazonClient.uploadFile(file),HttpStatus.OK); 
		} catch (IOException | DataBaseException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
