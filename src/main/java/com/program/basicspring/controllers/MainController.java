package com.program.basicspring.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.program.basicspring.utils.JwtUtil;
import com.program.basicspring.utils.ObjectParser;
import com.program.basicspring.utils.ResponseData;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
    
    @PostMapping("/user")
    public Object postUser(@RequestBody Object reqBody) {    
        return new ResponseEntity<>(reqBody, HttpStatus.valueOf(200));
    }

    @PostMapping("/generate-token")
    public Object generateToken(@RequestBody Object _reqBody) {
        JSONObject reqBody = ObjectParser.toJSONObject(_reqBody);
        String token = jwtUtil.generateToken(reqBody.optString("username", "ADMIN"));
        Map<String, Object> ret = new HashMap<>();
        ret.put("token", token);

        return ResponseData.buildResponseBody(200, "Token berhasil di generate", ret);
    }
    
    
}
