package ru.jf17.ide;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

final class FileDropHandler extends TransferHandler {
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType()) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!this.canImport(support))
            return false;

        List<File> files;
        try {
            files = (List<File>) support.getTransferable()
                    .getTransferData(DataFlavor.javaFileListFlavor);
        } catch ( Exception ex) {
            // should never happen (or JDK is buggy)
            return false;
        }

        for (File file: files) {
            // do something...

            String ext2 = FilenameUtils.getExtension(file.getAbsolutePath());

            if(ext2.equals("java")){ new JavaWindow(file);}
            else if(ext2.equals("html")){new HTMLWindow(file);}
            else if(ext2.equals("cpp")){new CppWindow(file);}
        }
        return true;
    }
}