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

package fr.quinoaa.launcherr.resource.download;

import fr.quinoaa.launcherr.data.VersionListData;
import fr.quinoaa.launcherr.data.Provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManifestResource extends JsonResource<VersionListData> {
    public static final String MANIFEST_URI = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";

    public ManifestResource() {
        super(MANIFEST_URI, null, Paths.get("launcher_manifest_v2.json"), -1);
    }

    long expire = 1000 * 60 * 10;
    @Override
    public boolean isValid(Path root) throws IOException {
        Path file = getPath(root);
        if(Files.size(file) == 0) return false;

        long age = System.currentTimeMillis() - Files.getLastModifiedTime(file).toMillis();
        if(age < 0) return false;

        return age < expire;
    }


    @Override
    public Provider<VersionListData> getReader() {
        return VersionListData::new;
    }
}
