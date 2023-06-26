package com.albo.marvel.config;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class EncrycpKey {


    public String encrycpCode(String tsKey, String privateKey, String publicKey) {

        String encript = tsKey.concat(privateKey.concat(publicKey));
        String encodKey;
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(encript.getBytes(), 0, encript.length());
            encodKey = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error al Generar la Encriptación");
            throw new RuntimeException(e.getMessage());
        }
        return encodKey;
    }
}
