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
            ScreenManager.getInstance().setScreen(ScreenManager.Screen.HELP);
            return EventResult.HANDLED;
        }
        if (event.code() == KeyCode.F3) {
            ScreenManager.getInstance().setScreen(ScreenManager.Screen.MAIN);
            return EventResult.HANDLED;
        }
        return EventResult.UNHANDLED;
    }
}
