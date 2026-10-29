/**
* Java ON Conf - Malware analysis tool
* Copyright 2026
**/

package corp.siendev.com;

import corp.siendev.com.screens.SplashScreen;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.column;

public class JavaConf extends ToolkitApp {
    public static void main(String[] args) throws Exception {
        new JavaConf().run();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected Element render() {
        return column(new SplashScreen().render())
                .id("root")
                .focusable()
                .onKeyEvent(this::handleKeyEvent);
    }

    private EventResult handleKeyEvent(KeyEvent event) {
        if (event.isQuit()) {
            quit();
            return EventResult.HANDLED;
        }
        return EventResult.UNHANDLED;
    }
}
