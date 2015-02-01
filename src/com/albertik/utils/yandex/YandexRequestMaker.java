package com.albertik.utils.yandex;

import com.albertik.request.RequestMaker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Beta on 2/1/15.
 */
public class YandexRequestMaker extends RequestMaker {
    public static final String url = "http://speller.yandex.net/services/spellservice.json/checkText";

    public JSONArray createRequest(final String lang,final int option,final String text) throws IOException, JSONException {
        Map<String,String> map = new HashMap<String, String>();
        map.put("lang",lang);
        map.put("options",option+"");
        map.put("text",text);
        return new JSONArray(super.createRequest(url,map));

    }
}
