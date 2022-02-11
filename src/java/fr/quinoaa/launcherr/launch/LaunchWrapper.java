/*
 * MIT License
 *
 * Copyright (c) 2022 quinoaa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.quinoaa.launcherr.launch;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class LaunchWrapper {
    LaunchData data;

    public LaunchWrapper(LaunchData data){
        this.data = data;
    }

    public void launch(ParameterProvider args, ParameterProvider jvm, JavaSettings settings) throws IOException {
        launch(args, jvm, settings, new HashMap<>());
    }

    public void launch(ParameterProvider gameargs, ParameterProvider jvmargs, JavaSettings settings,
                       Map<String, Boolean> features) throws IOException {
        List<String> jvm = data.parseJVMArgs(features, jvmargs);
        List<String> game = data.parseGameArgs(features, gameargs);

        List<String> command = new ArrayList<>();

        command.add(settings.java);
        command.addAll(jvm);
        command.addAll(Arrays.asList(settings.extraarguments));
        command.add(data.mainclass);
        command.addAll(game);

        command.replaceAll(str->{
            if(str.contains(" ")) return '"' + str + '"';
            return str;
        });


        Logger.getGlobal().warning(String.join(" ", command));
        ProcessBuilder pb = new ProcessBuilder().command(command);
        pb.redirectOutput(new File("output.txt"));
        pb.redirectError(new File("error.txt"));
        Process p = pb.start();
        while(p.isAlive()) {
            try {
                Thread.sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public interface ParameterProvider {
        String replaceArgument(String key);
    }
}
