package fr.quinoaa.launcherr.resource;

import fr.quinoaa.launcherr.data.LauncherData;
import fr.quinoaa.launcherr.data.Provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManifestResource extends JsonResource<LauncherData> {
    public static final String MANIFEST_URI = "https://launchermeta.mojang.com/mc/game/version_manifest_v2.json";

    public ManifestResource() {
        super(MANIFEST_URI, null, Paths.get("launcher_manifest_v2.json"), -1);
    }

    long expire = 1000 * 60 * 10;
    @Override
    public boolean isValid(Path root) throws IOException {
        long age = System.currentTimeMillis() - Files.getLastModifiedTime(getPath(root)).toMillis();
        if(age < 0) return false;

        return age < expire;
    }


    @Override
    public Provider getReader() {
        return LauncherData::new;
    }
}
