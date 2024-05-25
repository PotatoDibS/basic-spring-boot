package com.program.basicspring.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseData {
    public static ResponseEntity<Object> buildResponseBody (int statusCode, String message, Object payload) { return createResponseBody(statusCode, message, payload); }
    public static ResponseEntity<Object> buildResponseBody (int statusCode, String message) { return createResponseBody(statusCode, message, null); }
    public static ResponseEntity<Object> buildResponseBody (int statusCode, Object payload) { return createResponseBody(statusCode, null, payload); }

    private static ResponseEntity<Object> createResponseBody (Integer statusCode, String message, Object payload) {
        Map<String, Object> obj = new HashMap<>();
        obj.put("success", ((statusCode / 200) >= 1 && (statusCode / 200) < 1.5));
        obj.put("statusCode", statusCode);
        obj.put("statusName", HttpStatus.valueOf(statusCode));
        obj.put("message", message);     
        obj.put("data", payload);

        return new ResponseEntity<>(obj, HttpStatus.valueOf(statusCode));
    }

}
