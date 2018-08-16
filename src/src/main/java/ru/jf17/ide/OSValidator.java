package ru.jf17.ide;

import javax.swing.*;

public class OSValidator {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public OSValidator() {
    }

    public static boolean isWindows() {

      //  JOptionPane.showMessageDialog(null, OS);

        return (OS.contains("windows"));

    }



    public static boolean isMac() {

        return (OS.contains("mac"));

    }

    public static boolean isUnix() {

        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 );

    }

    public static boolean isSolaris() {

        return (OS.contains("sunos"));

    }

}
