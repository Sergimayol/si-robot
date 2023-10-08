package ui;

import java.util.logging.Logger;

import betterSwing.Section;
import betterSwing.Window;
import betterSwing.utils.DirectionAndPosition;
import utils.Config;

public class View {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private Window window;
    private Map map;

    public View() {
        this.window = new Window(Config.VIEW_MAIN_WIN_CONFIG_PATH);
        this.window.initConfig();
        this.map = new Map(10);
    }

    public void start() {
        logger.info("[VIEW] Starting view...");
        Section section = new Section();
        this.map.initMapTiles();
        section.createFreeSection(this.map);
        this.window.addSection(section, DirectionAndPosition.POSITION_CENTER, "Main");
        this.window.start();
        this.window.reStart();
    }

    public void stop() {
        logger.info("[VIEW] Stopping view...");
        this.window.stop();
    }
}
