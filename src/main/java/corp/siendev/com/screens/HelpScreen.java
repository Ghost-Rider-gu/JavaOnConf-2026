/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.screens;

import corp.siendev.com.ui.Theme;
import dev.tamboui.toolkit.element.Element;

import java.util.Arrays;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;

public class HelpScreen {
    private static final Theme CURRENT_THEME = Theme.MATRIX;
    private static final String ASCII_LOGO = "                                                                                                                                            \n" +
            "     ▄▄▄   ▄▄▄▄   ▄▄▄▄  ▄▄▄▄   ▄▄▄▄       ▄▄▄▄▄   ▄▄▄    ▄▄▄    ▄▄▄▄▄▄▄   ▄▄▄▄▄   ▄▄▄    ▄▄▄  ▄▄▄▄▄▄▄   ▄▄▄▄▄▄▄    ▄▄▄▄   ▄▄▄▄▄▄▄    ▄▄▄▄▄▄ \n" +
            "     ███ ▄██▀▀██▄ ▀███  ███▀ ▄██▀▀██▄   ▄███████▄ ████▄  ███   ███▀▀▀▀▀ ▄███████▄ ████▄  ███ ███▀▀▀▀▀   ▀▀▀▀████ ▄██████▄ ▀▀▀▀████ ▄███▀▀▀▀ \n" +
            "     ███ ███  ███  ███  ███  ███  ███   ███   ███ ███▀██▄███   ███      ███   ███ ███▀██▄███ ███▄▄         ▄██▀  ███  ███    ▄██▀  ███████▄ \n" +
            "▄▄▄  ███ ███▀▀███  ███▄▄███  ███▀▀███   ███▄▄▄███ ███  ▀████   ███      ███▄▄▄███ ███  ▀████ ███▀▀       ▄███▄▄▄ ███▄▄███  ▄███▄▄▄ ███  ███ \n" +
            " ▀████▀  ███  ███   ▀████▀   ███  ███    ▀█████▀  ███    ███   ▀███████  ▀█████▀  ███    ███ ███        ████████  ▀████▀  ████████ ▀██████▀";

    public Element render() {
        return dock()
                .top(logo())
                .center(helpMenu())
                .bottom(footerHelp());
    }

    private Element helpMenu() {
        return panel(" Menu ",
                  row(text("[F3] Open file    [h] Help    [q] Quit").fg(CURRENT_THEME.text())).length(1))
                .titleCenter()
                .borderColor(CURRENT_THEME.border());
    }

    private Element logo() {
        Element[] ascii = Arrays.stream(ASCII_LOGO.split("\n"))
                .map(line -> text(line).fg(CURRENT_THEME.text()).bold().length(1))
                .toArray(Element[]::new);
        return column(
                spacer(),
                column(ascii),
                spacer(2)
        ).id("ascii_logo");
    }

    private Element footerHelp() {
        return row(
                text("Copyright ©2026").dim(),
                spacer(),
                text("An example of PE Analysis app that we can use for malware detection").dim()
        ).length(1);
    }
}
