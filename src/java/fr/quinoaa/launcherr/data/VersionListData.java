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

package fr.quinoaa.launcherr.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.download.version.VersionResource;

import java.io.IOException;
import java.util.*;

public class VersionListData {
    public static final String TYPE_ALPHA = "old_alpha";
    public static final String TYPE_BETA = "old_beta";
    public static final String TYPE_RELEASE = "release";
    public static final String TYPE_SNAPSHOT = "snapshot";

    public JsonObject json;

    public Set<String> types = new HashSet<>();

    List<VersionResource> versions = new ArrayList<>();

    public VersionListData(JsonElement el) throws IOException {
        JsonArray arr = el.getAsJsonObject().getAsJsonArray("versions");
        arr.forEach(ver -> versions.add(new VersionResource(ver.getAsJsonObject())));
        versions = Collections.unmodifiableList(versions);
    }

    public VersionResource getVersion(String id){
        for(VersionResource res : versions){
            if(res.id.equals(id)){
                return res;
            }
        }
        return null;
    }

    public List<VersionResource> getAllVersions(){
        return versions;
    }
}
