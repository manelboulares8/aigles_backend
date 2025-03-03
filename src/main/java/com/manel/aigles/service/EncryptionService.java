package com.manel.aigles.service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.manel.aigles.model.FormData;
import com.manel.aigles.repos.FormDataRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
@Service
public class EncryptionService {
    private static final String IV = "0123456789123456"; // Remplacez par votre IV
    private static final String SALT = "##-s!m@c19:87+##"; // Remplacez par votre sel

    public String encrypt(final String dataToEncrypt) {
        String encryptedData = null;
        try {
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, IV, SALT);
            final byte[] encryptedByteArray = cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));
            encryptedData = Base64.getEncoder().encodeToString(encryptedByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    private Cipher initCipher(int mode, String iv, String salt) throws Exception {
        SecretKeySpec key = generateKey(salt);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(mode, key, ivSpec);
        return cipher;
    }

    private SecretKeySpec generateKey(String salt) throws Exception {
        byte[] key = (salt).getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(key, "AES");
    }
}
