/**
* Java ON Conf - Malware analysis tool
* Copyright 2026
**/

package corp.siendev.com;

import corp.siendev.com.screens.SplashScreen;
import corp.siendev.com.utils.AppEventHandler;
import dev.tamboui.toolkit.app.ToolkitApp;
import dev.tamboui.toolkit.element.Element;

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
                .onKeyEvent(new AppEventHandler());
    }
}
