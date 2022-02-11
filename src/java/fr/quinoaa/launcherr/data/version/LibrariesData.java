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

package fr.quinoaa.launcherr.data.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.download.version.ArtifactResource;
import fr.quinoaa.launcherr.resource.download.version.ClassifierResource;
import fr.quinoaa.launcherr.resource.extract.NativeResource;
import fr.quinoaa.launcherr.util.OsUtil;
import fr.quinoaa.launcherr.util.RuleUtil;

import java.util.ArrayList;
import java.util.List;

public class LibrariesData {
    public final ArtifactResource[] artifacts;
    public final ClassifierResource[] classifiers;

    public final NativeResource[] natives;

    public LibrariesData(JsonArray list){
        List<ArtifactResource> artifacts = new ArrayList<>();
        List<ClassifierResource> classifiers = new ArrayList<>();
        List<NativeResource> natives = new ArrayList<>();

        for(JsonElement el : list) {
            JsonObject obj = el.getAsJsonObject();

            boolean allow = true;
            if(obj.has("rules"))
                allow = "allow".equals(RuleUtil.getAction(obj.getAsJsonArray("rules")));

            if(!allow) continue;

            JsonObject download = obj.getAsJsonObject("downloads");

            if(download.has("artifact")){
                artifacts.add(new ArtifactResource(download.getAsJsonObject("artifact")));
            }

            if(!obj.has("natives")) continue;
            JsonObject names = obj.getAsJsonObject("natives");

            if(names.has(OsUtil.getOs())){
                String name = OsUtil.format(names.get(OsUtil.getOs()).getAsString());

                JsonObject classifier = download.getAsJsonObject("classifiers").getAsJsonObject(name);
                ClassifierResource classifierResource = new ClassifierResource(obj, classifier, name);
                classifiers.add(classifierResource);

                List<String> excludelist = new ArrayList<>();
                if(obj.has("extract")){
                    JsonObject extract = obj.getAsJsonObject("extract");

                    if(extract.has("exclude")){
                        JsonArray excludes = extract.getAsJsonArray("exclude");
                        for(JsonElement exclude : excludes) excludelist.add(exclude.getAsString());
                    }
                }
                natives.add(new NativeResource(classifierResource, excludelist.toArray(new String[0])));
            }
        }

        this.artifacts = artifacts.toArray(new ArtifactResource[0]);
        this.classifiers = classifiers.toArray(new ClassifierResource[0]);
        this.natives = natives.toArray(new NativeResource[0]);
    }
}
