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

package fr.quinoaa.launcherr.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {
    public static void unzip(Path src, Path out, String... exclude) throws IOException {
        InputStream is = Files.newInputStream(src);
        ZipInputStream zis = new ZipInputStream(is);

        ZipEntry entry;
        byte[] buff = new byte[1024*8];
        int read;
        while((entry = zis.getNextEntry())!=null){
            boolean excluded = false;
            for(String s : exclude) {
                if (entry.getName().startsWith(s)) {
                    excluded = true;
                    break;
                }
            }
            if(excluded) continue;

            Path file = out.resolve(Paths.get(entry.getName()));
            if(!file.normalize().startsWith(out)) continue;

            Files.createDirectories(file.getParent());
            if(entry.isDirectory()) continue;

            if(!Files.exists(file)) Files.createFile(file);

            OutputStream os = Files.newOutputStream(file);
            while((read = zis.read(buff)) != -1){
                os.write(read);
            }
            os.close();
        }

        zis.close();
        is.close();
    }
}
