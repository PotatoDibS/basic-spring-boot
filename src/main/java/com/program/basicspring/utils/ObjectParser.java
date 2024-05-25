package com.program.basicspring.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectParser {
    
    private static ObjectMapper oMapper = new ObjectMapper();

    public static JSONObject toJSONObject(Object data) {
        try {
            Object obj = data;
            String json = oMapper.writeValueAsString(obj);

            return Optional.ofNullable(new JSONObject(json)).orElse(new JSONObject());
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap (Object data) {
        Map<String, Object> toReturn = new HashMap<>();
        try {
            toReturn = Optional.ofNullable(oMapper.convertValue(data, HashMap.class)).orElse(new HashMap<>());
        } catch (Exception er) {
            toReturn = new HashMap<>();
        }
        return toReturn;
    }

    public static Object toJSON(String data) throws JSONException {
        try { return new JSONObject(data); } 
        catch (Exception e) { 
            try { return new JSONArray(data); } catch (Exception ex) { return data; }
        }
    }

    public static MultiValueMap<String, String> toMultiValueMap(JSONObject _obj) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        try {
            if (!Objects.isNull(_obj)) {
                for (Iterator<String> it = _obj.keys(); it.hasNext();) {
                    String key = it.next();
                    multiValueMap.add(key, _obj.get(key).toString());
                }
            }
        } catch (Exception e) {}        

        return multiValueMap;
    }
}
