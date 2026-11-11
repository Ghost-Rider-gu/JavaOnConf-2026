/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.screens;

import corp.siendev.com.ui.Theme;
import corp.siendev.com.utils.AppEventHandler;
import corp.siendev.com.utils.FilePicker;
import corp.siendev.com.utils.PEAnalysis;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.widgets.block.BorderType;

import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.grid;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.text;

public class MainScreen {
    private static boolean showDialog = false;
    private static PEAnalysis peAnalysis = null;

    public Element render() {
        if (!showDialog) {
            peAnalysis = new PEAnalysis(FilePicker.selectFile());
            showDialog = true;
        }

        return dock()
                .top(topArea())
                .center(centerArea())
                .bottom(bottomArea())
                .focusable()
                .onKeyEvent(new AppEventHandler());
    }

    private Element centerArea() {
        return grid(drawSignatureData(),
                drawSectionsData(),
                drawExportsData(),
                drawImportData())
                .gutter(1);
    }

    private Element drawSignatureData() {
        return panel("PE Signature", text("[ " + peAnalysis.getSignatureDetails() + " ]").bold().fg(Color.BRIGHT_WHITE))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_BLUE);
    }

    private Element drawSectionsData() {
        return panel("PE Sections", text("[ " + peAnalysis.getSections().getInfo() + " ]").bold().fg(Color.BRIGHT_WHITE))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_RED);
    }

    private Element drawExportsData() {
        return panel("PE Sections", text("[ " + peAnalysis.getExports().toString() + " ]").bold().fg(Color.BRIGHT_WHITE))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_CYAN);
    }

    private Element drawImportData() {
        return panel("PE Sections", text("[ " + peAnalysis.getImports().toString() + " ]").bold().fg(Color.BRIGHT_WHITE))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_YELLOW);
    }

    private Element topArea() {
        return panel(peAnalysis.getFileName(),
                row(text("[analyzing in progress ... please check signature, sections and exports/imports]").dim()))
                .rounded()
                .borderColor(Theme.MATRIX.border());
    }

    private Element bottomArea() {
        return panel("Menu",
                row(text("[F1] Home    [h] Help    [q] Quit").fg(Theme.MATRIX.text())).length(1))
                .rounded()
                .borderColor(Theme.MATRIX.border());
    }
}
