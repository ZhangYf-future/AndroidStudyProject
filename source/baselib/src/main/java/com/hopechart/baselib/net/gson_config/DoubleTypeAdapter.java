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
public class DoubleTypeAdapter implements JsonDeserializer<Double> {
    private double mDefault = -1D;

    public DoubleTypeAdapter(){

    }

    public DoubleTypeAdapter(double value){
        this.mDefault = value;
    }

    @Override
    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonNull()){
            Logs.e("字段为空");
            return mDefault;
        }
        double result;
        try {
            result = json.getAsDouble();
        }catch (NumberFormatException e){
            Logs.e("数值转换失败");
            result = mDefault;
        }

        return result;
    }
}
