package com.program.basicspring.utils;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class APICaller {
    private static Environment env;

    public APICaller(Environment environment) {
        env = environment;
    }
    
    public static String requestUrlString(String url, JSONObject payload, String contentType, String publicAuth) throws Exception {
		HttpPost post = new HttpPost(url);
    
        post.addHeader("Content-Type", contentType);
        post.addHeader("Authorization", publicAuth);

        if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
            Iterator <String> keys = payload.keys();
            StringBuffer encoded = new StringBuffer();
            while (keys.hasNext()) {
                String key = keys.next();
                if (payload.get(key) instanceof String) {
                    encoded.append(key + "=" + payload.get(key).toString());
                } else {
                    encoded.append(key + "=" + URLEncoder.encode(payload.get(key).toString(), "utf-8"));
                }
    
                if (keys.hasNext()) {
                    encoded.append("&");
                }
            }
            post.setEntity(new StringEntity(encoded.toString()));
        } else if (contentType.equalsIgnoreCase("application/json")){
            post.setEntity(new StringEntity(payload.toString()));
        }

        String result = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            System.out.println("APICaller : " + e.getMessage());
            throw new Exception("Gagal mengambil data ke data source!");
        }

        return result;
    }

    public static String callAPI (String url, String method, JSONObject _reqBody, JSONObject _reqDataObj) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String contentType = _reqDataObj.optString("req_body_type", "application/json");
        String authType = _reqDataObj.optString("auth_type", "");

        // Set Up Headers
        HttpHeaders headers = new HttpHeaders();
        if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        } else {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        if (authType.equalsIgnoreCase("01")) { // Basic Auth
            headers.setBasicAuth(
                _reqDataObj.optString("auth_user", ""), 
                _reqDataObj.optString("auth_password", "")
            );
        } else {
            if (_reqDataObj.has("token")) { headers.add("Authorization", _reqDataObj.getString("token")); }
            headers.add("Authorization", "JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.U2FsdGVkX192oWxGPxpgyK0oRnNf1oBwFBJUsMhdSNReOZ8aRn3yuQ7gjKNorXjmmHjnKDIcl2gL+IFgKPqP7GuVURmkjeZKogcCq/4LFFdfzMVEPqPJ7zgWF8EsbCHHtB8Dw9bwuU8rmGUPq26oynMfr8OvXBTMJ1WbkewNvmwOGeCmKLvGCX1pgxRxCym2hweau+lTfNuemSB8QrpOo1ah/MZJK3v2mHFqsXy2+6w/7Mh5WxtWZczjiv01mTh2/t3OQtOqwHIrxnad//qhGcj81SFQj99HRPXAhTYVG9cHdQJumdFEEn084bf7oaBheg9pWkltbXWJznSiGgCF+aTzr7qa0ABWXQ5v1eZfZf+ExUCoBzk57ssAZSYivLawyYppHHpRpt5EMjjz06KKjXN+Uq3QApsOCDfTREvAbraPjPGTSiq4dSLjLSspqx4kjPdvOsdHynS0ZFKgrI4yJnoqsL60tuL/BfGyZ3Qg5yKL3hMZb+YtFAS6yc7vg53DF3F+kNsVVc+MBW08/Nhu+T0q7WnD3QHMxT5kx2blo57w4QyxO/C5qJxXZyN53+PuMilKSh+4IUxRPUihlSe5Zw==.nygjBHInuktb68CcAVsrYRzrGNPyJWwv8JzM9FT3Fn4");
        }
        
        // Set Up Body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> requesEntity = null;
        if (method.equalsIgnoreCase("GET")) {
            // url = ObjectParser.toQueryParamString(url, _reqBody);
            requesEntity = new HttpEntity<>(headers);
        } else {
            if (headers.getContentType().equals(MediaType.APPLICATION_FORM_URLENCODED)) {
                requestBody = ObjectParser.toMultiValueMap(_reqBody);
                requesEntity = new HttpEntity<>(requestBody, headers);
            } else if (headers.getContentType().equals(MediaType.APPLICATION_JSON)) {
                requesEntity = new HttpEntity<>(_reqBody.toString(), headers);
            }
        }

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                method.equalsIgnoreCase("GET") ? HttpMethod.GET : HttpMethod.POST,
                requesEntity,
                String.class
            );
            
            return responseEntity.getBody();
        } catch (HttpServerErrorException e) {
            Logger.getLogger(APICaller.class.getName())
                .log(Level.SEVERE, "Server Error: " + e.getStatusCode() + " - " + e.getStatusText());
            throw new Exception("Gagal Request API (Server Error): " + e.getStatusCode() + " - " 
                + e.getStatusText()
                + ". Response body: " + e.getResponseBodyAsString()
            );
        } catch (HttpClientErrorException e) {
            Logger.getLogger(APICaller.class.getName())
                .log(Level.SEVERE, "Client Error: " + e.getStatusCode() + " - " + e.getStatusText());
            throw new Exception("Gagal Request API (Client Error): " + e.getStatusCode() + " - " 
                + e.getStatusText()
                + ". Response body: " + e.getResponseBodyAsString()
            );
        } catch (Exception e) {
            Logger.getLogger(APICaller.class.getName()).log(Level.SEVERE, "Gagal melakukan request API!\n" + e.getMessage());
            throw new Exception("Gagal melakukan request API!");
        }
    }

    public static String callAPIErp (String url, JSONObject _reqBody) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Set Up Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(
                env.getProperty("erp.auth.username"), 
                env.getProperty("erp.auth.password")
            );
        
        // Set Up Body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> requesEntity = null;
        requestBody = ObjectParser.toMultiValueMap(_reqBody);
        requesEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requesEntity,
                String.class
            );
            
            return responseEntity.getBody();
        } catch (HttpServerErrorException e) {
            Logger.getLogger(APICaller.class.getName())
                .log(Level.SEVERE, "Server Error: " + e.getStatusCode() + " - " + e.getStatusText());
            throw new Exception("Gagal Request API (Server Error): " + e.getStatusCode() + " - " 
                + e.getStatusText()
                + ". Response body: " + e.getResponseBodyAsString()
            );
        } catch (HttpClientErrorException e) {
            Logger.getLogger(APICaller.class.getName())
                .log(Level.SEVERE, "Client Error: " + e.getStatusCode() + " - " + e.getStatusText());
            throw new Exception("Gagal Request API (Client Error): " + e.getStatusCode() + " - " 
                + e.getStatusText()
                + ". Response body: " + e.getResponseBodyAsString()
            );
        } catch (Exception e) {
            Logger.getLogger(APICaller.class.getName()).log(Level.SEVERE, "Gagal melakukan request API!\n" + e.getMessage());
            throw new Exception("Gagal melakukan request API!");
        }
    }
}
