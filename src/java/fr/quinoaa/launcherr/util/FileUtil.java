package fr.quinoaa.launcherr.util;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtil {
    public static boolean check(Path file, String hash) throws IOException {
        return sha1String(file).equalsIgnoreCase(hash);
    }

    public static String sha1String(Path file) throws IOException {
        byte[] hash = sha1(file);

        return new HexBinaryAdapter().marshal(hash);
    }
    public static byte[] sha1(Path file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            InputStream stream = Files.newInputStream(file);

            int n = 0;
            byte[] buf = new byte[1024*8];
            while(n != -1){
                n = stream.read(buf);
                if(n > 0) digest.update(buf, 0, n);
            }
            stream.close();

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IOException(e);
        }
    }
}
