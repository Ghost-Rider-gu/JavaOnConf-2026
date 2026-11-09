/**
 * Java ON Conf - Malware analysis tool
 * Copyright 2026
 **/

package corp.siendev.com.utils;

public class ScreenManager {
    public enum Screen { SPLASH, MAIN, HELP }

    private static final ScreenManager INSTANCE = new ScreenManager();
    private Screen currentScreen = Screen.SPLASH;

    private ScreenManager() {}

    public static ScreenManager getInstance() {
        return INSTANCE;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setScreen(Screen screen) {
        this.currentScreen = screen;
    }
}
