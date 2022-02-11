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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.launch.LaunchData;
import fr.quinoaa.launcherr.resource.download.version.AssetIndexResource;
import fr.quinoaa.launcherr.resource.download.version.ClientResource;

public class VersionData {
    public final String id;

    public AssetIndexResource assets = null;
    public LibrariesData libraries = null;
    public ClientResource client = null;

    public LaunchData launchData;

    public String type = null;

    public VersionData(JsonElement jsonElement) {
        JsonObject base = jsonElement.getAsJsonObject();

        id = base.get("id").getAsString();
        launchData = LaunchData.get(base);

        if(base.has("assets"))
            assets = new AssetIndexResource(base.getAsJsonObject("assetIndex"));

        if(base.has("libraries"))
            libraries = new LibrariesData(base.getAsJsonArray("libraries"));

        if(base.has("downloads")){
            JsonObject downloads = base.getAsJsonObject("downloads");

            if(downloads.has("client"))
                client = new ClientResource(id, downloads.getAsJsonObject("client"));
        }
        if(base.has("type")){
            type = base.get("type").getAsString();
        }
    }
}
