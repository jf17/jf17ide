package ru.jf17.ide;

import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
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

            String ext2 = FilenameUtils.getExtension(file.getAbsolutePath()).toUpperCase();

            if(ext2.equals("JAVA")){ new JavaWindow(file);}
            else if(ext2.equals("HTML")){new HTMLWindow(file);}
            else if(ext2.equals("CPP")){new CppWindow(file);}
            else if(ext2.equals("HPP")){new CppWindow(file);}
            else if(ext2.equals("TXT")){new TXTWindow(file);}
            else if(ext2.equals("PIXI")){new PIXIWindow(file);}
            else if(ext2.equals("GO")){new GoWindow(file);}
        }
        return true;
    }
}