package com.jsp.book.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryHelper {

    private static final String MOVIE_FOLDER = "BMT-Movies";
    private static final String THEATER_FOLDER = "BMT-Theater";
    private static final String QR_FOLDER = "BMT-Theater-QR";

    private static final String FALLBACK_IMAGE = "https://placehold.co/600x400/EEE/31343C";

    private final Cloudinary cloudinary;

    // ✅ Constructor (FIXED - uses individual properties)
    public CloudinaryHelper(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        );

        this.cloudinary = new Cloudinary(config);
    }

    // ✅ Upload Movie Image
    public String generateImageLink(MultipartFile file) {
        return upload(file, MOVIE_FOLDER);
    }

    // ✅ Upload Theater Image
    public String getTheaterImageLink(MultipartFile file) {
        return upload(file, THEATER_FOLDER);
    }

    // ✅ Upload QR Code
    public String saveTicketQr(byte[] qr) {
        return upload(qr, QR_FOLDER);
    }

    /* ---------- Private Helpers ---------- */

    private String upload(MultipartFile file, String folder) {
        try {
            return upload(file.getBytes(), folder);
        } catch (IOException e) {
            e.printStackTrace();
            return FALLBACK_IMAGE;
        }
    }

    @SuppressWarnings("unchecked")
    private String upload(byte[] data, String folder) {
        try {
            Map<String, Object> params = ObjectUtils.asMap(
                    "folder", folder,
                    "use_filename", true
            );

            Map<String, Object> result = cloudinary.uploader().upload(data, params);

            return (String) result.get("url");

        } catch (IOException e) {
            e.printStackTrace();
            return FALLBACK_IMAGE;
        }
    }
}