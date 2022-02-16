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

package fr.quinoaa.launcherr;

import fr.quinoaa.launcherr.auth.User;
import fr.quinoaa.launcherr.data.VersionListData;
import fr.quinoaa.launcherr.data.version.AssetIndexData;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.download.Downloader;
import fr.quinoaa.launcherr.launch.JavaSettings;
import fr.quinoaa.launcherr.launch.LaunchWrapper;
import fr.quinoaa.launcherr.resource.DownloadResource;
import fr.quinoaa.launcherr.resource.download.JsonResource;
import fr.quinoaa.launcherr.resource.download.ManifestResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Launcher {
    public final Path gamefiles;
    public final Path savefiles;

    public Launcher(Path gamefiles, Path savefiles){
        this.gamefiles = gamefiles;
        this.savefiles = savefiles;
    }


    ManifestResource versionmanifest = new ManifestResource();
    VersionListData versions = null;

    public VersionListData getVersionList(boolean update) throws IOException {
        if(versions != null) return versions;

        if(update){
            Downloader.download(gamefiles, versionmanifest);
        }

        return (versions = versionmanifest.read(gamefiles));
    }
    public VersionListData getVersionList() throws IOException {
        return getVersionList(true);
    }



    public VersionData queryVersionData(JsonResource<VersionData> version) throws IOException {
        if(version.url == null) return queryVersionData(version, false);
        return queryVersionData(version, true);
    }
    public VersionData queryVersionData(JsonResource<VersionData> version, boolean update) throws IOException {
        if(update){
            Downloader.download(gamefiles, version);
        }

        return version.read(gamefiles);
    }

    public List<DownloadResource> getFilesToDownload(VersionData versiondata) throws IOException {
        Downloader.download(gamefiles, versiondata.assets);

        AssetIndexData index = versiondata.assets.read(gamefiles);

        List<DownloadResource> downloads = new ArrayList<>();
        downloads.add(versiondata.client);
        downloads.addAll(Arrays.asList(versiondata.libraries.artifacts));
        downloads.addAll(Arrays.asList(versiondata.libraries.classifiers));
        downloads.addAll(Arrays.asList(index.assets));

        return downloads;
    }
    public void downloadDatas(VersionData versiondata) throws IOException {
        List<DownloadResource> downloads = getFilesToDownload(versiondata);
        Downloader dl = new Downloader(downloads.toArray(new DownloadResource[0]));
        dl.download(gamefiles);
    }

    public LauncherParameters createLaunchParameters(User user, VersionData version, JavaSettings java){
        return new LauncherParameters(user, version, java, this);
    }
    public LaunchWrapper prepareLaunch(VersionData data, LauncherParameters params) throws IOException {
        LaunchWrapper wrapper = new LaunchWrapper(data.launchData, params.gameparams, params.jvmparams,
                params.java, gamefiles, savefiles);

        wrapper.prepareNatives();
        return wrapper;
    }
}
