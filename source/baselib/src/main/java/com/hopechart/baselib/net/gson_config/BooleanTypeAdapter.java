package com.hopechart.baselib.net.gson_config;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.hopechart.baselib.utils.Logs;

import java.lang.reflect.Type;

/**
 * @time 2020/4/24
 * @user 张一凡
 * @description
 * @introduction
 */
public class BooleanTypeAdapter implements JsonDeserializer<Boolean> {
    private boolean mDefault = false;

    public BooleanTypeAdapter(){

    }

    public BooleanTypeAdapter(boolean defaultValue){
        this.mDefault = defaultValue;
    }
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonNull()){
            Logs.e("json 为空");
            return mDefault;
        }
        boolean result;
        try{
            result = json.getAsBoolean();
        }catch (NumberFormatException e){
            Logs.e("数值转换失败");
            result = mDefault;
        }
        return result;
    }
}
