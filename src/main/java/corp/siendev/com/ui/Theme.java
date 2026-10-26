/**
* Java ON Conf - Malware analysis tool
* Copyright 2026
**/

package corp.siendev.com.ui;

import dev.tamboui.style.Color;

public record Theme (
        Color text,
        Color border
) {
    public static Theme MATRIX = new Theme(
            Color.hex("#005d00"),
            Color.hex("#00de00")
    );
}
