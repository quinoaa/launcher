package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.Resource;

import java.nio.file.Paths;

public class ArtifactResource extends Resource {

    public ArtifactResource(JsonObject obj) {
        super(obj.get("url").getAsString(),
                obj.get("sha1").getAsString(),
                Paths.get("libraries", obj.get("path").getAsString()),
                obj.get("size").getAsLong());
    }
}
