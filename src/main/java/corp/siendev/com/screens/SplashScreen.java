/**
* Java ON Conf - Malware analysis tool
* Copyright 2026
**/

package corp.siendev.com.screens;

import corp.siendev.com.ui.Theme;
import dev.tamboui.layout.Constraint;
import dev.tamboui.toolkit.element.Element;

import java.util.Arrays;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.dock;
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

    public Element render() {
        return dock()
                .top(header())
                .center(fileChooser())
                .bottom(footer(), Constraint.length(1));
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
                 text(" to continue, please select the file ... ").dim(),
                 text(" [h] Help ").dim(),
                 text(" [q] Quit ").dim()
                ).length(1))
        ).id("splash");
    }

    private Element fileChooser() {
        return panel()
                .id("fileChooser")
                .focusable()
                .rounded()
                .titleEllipsisStart()
                .borderColor(CURRENT_THEME.border())
                .fill();
    }

    private Element footer() {
        return row(
                text("Copyright ©2026 is all right").dim(),
                spacer(),
                text("[PE Analysis - malware you shall not pass]").dim()
        ).length(1);
    }
}
