package fr.quinoaa.launcherr.resource.version;

import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.Provider;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.resource.JsonResource;

import java.nio.file.Path;
import java.nio.file.Paths;

public class VersionResource extends JsonResource<VersionData> {
    public final String id;

    public final int compliance;

    public final String type;

    public final String date_release;
    public final String date_update;

    public VersionResource(JsonObject json) {
        super(json.get("url").getAsString(),
                json.get("sha1").getAsString(),
                getPath(json),
                -1);

        id = json.get("id").getAsString();
        compliance = json.get("complianceLevel").getAsInt();
        type = json.get("type").getAsString();
        date_release = json.get("releaseTime").getAsString();
        date_update = json.get("time").getAsString();
    }

    private static Path getPath(JsonObject json){
        String version = json.get("id").getAsString();
        return Paths.get("versions", version, version + ".json");
    }

    @Override
    public Provider getReader() {
        return VersionData::new;
    }
}
