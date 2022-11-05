package com.ninjaone.backendinterviewproject.controller;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

public class ControllerUtils {
    private static String username = "username";
    private static String password = "p@ssword";

    public static String getEncodedPassword() {
        String encodedData = "";
        try {
            encodedData = DatatypeConverter.printBase64Binary((username + ":" + password).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return encodedData;
    }
}
