/*
 * MIT License
 *
 * Copyright (c) 2022 quinoaa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.quinoaa.launcherr.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class RuleUtil {

    public static String getAction(JsonArray rules){
        return getAction(rules, new HashMap<>());
    }
    public static String getAction(JsonArray rules, Map<String, Boolean> features) {
        String action = null;

        for(JsonElement el : rules){
            JsonObject obj = el.getAsJsonObject();

            boolean apply = true;
            if(obj.has("os")){
                JsonObject os = obj.getAsJsonObject("os");

                if(os.has("name")){
                    apply = apply && OsUtil.getOs().equalsIgnoreCase(os.get("name").getAsString());
                }
                if(os.has("arch")){
                    apply = apply && OsUtil.getArch().equalsIgnoreCase(os.get("arch").getAsString());
                }
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
