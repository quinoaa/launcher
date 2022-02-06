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
