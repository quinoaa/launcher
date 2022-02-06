package fr.quinoaa.launcherr.resource;

import fr.quinoaa.launcherr.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Resource {
    public final String url;
    public final String hash;
    public final Path relative;
    public final long size;

    public Resource(String url, String hash, Path relative, long size) {
        this.url = url;
        this.hash = hash;
        this.relative = relative;
        this.size = size;
    }

    public Path getPath(Path root){
        return root.resolve(relative);
    }

    public boolean isValid(Path root) throws IOException {
        Path file = getPath(root);

        if(size != -1 && Files.size(file) != size) return false;

        return FileUtil.check(file, hash);
    }
}
