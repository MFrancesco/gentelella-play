package com.github.gentelella.play.security;

import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fre on 15/11/16.
 */
public class CryptoUtils {

    private final String appSecret;

    @Inject
    public CryptoUtils(Configuration configuration){
        this.appSecret = configuration.getString("play.crypto.secret");
        Logger.info("CryptoUtils created, using secret " + appSecret);
    }

    public String encrypt(String value){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(value+appSecret));
            return String.format("%032x", new BigInteger(1, md5.digest()));
        } catch ( NoSuchAlgorithmException e) {
            return value;
        }
    }
}
