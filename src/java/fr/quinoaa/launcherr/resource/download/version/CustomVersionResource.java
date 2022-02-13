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

package fr.quinoaa.launcherr.resource.download.version;

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.data.VersionListData;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.download.Downloader;
import fr.quinoaa.launcherr.resource.download.JsonResource;
import fr.quinoaa.launcherr.util.JsonUtil;

import java.io.IOException;
import java.nio.file.Path;

public class CustomVersionResource extends JsonResource<VersionData> {
    VersionListData versionlist;

    public CustomVersionResource(Path path, VersionListData versionlist) {
        this(path, null, versionlist);
    }
    public CustomVersionResource(Path path, String url, VersionListData versionlist) {
        super(url,
                null,
                path,
                -1);
        this.versionlist = versionlist;
    }

    @Override
    public Provider<VersionData> getReader() {
        return VersionData::new;
    }

    @Override
    public VersionData read(Path root) throws IOException {
        JsonObject replace = readJson(root);
        if(!replace.has("inheritsFrom")) return getReader().read(replace);
        String inherits = replace.get("inheritsFrom").getAsString();

        VersionResource version = versionlist.getVersion(inherits);
        Downloader.download(root, version);
        JsonObject original = version.readJson(root);

        original = JsonUtil.mergeJson(original, replace).getAsJsonObject();

        return getReader().read(original);
    }
}
