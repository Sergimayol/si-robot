package ui;

import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.smm.betterswing.Section;
import org.smm.betterswing.Window;
import org.smm.betterswing.utils.DirectionAndPosition;

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
        this.window.addMenuBar(this.creatMenuBar());
        Section section = new Section();
        this.map.initMapTiles();
        section.createFreeSection(this.createInitPage());
        this.window.addSection(section, DirectionAndPosition.POSITION_CENTER, "Main");
        this.window.start();
        logger.info("[VIEW] View started");
    }

    private JPanel createInitPage() {
        logger.info("[VIEW] Creating init page...");
        JPanel panel = new JPanel();
        JButton button = new JButton("Iniciar Mapa");
        button.addActionListener(e -> {
            Section section = new Section();
            section.createFreeSection(this.map);
            this.map.paintComponent(this.map.getGraphics());
            this.window.updateSection(section, "Main", DirectionAndPosition.POSITION_CENTER);
        });
        panel.add(button);
        return panel;
    }

    public void stop() {
        logger.info("[VIEW] Stopping view...");
        this.window.stop();
    }

    private JMenuBar creatMenuBar() {
        logger.info("[VIEW] Creating menu bar...");
        // TODO: Implementar menu
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem = new JMenuItem("A text-only menu item");
        menuBar.add(menuItem);
        return menuBar;
    }
}
