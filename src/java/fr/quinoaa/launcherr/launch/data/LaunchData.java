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

package fr.quinoaa.launcherr.launch.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.launch.LaunchWrapper;
import fr.quinoaa.launcherr.util.RuleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaunchData {
    public final VersionData version;
    public JsonArray gameargs = null;
    public JsonArray jvmargs = null;

    public String mainclass = null;

    public LaunchData(JsonObject root, VersionData version) {
        this.version = version;

        JsonObject arguments = root.getAsJsonObject("arguments");
        this.jvmargs = arguments.getAsJsonArray("jvm");
        this.gameargs = arguments.getAsJsonArray("game");

        mainclass = root.get("mainClass").getAsString();
    }

    LaunchData(VersionData version){
        this.version = version;
    }

    public List<String> parseGameArgs(Map<String, Boolean> features, LaunchWrapper.ParameterProvider settings){
        return parse(gameargs, features, settings);
    }
    public List<String> parseJVMArgs(Map<String, Boolean> features, LaunchWrapper.ParameterProvider settings){
        return parse(jvmargs, features, settings);
    }

    public List<String> parse(JsonArray array, Map<String, Boolean> features, LaunchWrapper.ParameterProvider settings){
        List<String> arguments = new ArrayList<>();
        for(JsonElement el : array){
            if(!el.isJsonObject()){
                arguments.add(settings.replaceArgument(el.getAsString()));
                continue;
            }

            JsonObject obj = el.getAsJsonObject();
            String result = RuleUtil.getAction(obj.getAsJsonArray("rules"), features);

            if("allow".equalsIgnoreCase(result)){
                JsonElement value = obj.get("value");
                if(!value.isJsonArray()){
                    arguments.add(value.getAsString());
                    continue;
                }
                JsonArray values = value.getAsJsonArray();

                for(JsonElement v : values){
                    arguments.add(settings.replaceArgument(v.getAsString()));
                }
            }
        }
        return arguments;
    }

    public static LaunchData get(JsonObject versionjson, VersionData version){

        if(versionjson.has("arguments"))
            return new LaunchData(versionjson, version);
        if(versionjson.has("minecraftArguments"))
            return new LegacyLaunchData(versionjson, version);

        return null;
    }
}
