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

package fr.quinoaa.launcherr.test;

import fr.quinoaa.launcherr.data.version.LibrariesData;
import fr.quinoaa.launcherr.download.Downloader;
import fr.quinoaa.launcherr.resource.Resource;
import fr.quinoaa.launcherr.resource.version.AssetIndexResource;
import fr.quinoaa.launcherr.resource.ManifestResource;
import fr.quinoaa.launcherr.resource.version.VersionResource;
import fr.quinoaa.launcherr.data.version.AssetIndexData;
import fr.quinoaa.launcherr.data.LauncherData;
import fr.quinoaa.launcherr.data.version.VersionData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;


public class ResourceTest {
    Path root = new File("").toPath();

    Logger logger = Logger.getGlobal();



    @Test
    public void test() throws Exception {
        ManifestResource manifest = new ManifestResource();

        Downloader dl = new Downloader(manifest);
        dl.download(root);


        LauncherData launcherData = manifest.read(root);
        VersionResource ver = launcherData.getVersion("1.8");

        dl = new Downloader(ver);
        dl.download(root);
        VersionData verdata = ver.read(root);
        dl = new Downloader(verdata.assets);
        dl.download(root);

        AssetIndexResource index = verdata.assets;
        AssetIndexData indexdata = index.read(root);


        dl = new Downloader(indexdata.assets);
        dl.download(root);

        LibrariesData libs = verdata.libraries;
        dl = new Downloader(libs.artifacts);
        dl.download(root);
        dl = new Downloader(libs.classifiers);
        dl.download(root);
    }


}
