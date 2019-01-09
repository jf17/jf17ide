package ru.jf17.ide;

import java.io.File;

public class MyCustomFilterGo extends javax.swing.filechooser.FileFilter {
    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".go");
    }
    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Golang documents (*.go)";
    }
}
