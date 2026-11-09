/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.utils;

import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.toolkit.event.KeyEventHandler;
import dev.tamboui.tui.event.KeyCode;
import dev.tamboui.tui.event.KeyEvent;

public class AppEventHandler implements KeyEventHandler {
    @Override
    public EventResult handle(KeyEvent event) {
        if (event.isChar('h')) {
            System.out.println("was pressed h");
            return EventResult.HANDLED;
        }
        if (event.code() == KeyCode.F3) {
            System.out.println(FilePicker.selectFile());
            return EventResult.HANDLED;
        }
        return EventResult.UNHANDLED;
    }
}
