package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.Resource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AssetResource extends Resource {
    public AssetResource(JsonObject obj){
        super(getUrl(obj), obj.get("hash").getAsString(), getPath(obj), obj.get("size").getAsLong());
    }

    static final String base = "http://resources.download.minecraft.net";
    private static String getUrl(JsonObject obj){
        String hash = obj.get("hash").getAsString();
        return String.join("/", base, hash.substring(0, 2), hash);
    }
    private static Path getPath(JsonObject obj){
        String hash = obj.get("hash").getAsString();
        return Paths.get("assets", "objects", hash.substring(0, 2), hash);
    }
}
