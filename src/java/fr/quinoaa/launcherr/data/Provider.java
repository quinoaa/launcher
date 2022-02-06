package fr.quinoaa.launcherr.data;

import com.google.gson.JsonElement;

import java.io.IOException;

public interface Provider<T> {
    T read(JsonElement json) throws IOException;
}
