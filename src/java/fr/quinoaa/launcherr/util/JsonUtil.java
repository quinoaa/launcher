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

public class JsonUtil {
    public static JsonElement mergeJson(JsonElement original, JsonElement overrides){
        merge(original, overrides);
        return original;
    }

    private static void merge(JsonElement original, JsonElement overrides) {

        if (original.isJsonArray() && overrides.isJsonArray()) {
            JsonArray origin = original.getAsJsonArray();
            JsonArray add = overrides.getAsJsonArray();

            for (JsonElement el : add) {
                origin.add(el);
            }
            return;
        }

        if (overrides.isJsonObject() && original.isJsonObject()){
            JsonObject originjson = original.getAsJsonObject();
            JsonObject overridejson = overrides.getAsJsonObject();

            for(String key : overridejson.keySet()){
                JsonElement origin = originjson.get(key);
                JsonElement override = overridejson.get(key);

                if(origin != null && (override.isJsonObject() && origin.isJsonObject()
                        || override.isJsonArray() && origin.isJsonArray())) {
                    merge(origin, override);
                    continue;
                }

                originjson.add(key, override);
            }
        }

    }
}
