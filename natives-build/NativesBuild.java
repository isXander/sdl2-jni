import com.badlogic.gdx.jnigen.*;
import com.badlogic.gdx.jnigen.BuildTarget.TargetOs;

import java.io.File;
import java.io.FileNotFoundException;

class NativesBuild {

    static final String win32crossCompilePath="/usr/local/cross-tools/i686-w64-mingw32/bin/";
    static final String win64crossCompilePath="/usr/local/cross-tools/x86_64-w64-mingw32/bin/";
    static final String minSDLversion="2.0.9";
    static final String macLibPath ="/usr/local/lib/libSDL2.a";

    public static void main(String[] args) throws Exception {
        //Deal with arguments
        boolean useSystemSDL = false;
        boolean buildWindows64 = false;
        boolean buildWindows32 = false;
        boolean buildLinux = false;
        boolean buildMac64 = false;
        boolean buildMacArm64 = false;

        for(String s: args) {
            switch (s) {
                case "system-SDL2" -> useSystemSDL = true;
                case "build-windows64" -> buildWindows64 = true;
                case "build-windows32" -> buildWindows32 = true;
                case "build-linux" -> buildLinux = true;
                case "build-mac-x86_64" -> buildMac64 = true;
                case "build-mac-arm64" -> buildMacArm64 = true;
            }
        }

        System.out.println("Using system SDL             (arg: system-SDL2)       " + (useSystemSDL ? "ON" : "OFF"));
        System.out.println("Building for Windows [x64]   (arg: build-windows64)   " + (buildWindows64 ? "ON" : "OFF"));
        System.out.println("Building for Windows [x32]   (arg: build-windows32)   " + (buildWindows32 ? "ON" : "OFF"));
        System.out.println("Building for Linux           (arg: build-linux)       " + (buildLinux ? "ON" : "OFF"));
        System.out.println("Building for macOS [x86_64]  (arg: build-mac_x86_64)  " + (buildMac64 ? "ON" : "OFF"));
        System.out.println("Building for macOS [arm64]   (arg: build-mac_arm64)   " + (buildMacArm64 ? "ON" : "OFF"));
        System.out.println();

        BuildTarget lin64 = BuildTarget.newDefaultTarget(TargetOs.Linux, true);
        BuildTarget win32 = BuildTarget.newDefaultTarget(TargetOs.Windows, false);
        BuildTarget win64 = BuildTarget.newDefaultTarget(TargetOs.Windows, true);
        BuildTarget mac64 = BuildTarget.newDefaultTarget(TargetOs.MacOsX, true);
        BuildTarget macArm64 = BuildTarget.newDefaultTarget(TargetOs.MacOsX, true, true);

        lin64.osFileName = "uncompressed/" + lin64.getTargetFolder();
        win32.osFileName = "uncompressed/" + win32.getTargetFolder();
        win64.osFileName = "uncompressed/" + win64.getTargetFolder();
        mac64.osFileName = "uncompressed/" + mac64.getTargetFolder();
        macArm64.osFileName = "uncompressed/" + macArm64.getTargetFolder();

        // match with maven artifact
        lin64.libName = win32.libName = win64.libName = mac64.libName = macArm64.libName = "sdl2-jni-natives-";
        lin64.libName += "linux64.so";
        win32.libName += "win32.dll";
        win64.libName += "win64.dll";
        mac64.libName += "mac64.dylib";
        macArm64.libName += "mac-arm64.dylib";

        String sdl2Version = null;

        if(buildLinux) {
            sdl2Version = checkSDLVersion("sdl2-config", minSDLversion, sdl2Version);

            lin64.cIncludes = new String[] {};
            String cflags = execCmd("sdl2-config --cflags"); // "-I/usr/local/include/SDL2"
            lin64.cFlags = lin64.cFlags + " "  + cflags;
            lin64.cppFlags = lin64.cFlags;
            lin64.linkerFlags = "-shared -m64";
            String libraries = execCmd("sdl2-config --static-libs").replace("-lSDL2","-l:libSDL2.a" );
            //"-L/usr/local/lib -Wl,-rpath,/usr/local/lib -Wl,--enable-new-dtags -l:libSDL2.a -Wl,--no-undefined -lm -ldl -lsndio -lpthread -lrt";
            lin64.libraries = libraries;
        }

        if(buildMac64){
            sdl2Version = checkSDLVersion("sdl2-config", minSDLversion, sdl2Version);

            mac64.cIncludes = new String[] {};
            //mac64.headerDirs = new String[] {"/usr/local/include/SDL2"};
            String cflags = execCmd("sdl2-config --cflags");
            mac64.cFlags = cflags + " -c -Wall -O2 -arch x86_64 -DFIXED_POINT -fmessage-length=0 -fPIC -mmacosx-version-min=10.9 -stdlib=libc++";
            mac64.cppFlags = mac64.cFlags;
            mac64.linkerFlags = "-shared -arch x86_64 -mmacosx-version-min=10.9";
            mac64.libraries = macLibPath +" -lm -liconv -Wl,-framework,CoreAudio -Wl,-framework,AudioToolbox -Wl,-framework,ForceFeedback -lobjc -Wl,-framework,CoreVideo -Wl,-framework,Cocoa -Wl,-framework,Carbon -Wl,-framework,IOKit -Wl,-weak_framework,QuartzCore -Wl,-weak_framework,Metal";
                    // we cant use:
                    //   execCmd("sdl2-config --static-libs").replace("-lSDL2","-l:libSDL2.a" )
                    // because OSX has Clang, not GCC.  See https://jonwillia.ms/2018/02/02/static-linking for the problem

        }

        if (buildMacArm64) {
            sdl2Version = checkSDLVersion("sdl2-config", minSDLversion, sdl2Version);

            macArm64.cIncludes = new String[] {};
            String cflags = execCmd("sdl2-config --cflags");
            macArm64.cFlags = cflags + " -c -Wall -O2 -arch arm64 -DFIXED_POINT -fmessage-length=0 -fPIC -mmacosx-version-min=10.9 -stdlib=libc++";
            macArm64.cppFlags = macArm64.cFlags;
            macArm64.linkerFlags = "-shared -arch arm64 -mmacosx-version-min=10.9";
            macArm64.libraries = macLibPath +" -lm -liconv -Wl,-framework,CoreAudio -Wl,-framework,AudioToolbox -Wl,-framework,ForceFeedback -lobjc -Wl,-framework,CoreVideo -Wl,-framework,Cocoa -Wl,-framework,Carbon -Wl,-framework,IOKit -Wl,-weak_framework,QuartzCore -Wl,-weak_framework,Metal";
        }

        if(buildWindows64){
            sdl2Version = checkSDLVersion(win64crossCompilePath+"sdl2-config", minSDLversion, sdl2Version);

            win64.cFlags = win64.cFlags + " " +execCmd(win64crossCompilePath+"sdl2-config --cflags");
            win64.cppFlags = win64.cFlags;
            win64.libraries = execCmd(win64crossCompilePath+"sdl2-config --static-libs");
        }

        if (buildWindows32) {
            sdl2Version = checkSDLVersion(win32crossCompilePath+"sdl2-config", minSDLversion, sdl2Version);

            win32.cFlags = win32.cFlags + " " +execCmd(win32crossCompilePath+"sdl2-config --cflags");
            win32.cppFlags = win32.cFlags;
            win32.libraries = execCmd(win32crossCompilePath+"sdl2-config --static-libs");
        }

        //Generate native code, build scripts
        System.out.println("##### GENERATING NATIVE CODE AND BUILD SCRIPTS #####");
        new NativeCodeGenerator().generate("src", "build/classes/java/main/", "jni");

        if (!buildLinux && !buildMac64 && !buildMacArm64 && !buildWindows64 && !buildWindows32) {
            System.out.println("No build targets specified, nothing to do.");
            return;
        }

        new AntScriptGenerator().generate(
                new BuildConfig("sdl2-jni", "build/tmp",  "libs", "jni"), win32, win64, lin64, mac64, macArm64
        );
        System.out.println();

        if(!useSystemSDL) {
            throw new IllegalArgumentException("system SDL must be used!");
        }

        //Build library for all platforms and bitnesses
        if (buildWindows64) {
            System.out.println("##### COMPILING NATIVES FOR WINDOWS x64 #####");
            BuildExecutor.executeAnt("jni/build-windows64.xml", "-Dhas-compiler=true -Drelease=true clean postcompile");
            System.out.println();
        }
        if (buildWindows32) {
            System.out.println("##### COMPILING NATIVES FOR WINDOWS x32 #####");
            BuildExecutor.executeAnt("jni/build-windows32.xml", "-Dhas-compiler=true -Drelease=true clean postcompile");
            System.out.println();
        }
        if (buildLinux) {
            System.out.println("##### COMPILING NATIVES FOR LINUX #####");
            BuildExecutor.executeAnt("jni/build-linux64.xml", "-Dhas-compiler=true -Drelease=true clean postcompile");
            System.out.println();
        }
        if (buildMac64) {
            System.out.println("##### COMPILING NATIVES FOR OSX #####");
            BuildExecutor.executeAnt("jni/build-macosx64.xml", "-Dhas-compiler=true -Drelease=true clean postcompile");
            System.out.println();
        }
        if (buildMacArm64) {
            System.out.println("##### COMPILING NATIVES FOR OSX ARM64 #####");
            BuildExecutor.executeAnt("jni/build-macosxarm64.xml", "-Dhas-compiler=true -Drelease=true clean postcompile");
            System.out.println();
        }

        System.out.println("##### PACKING NATIVES INTO .JAR #####");
        BuildExecutor.executeAnt("jni/build.xml", "pack-natives");
    }

    private static String checkSDLVersion(String command, String minVersion, String currentVersion) throws FileNotFoundException{
        String sdl = "0";
        try {
            sdl = execCmd(command+" --version").trim();
        } catch (Exception e){
            System.out.println("SDL must be installed and "+command+" command must be on path.");
            e.printStackTrace();
        }
        if (currentVersion == null)
            System.out.println("SDL version found: "+sdl);
        if(compareVersions(sdl, minVersion)<0){
            throw new FileNotFoundException("\n!!! SDL version must be >= "+minVersion+" current is "+sdl);
        }
        if (currentVersion != null && compareVersions(sdl, currentVersion) != 0) {
            throw new IllegalStateException("\n!!! SDL version between platforms must match!");
        }

        return sdl;
    }

    public static String execCmd(String cmd) throws java.io.IOException {
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next().trim() : "";
    }

    public static int compareVersions(String v1, String v2) {
        String[] components1 = v1.split("\\.");
        String[] components2 = v2.split("\\.");
        int length = Math.min(components1.length, components2.length);
        for(int i = 0; i < length; i++) {
            int result = Integer.compare(Integer.parseInt(components1[i]), Integer.parseInt(components2[i]));
            if(result != 0) {
                return result;
            }
        }
        return Integer.compare(components1.length, components2.length);
    }
}