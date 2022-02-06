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

package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.Resource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassifierResource extends Resource {
    List<String> excludes = new ArrayList<>();

    public ClassifierResource(JsonObject parent, JsonObject download, String name) {
        super(download.get("url").getAsString(),
                download.get("sha1").getAsString(),
                Paths.get("libraries", download.get("path").getAsString()),
                download.get("size").getAsLong());

        if(!parent.has("extract")) return;
        JsonObject extract = parent.getAsJsonObject("extract");

        if(extract.has("exclude")){
            for(JsonElement el : extract.getAsJsonArray("exclude")){
                excludes.add(el.getAsString());
            }
        }
    }

    public boolean shouldExtract(String path){
        for(String start : excludes){
            if(path.startsWith(start)) return false;
        }
        return true;
    }
}
