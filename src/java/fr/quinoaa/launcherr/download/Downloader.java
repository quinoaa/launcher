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

package fr.quinoaa.launcherr.download;

import fr.quinoaa.launcherr.resource.DownloadResource;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Downloader {
    DownloadResource[] resources;
    BlockingQueue<DownloadResource> queue;

    public Downloader(DownloadResource... resources){
        this.resources = resources;

        if(resources.length == 0){
            queue = new ArrayBlockingQueue<>(1);
            return;
        }

        queue = new ArrayBlockingQueue<>(resources.length, false, Arrays.asList(resources));
    }

    public void download(Path root) throws IOException {
        DownloadResource res;
        while((res = queue.poll()) != null){
            if(res.url==null) continue;
            Path path = res.getPath(root);
            if(!Files.exists(path)){
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            if(!res.isValid(root)){
                Request.Get(res.url).execute().saveContent(res.getPath(root).toFile());
            }
        }
    }


    public static void download(Path root, DownloadResource... resources) throws IOException {
        Downloader dl = new Downloader(resources);
        dl.download(root);
    }
}




