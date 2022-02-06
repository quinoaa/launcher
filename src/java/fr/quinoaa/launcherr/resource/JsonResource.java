package fr.quinoaa.launcherr.resource;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.Provider;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class JsonResource<T> extends Resource {
    public JsonResource(String url, String hash, Path relative, long size) {
        super(url, hash, relative, size);
    }

    public abstract Provider<T> getReader();

    public T read(Path root) throws IOException {
        Reader reader = new InputStreamReader(Files.newInputStream(getPath(root)));
        JsonElement json = new Gson().fromJson(reader, JsonObject.class);
        return getReader().read(json);
    }

}
