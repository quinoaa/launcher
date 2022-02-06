package fr.quinoaa.launcherr.download;

import fr.quinoaa.launcherr.resource.Resource;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class Downloader {
    Resource[] resources;
    BlockingQueue<Resource> queue;

    public Downloader(Resource... resources){
        this.resources = resources;

        if(resources.length == 0){
            queue = new ArrayBlockingQueue<>(1);
            return;
        }

        queue = new ArrayBlockingQueue<>(resources.length, false, Arrays.asList(resources));
    }

    public void download(Path root) throws IOException {
        Resource res;
        while((res = queue.poll()) != null){
            Path path = res.getPath(root);
            if(!Files.exists(path)){
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
            if(!res.isValid(root)){
                Request.Get(res.url).execute().saveContent(res.getPath(root).toFile());
            }
        }
    }

}




