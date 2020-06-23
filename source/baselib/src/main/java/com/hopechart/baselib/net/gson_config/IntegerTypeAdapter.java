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
 * @description 用于对返回Int类型为空时的判断
 * @introduction 如果返回空值或者转换为int时失败则默认返回-1
 */
public class IntegerTypeAdapter implements JsonDeserializer<Integer> {
    private int mDefault = -1;

    public IntegerTypeAdapter(){

    }

    public IntegerTypeAdapter(int defaultValue){
        this.mDefault = defaultValue;
    }

    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if(json.isJsonNull()){
            Logs.e("json 为空");
            return mDefault;
        }
        int result;
        try{
            result = json.getAsInt();
        }catch (NumberFormatException e){
            Logs.e("数值转换失败");
            result = mDefault;
        }
        return result;
    }
}
