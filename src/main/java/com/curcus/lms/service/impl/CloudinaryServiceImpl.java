package com.curcus.lms.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.curcus.lms.service.CloudinaryService;
import com.curcus.lms.validation.FileValidation;
import com.curcus.lms.exception.InvalidFileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) throws IOException, InvalidFileTypeException {
        FileValidation.validateFileType(file.getOriginalFilename());
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public String uploadImage(MultipartFile file) throws IOException, InvalidFileTypeException {
        FileValidation.validateFileType(file.getOriginalFilename());
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "resource_type", "raw"
            ));
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String uploadVideo(MultipartFile file) throws IOException, InvalidFileTypeException {
        FileValidation.validateVideoType(file.getOriginalFilename());
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "video"));
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String deleteFile(String publicId) throws IOException {
        try {
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return deleteResult.get("result").toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
