package com.salesianos.triana.dam.walleTTicket.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3client;

    private final String bucketName = "walletticket";

    private final String fileUrl = "https://walletticket.s3.eu-west-3.amazonaws.com/";

    private String storeByteArray(byte[] image, String filename, String contentType, long size) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(image)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);
            metadata.setContentType(contentType);
            PutObjectRequest request = new PutObjectRequest(bucketName, filename, inputStream, metadata);

            s3client.putObject(request);

            return fileUrl + filename;

        }
    }

    public String save(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new RuntimeException("Archivo vacío");

            newFilename = filename;
            while (s3client.doesObjectExist(bucketName, newFilename)) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("." + extension, "");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length() - 6);

                newFilename = name + "_" + suffix + "." + extension;
            }

            return storeByteArray(file.getBytes(), newFilename, file.getContentType(), file.getSize());

        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el fichero " + file.getOriginalFilename(), e);
        }
    }

    public boolean deleteImage(String key) {
        try {
            s3client.deleteObject(bucketName, key);
            return true;
        } catch (AmazonServiceException e) {
            throw new RuntimeException("Error al eliminar el fichero " + key, e);
        }
    }
}
