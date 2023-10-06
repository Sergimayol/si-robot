package utils;

public class Config {
    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static final boolean DEBUG = true;
    public static final String VIEW_MAIN_WIN_CONFIG_PATH = "./src/ui/settings/main.json";
}
