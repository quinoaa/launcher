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
import fr.quinoaa.launcherr.data.version.AssetIndexData;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.resource.JsonResource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AssetIndexResource extends JsonResource<AssetIndexData> {
    public final String id;
    public final long totalsize;

    public AssetIndexResource(JsonObject obj){
        super(obj.get("url").getAsString(),
                obj.get("sha1").getAsString(),
                getPath(obj),
                obj.get("size").getAsLong());

        id = obj.get("id").getAsString();
        totalsize = obj.get("totalSize").getAsLong();
    }

    private static Path getPath(JsonObject obj){
        return Paths.get("assets", "indexes", obj.get("id").getAsString() + ".json");
    }

    @Override
    public Provider getReader() {
        return AssetIndexData::new;
    }
}
