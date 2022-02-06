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

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.resource.JsonResource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionResource extends JsonResource<VersionData> {
    public final String id;

    public final int compliance;

    public final String type;

    public final String date_release;
    public final String date_update;

    public VersionResource(JsonObject json) {
        super(json.get("url").getAsString(),
                json.get("sha1").getAsString(),
                getPath(json),
                -1);

        id = json.get("id").getAsString();
        compliance = json.get("complianceLevel").getAsInt();
        type = json.get("type").getAsString();
        date_release = json.get("releaseTime").getAsString();
        date_update = json.get("time").getAsString();
    }

    private static Path getPath(JsonObject json){
        String version = json.get("id").getAsString();
        return Paths.get("versions", version, version + ".json");
    }

    @Override
    public Provider getReader() {
        return VersionData::new;
    }
}
