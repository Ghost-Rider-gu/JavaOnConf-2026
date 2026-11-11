/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.utils;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FilePicker {
    public static String selectFile() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose PE file");
        fileChooser.setAcceptAllFileFilterUsed(false);

        FileFilter fileFilter = new FileNameExtensionFilter("PE files", "exe", "dll");
        fileChooser.addChoosableFileFilter(fileFilter);

        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            frame.dispose();
            return fileChooser.getSelectedFile().getAbsolutePath();
        }

        frame.dispose();
        return "No file selected";
    }
}
