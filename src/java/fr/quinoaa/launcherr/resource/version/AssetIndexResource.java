package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.version.AssetIndexData;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.resource.JsonResource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AssetIndexResource extends JsonResource<AssetIndexData> {
    public final String id;
    public final long totalsize;

    public AssetIndexResource(JsonObject obj){
        super(obj.get("url").getAsString(),
                obj.get("sha1").getAsString(),
                getPath(obj),
                obj.get("size").getAsLong());

        id = obj.get("id").getAsString();
        totalsize = obj.get("totalSize").getAsLong();
    }

    private static Path getPath(JsonObject obj){
        return Paths.get("assets", "indexes", obj.get("id").getAsString() + ".json");
    }

    @Override
    public Provider getReader() {
        return AssetIndexData::new;
    }
}
