package fr.quinoaa.launcherr.data.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.version.AssetIndexResource;

public class VersionData {

    public final AssetIndexResource assets;

    public final LibrariesData libraries;

    public VersionData(JsonElement jsonElement) {
        JsonObject base = jsonElement.getAsJsonObject();
        assets = new AssetIndexResource(base.getAsJsonObject("assetIndex"));
        libraries = new LibrariesData(base.getAsJsonArray("libraries"));
    }
}
