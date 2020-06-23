package com.hopechart.baselib.net.gson_config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @time 2020/4/24
 * @user 张一凡
 * @description
 * @introduction
 */
public class StringNullTypeAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if(value == null){
            out.nullValue();
            return;
        }
        out.value(value);

    }

    @Override
    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";
        }

        String jsonStr = reader.nextString();
        if (jsonStr.equals("null")) {
            return "";
        } else {
            return jsonStr;
        }
    }
}
