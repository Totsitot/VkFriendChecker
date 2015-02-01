package com.albertik.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Beta on 2/1/15.
 */
public class RequestMaker {
    protected String createRequest(final String uri,Map<String,String> params)
            throws IOException, JSONException {
        String url = uri+"?";
        for(Map.Entry<String,String> key_value : params.entrySet()){
            url+= key_value.getKey()+"="+URLEncoder.encode(key_value.getValue(),"UTF-8")+"&";
        }
        URLConnection connection = new URL(url).openConnection();
        connection.setDoInput(true);
        connection.connect();
        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuffer response = new StringBuffer();
        while (scanner.hasNext()){
            response.append(scanner.next());
        }
        scanner.close();
        return response.toString();
    }
}
