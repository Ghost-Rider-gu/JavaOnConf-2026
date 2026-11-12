/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.screens;

import corp.siendev.com.ui.Theme;
import corp.siendev.com.utils.AppEventHandler;
import corp.siendev.com.utils.FilePicker;
import corp.siendev.com.utils.PEAnalysis;
import dev.tamboui.layout.Constraint;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.widgets.block.BorderType;
import dev.tamboui.widgets.table.Row;
import dev.tamboui.widgets.table.TableState;

import java.util.ArrayList;
import java.util.List;

import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.grid;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.table;
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
        return grid(drawSignatureAndSectionsData(),
                drawExportImportData())
                .gutter(1);
    }

    private Element drawSignatureAndSectionsData() {
        List<Element> details = new ArrayList<>();

        // add signature details
        details.add(text("[ Signature Details ]").bold().centered().fg(Color.LIGHT_BLUE));
        for (int i = 0; i < peAnalysis.getSignatureDetails().size(); i++) {
            details.add(text(peAnalysis.getSignatureDetails().get(i)).bold().fg(Color.BRIGHT_WHITE));
        }

        // add section details
        details.add(text("[ Section Details ]").bold().centered().fg(Color.LIGHT_BLUE));
        Row[] rows = new Row[peAnalysis.getSections().getNumberOfSections()];
        for (int i = 0; i < peAnalysis.getSections().getNumberOfSections(); i++) {
            rows[i] = Row.from(peAnalysis.getSections().getSectionHeaders().get(i).getName(),
                    "[" + peAnalysis.getSections().getSectionHeaders().get(i).getAlignedPointerToRaw(true) + "]",
                    "[" + peAnalysis.getSections().getSectionHeaders().get(i).getAlignedVirtualAddress(true) + "]");
            if (i % 2 == 0) {
                rows[i].style().bold().fg(Color.BRIGHT_WHITE).bg(Color.GRAY);
            } else {
                rows[i].style().bold().fg(Color.BRIGHT_WHITE).bg(Color.DARK_GRAY);
            }
        }
        details.add(table()
                .header("Section Name", "Aligned Pointer to Raw", "Aligned Virtual Address")
                .rows(rows)
                .widths(
                        Constraint.percentage(30),
                        Constraint.length(40),
                        Constraint.fill()
                )
                .bold()
                .rounded()
                .state(new TableState()));

        return panel("PE Signature and Sections", details.toArray(new Element[0]))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_BLUE);
    }

    private Element drawExportImportData() {
        List<Element> exportsImports = new ArrayList<>();
        // add export details
        exportsImports.add(text("[ Export Details ]").bold().centered().fg(Color.LIGHT_RED));
        if (peAnalysis.getExports().isEmpty()) {
            exportsImports.add(text("No exports for this PE file").bold().fg(Color.RED));
        } else {
            for (int i = 0; i < peAnalysis.getExports().size(); i++) {
                exportsImports.add(text(peAnalysis.getExports().get(i).toString()).bold().fg(Color.BRIGHT_WHITE));
            }
        }

        // add import details
        exportsImports.add(text("[ Import Details ]").bold().centered().fg(Color.LIGHT_RED));
        if (peAnalysis.getImports().isEmpty()) {
            exportsImports.add(text("No imports for this PE file").bold().fg(Color.RED));
        } else {
            for (int i = 0; i < peAnalysis.getImports().size(); i++) {
                String procImports = "";
                if (peAnalysis.getImports().get(i).getNameImports().isEmpty()) {
                    procImports = "No imported procedures from this DLL";
                } else {
                    procImports = peAnalysis.getImports().get(i).getNameImports().getFirst().getName()
                            + " RVA: " + peAnalysis.getImports().get(i).getNameImports().getFirst().getNameRVA();
                }
                exportsImports.add(row(
                        text("DLL Name: [" + peAnalysis.getImports().get(i).getName() + "]").bold().fg(Color.BRIGHT_WHITE),
                        spacer(),
                        text(procImports).dim()
                        ));
            }
        }

        return panel("Exports and Imports", exportsImports.toArray(new Element[0]))
                .borderType(BorderType.HEAVY_DOUBLE_DASHED)
                .borderColor(Color.LIGHT_RED);
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
