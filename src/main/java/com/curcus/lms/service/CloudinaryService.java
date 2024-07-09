package com.curcus.lms.service;

import com.curcus.lms.exception.InvalidFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    String uploadFile(MultipartFile file) throws IOException, InvalidFileTypeException;
    String uploadImage(MultipartFile file) throws IOException, InvalidFileTypeException;
    String uploadVideo(MultipartFile file) throws IOException, InvalidFileTypeException;
    String updateFile(String publicId, MultipartFile file) throws IOException, InvalidFileTypeException;
    String updateVideo(String publicId, MultipartFile file) throws IOException, InvalidFileTypeException;
    String updateImage(String publicId, MultipartFile file) throws IOException, InvalidFileTypeException;;
    String deleteFile(String publicId) throws IOException;
}
