//package org.example.expert.domain.profile.controller;
//
//import java.io.IOException;
//
//import org.example.expert.domain.profile.service.S3Service;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/profiles")
//@RequiredArgsConstructor
//public class ProfileController {
//
//	private final S3Service s3Service;
//
//	@PostMapping("/upload")
//	public ResponseEntity<String> uploadProfileImage(@RequestParam("file") MultipartFile file){
//
//		try{
//			String fileUrl =  s3Service.uploadFile(file);
//			return ResponseEntity.ok(fileUrl);
//
//		} catch(IOException e){
//			return new ResponseEntity<>("Error uploading profile image", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//}
