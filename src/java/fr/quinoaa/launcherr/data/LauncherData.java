package fr.quinoaa.launcherr.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.version.VersionResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LauncherData {
    public static final String TYPE_ALPHA = "old_alpha";
    public static final String TYPE_BETA = "old_beta";
    public static final String TYPE_RELEASE = "release";
    public static final String TYPE_SNAPSHOT = "snapshot";

    public JsonObject json;

    public Set<String> types = new HashSet<>();

    List<VersionResource> versions = new ArrayList<>();

    public LauncherData(JsonElement el) throws IOException {
        JsonArray arr = el.getAsJsonObject().getAsJsonArray("versions");
        arr.forEach(ver ->{
            versions.add(new VersionResource(ver.getAsJsonObject()));
        });
    }

    public VersionResource getVersion(String id){
        for(VersionResource res : versions){
            if(res.id.equals(id)){
                return res;
            }
        }
        return null;
    }
}
