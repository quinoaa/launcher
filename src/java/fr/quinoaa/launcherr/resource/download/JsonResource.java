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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.resource.DownloadResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class JsonResource<T> extends DownloadResource {
    public JsonResource(String url, String hash, Path relative, long size) {
        super(url, hash, relative, size);
    }

    public abstract Provider<T> getReader();

    public JsonObject readJson(Path root) throws IOException {
        Reader reader = new InputStreamReader(Files.newInputStream(getPath(root)));
        JsonObject json = new Gson().fromJson(reader, JsonObject.class);
        reader.close();
        return json;
    }
    public T read(Path root) throws IOException {
        return getReader().read(readJson(root));
    }

}
