/**
* Java ON Conf - Malware analysis tool
* Copyright 2026
**/

package corp.siendev.com.screens;

import corp.siendev.com.ui.Theme;
import corp.siendev.com.utils.AppEventHandler;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;

import java.util.Arrays;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.flow;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;

public class SplashScreen {
    private static final Theme CURRENT_THEME = Theme.MATRIX;
    private static final String BANNER = "::::::::::: :::     :::     :::     :::            ::::::::  ::::    :::        ::::::::   ::::::::  ::::    ::: ::::::::::        ::::::::   :::::::   ::::::::   ::::::::  \n" +
            "    :+:   :+: :+:   :+:     :+:   :+: :+:         :+:    :+: :+:+:   :+:       :+:    :+: :+:    :+: :+:+:   :+: :+:              :+:    :+: :+:   :+: :+:    :+: :+:    :+: \n" +
            "    +:+  +:+   +:+  +:+     +:+  +:+   +:+        +:+    +:+ :+:+:+  +:+       +:+        +:+    +:+ :+:+:+  +:+ +:+                    +:+  +:+  :+:+       +:+  +:+        \n" +
            "    +#+ +#++:++#++: +#+     +:+ +#++:++#++:       +#+    +:+ +#+ +:+ +#+       +#+        +#+    +:+ +#+ +:+ +#+ :#::+::#             +#+    +#+ + +:+     +#+    +#++:++#+  \n" +
            "    +#+ +#+     +#+  +#+   +#+  +#+     +#+       +#+    +#+ +#+  +#+#+#       +#+        +#+    +#+ +#+  +#+#+# +#+                +#+      +#+#  +#+   +#+      +#+    +#+ \n" +
            "#+# #+# #+#     #+#   #+#+#+#   #+#     #+#       #+#    #+# #+#   #+#+#       #+#    #+# #+#    #+# #+#   #+#+# #+#               #+#       #+#   #+#  #+#       #+#    #+# \n" +
            " #####  ###     ###     ###     ###     ###        ########  ###    ####        ########   ########  ###    #### ###              ##########  #######  ##########  ########  ";

    private static final String[] TECHS = {
            "Java", "Assembler", "Rust", "Python", "Go", "TypeScript",
            "C++", "Reverse Engineering", "Kali Linux", "Parrot OS", "NASM", "FASM",
            "JavaScript", "Clojure", "Zig", "Ethical Hacking", "Clouds", "OllyDBG",
            "Lua", "Ghidra", "IDA Pro", "PE", "Linux", "Windows", "WinDBG", "Security",
            "Data", "Algorithms"
    };

    private static final Color[] TECHS_COLORS = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
            Color.CYAN, Color.MAGENTA, Color.WHITE, Color.LIGHT_RED,
            Color.LIGHT_GREEN, Color.LIGHT_BLUE, Color.LIGHT_YELLOW, Color.LIGHT_CYAN,
            Color.LIGHT_RED, Color.BRIGHT_WHITE, Color.DARK_GRAY, Color.GREEN, Color.GRAY,
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.LIGHT_GREEN,
            Color.LIGHT_BLUE, Color.LIGHT_YELLOW, Color.LIGHT_CYAN, Color.LIGHT_RED,
            Color.CYAN, Color.LIGHT_MAGENTA
    };

    public Element render() {
        return dock()
                .top(header())
                .center(mainContent())
                .bottom(footer())
                .focusable()
                .onKeyEvent(new AppEventHandler());
    }

    private Element header() {
        Element[] bannerElements = Arrays.stream(BANNER.split("\n"))
                .map(line -> text(line).fg(CURRENT_THEME.text()).bold().length(1))
                .toArray(Element[]::new);
        return column(
                spacer(),
                column(bannerElements),
                spacer(2),
                column(row(
                 text(" PE ANALYSIS ").fg(CURRENT_THEME.text()),
                 spacer(),
                 text(" to continue, please press F3 and select the file ...").dim(),
                 text(" [h] Help ").dim(),
                 text(" [q] Quit ").dim()
                ).length(1))
        ).id("splash");
    }

    private Element mainContent() {
        return flow(buildTechBoxes())
                .spacing(1)
                .rowSpacing(1)
                .margin(2);
    }

    private Element footer() {
        return row(
                text("Copyright ©2026 is all right").dim(),
                spacer(),
                text("[PE Analysis - malware you shall not pass]").dim()
        ).length(1);
    }

    private Element techBox(String tech, Color boxColor) {
        return panel(text("  " + tech + "  ").bold().fg(boxColor))
                .rounded()
                .borderColor(boxColor);
    }

    private Element[] buildTechBoxes() {
        Element[] techBoxes = new Element[TECHS.length];
        for (int i = 0; i < TECHS.length; i++) {
            techBoxes[i] = techBox(TECHS[i], TECHS_COLORS[i]);
        }
        return techBoxes;
    }
}
