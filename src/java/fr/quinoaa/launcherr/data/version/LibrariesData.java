package fr.quinoaa.launcherr.data.version;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.version.ArtifactResource;
import fr.quinoaa.launcherr.resource.version.ClassifierResource;
import fr.quinoaa.launcherr.util.OsUtil;
import fr.quinoaa.launcherr.util.RuleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibrariesData {
    public final ArtifactResource[] artifacts;
    public final ClassifierResource[] classifiers;

    public LibrariesData(JsonArray list){
        List<ArtifactResource> artifacts = new ArrayList<>();
        List<ClassifierResource> classifiers = new ArrayList<>();

        for(JsonElement el : list) {
            JsonObject obj = el.getAsJsonObject();

            boolean allow = true;
            if(obj.has("rules"))
                allow = "allow".equals(RuleUtil.getAction(obj.getAsJsonArray("rules")));

            if(!allow) continue;

            JsonObject download = obj.getAsJsonObject("downloads");

            if(download.has("artifact")){
                artifacts.add(new ArtifactResource(download.getAsJsonObject("artifact")));
            }

            if(!obj.has("natives")) continue;
            JsonObject names = obj.getAsJsonObject("natives");

            if(names.has(OsUtil.getOs())){
                String name = OsUtil.format(names.get(OsUtil.getOs()).getAsString());

                JsonObject classifier = download.getAsJsonObject("classifiers").getAsJsonObject(name);
                classifiers.add(new ClassifierResource(obj, classifier, name));

            }
        }

        this.artifacts = artifacts.toArray(new ArtifactResource[0]);
        this.classifiers = classifiers.toArray(new ClassifierResource[0]);
    }
}
