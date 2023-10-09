package utils;

public class Config {
    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static final boolean DEBUG = true;
    public static final boolean RUN_TESTS = true;
    public static final String VIEW_MAIN_WIN_CONFIG_PATH = "./src/ui/settings/main.json";
    public static final String OBSTACLE_IMG_PATH = "./assets/obstacle.png";
    public static final String ROBOT_IMG_PATH = "./assets/robot.png";
    public static final String APP_UI_ICON_PATH = "./assets/icon.png";
}
