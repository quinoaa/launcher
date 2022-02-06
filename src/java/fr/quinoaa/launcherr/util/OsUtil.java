package fr.quinoaa.launcherr.util;

public class OsUtil {
    public static String getOs(){
        String osname = System.getProperty("os.name").toLowerCase();

        if(osname.contains("win")){
            return "windows";
        }else if(osname.contains("mac") || osname.contains("darwin")){
            return "osx";
        }else{
            return "linux";
        }
    }

    public static String getArch(){
        if(System.getProperty("os.arch").contains("64")) return "64";
        return "32";
    }

    public static String format(String name){
        return name.replace("${arch}", getArch());
    }
}
