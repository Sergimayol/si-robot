package ui;

import java.util.logging.Logger;

import betterSwing.Window;
import utils.Config;

public class View {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private Window window;

    public View() {
        this.window = new Window(Config.VIEW_MAIN_WIN_CONFIG_PATH);
        this.window.initConfig();
    }

    public void start() {
        logger.info("[VIEW] Starting view...");
        this.window.start();
    }

    public void stop() {
        logger.info("[VIEW] Stopping view...");
        this.window.stop();
    }
}
