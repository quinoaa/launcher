package fr.quinoaa.launcherr.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RuleUtil {

    public static String getAction(JsonArray rules){
        return getAction(rules, new HashMap<>());
    }
    public static String getAction(JsonArray rules, Map<String, Boolean> features) {
        String action = null;

        for(JsonElement el : rules){
            JsonObject obj = el.getAsJsonObject();

            boolean apply;
            if(obj.has("os")){
                apply = OsUtil.getOs().equals(obj.getAsJsonObject("os").get("name").getAsString());
            }else{
                apply = true;
            }

            if(obj.has("features") && apply){
                JsonObject required = obj.getAsJsonObject("features");
                for(String key : required.keySet()){
                    if(features.containsKey(key)){
                        apply = features.get(key) == required.get(key).getAsBoolean() && apply;
                    }else{
                        apply = false;
                    }
                }
            }

            if(apply) action = obj.get("action").getAsString();
        }

        return action;
    }
}
