package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.Resource;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassifierResource extends Resource {
    List<String> excludes = new ArrayList<>();

    public ClassifierResource(JsonObject parent, JsonObject download, String name) {
        super(download.get("url").getAsString(),
                download.get("sha1").getAsString(),
                Paths.get("libraries", download.get("path").getAsString()),
                download.get("size").getAsLong());

        if(!parent.has("extract")) return;
        JsonObject extract = parent.getAsJsonObject("extract");

        if(extract.has("exclude")){
            for(JsonElement el : extract.getAsJsonArray("exclude")){
                excludes.add(el.getAsString());
            }
        }
    }

    public boolean shouldExtract(String path){
        for(String start : excludes){
            if(path.startsWith(start)) return false;
        }
        return true;
    }
}
