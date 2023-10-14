package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.smm.betterswing.Section;
import org.smm.betterswing.Window;
import org.smm.betterswing.utils.DirectionAndPosition;

import utils.Config;
import utils.Helpers;

public class View {

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private Window window;
    private Map map;
    private JSplitPane splitPane;
    private int mapSize;

    public View() {
        this.window = new Window(Config.VIEW_MAIN_WIN_CONFIG_PATH);
        this.window.initConfig();
        this.mapSize = 10;
        this.map = new Map(this.mapSize);
    }

    public void start() {
        logger.info("[VIEW] Starting view...");
        this.window.addMenuBar(this.creatMenuBar());
        this.map.initMapTiles();
        this.initSplitPane();
        this.splitPane.setRightComponent(this.sideBar());
        Section body = new Section();
        body.createFreeSection(this.createInitPage());
        this.window.addSection(body, DirectionAndPosition.POSITION_CENTER, "Main");
        this.window.start();
        logger.info("[VIEW] View started");
    }

    private JPanel sideBar() {
        logger.info("[VIEW] Creating side bar...");
        // TODO: Añadir opciones de cambiar tamaño de mapa y poner y quitar al robot
        JPanel sideBar = new JPanel();
        sideBar.setBackground(Color.WHITE);
        sideBar.setLayout(new GridLayout(3, 1));

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.WHITE);
        JLabel icon = new JLabel();
        icon.setBackground(Color.WHITE);
        icon.setIcon(Helpers.escalateImageIcon(Config.APP_UI_ICON_PATH, 128, 128));
        iconPanel.add(icon);
        logoPanel.add(iconPanel);
        sideBar.add(logoPanel);

        // Actions panel
        JPanel actionsPanel = new JPanel();
        actionsPanel.setBackground(Color.WHITE);
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        JPanel solveStrategyPanel = new JPanel();
        solveStrategyPanel.setBackground(Color.WHITE);
        solveStrategyPanel.setLayout(new BoxLayout(solveStrategyPanel, BoxLayout.Y_AXIS));
        solveStrategyPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JPanel heuristicPanel = new JPanel();
        heuristicPanel.setLayout(new BoxLayout(heuristicPanel, BoxLayout.Y_AXIS));
        heuristicPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        heuristicPanel.setBackground(Color.WHITE);

        final String fontName = "Arial";

        JPanel resetImgPanel = new JPanel();
        resetImgPanel.setBackground(Color.WHITE);
        resetImgPanel.setLayout(new BoxLayout(resetImgPanel, BoxLayout.Y_AXIS));
        // Crear el JSpinner utilizando el modelo
        JButton resetImgBtn = new JButton("TODO");

        // Personalizar la apariencia del JSpinner
        resetImgBtn.setFont(new Font(fontName, Font.PLAIN, 14));

        // Put the same start location for both components
        resetImgBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Change the size of the puzzle to be the same as the label size
        resetImgBtn.setMaximumSize(new Dimension(
                (int) resetImgBtn.getPreferredSize().getWidth() + 20,
                (int) resetImgBtn.getPreferredSize().getHeight() + 15));

        resetImgPanel.add(resetImgBtn);

        JPanel roboPanel = new JPanel();
        roboPanel.setBackground(Color.WHITE);
        roboPanel.setLayout(new BoxLayout(roboPanel, BoxLayout.Y_AXIS));
        JButton roboButton = new JButton(Helpers.escalateImageIcon(Config.APP_UI_ICON_PATH, 64, 64));
        roboButton.setFont(new Font(fontName, Font.PLAIN, 14));
        roboButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        roboButton.setMaximumSize(new Dimension(
                (int) roboButton.getPreferredSize().getWidth() + 10,
                (int) roboButton.getPreferredSize().getHeight() + 5));

        roboButton.addActionListener(e -> {
            // Iniciar programa
        });
        roboPanel.add(roboButton);
        actionsPanel.add(Box.createVerticalStrut(5));
        actionsPanel.add(solveStrategyPanel);
        actionsPanel.add(Box.createVerticalStrut(15));
        actionsPanel.add(heuristicPanel);
        actionsPanel.add(Box.createVerticalStrut(5));
        actionsPanel.add(resetImgPanel);
        actionsPanel.add(Box.createVerticalStrut(5));
        actionsPanel.add(roboPanel);
        actionsPanel.add(Box.createVerticalStrut(5));
        sideBar.add(actionsPanel);

        // Add size to the puzzle
        JPanel puzzleSizePanel = new JPanel();
        puzzleSizePanel.setBackground(Color.WHITE);
        puzzleSizePanel.setLayout(new BoxLayout(puzzleSizePanel, BoxLayout.Y_AXIS));
        JLabel puzzleSizeLabel = new JLabel("Tamaño del mapa:");

        // Crear un modelo para el JSpinner con un rango de valores válidos
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(this.mapSize, 1, 50, 1);
        spinnerModel.addChangeListener(e -> {
            final int size = (int) spinnerModel.getValue();
            this.map.changeMapUIsize(size, true);
        });

        // Crear el JSpinner utilizando el modelo
        JSpinner puzzleSize = new JSpinner(spinnerModel);

        // Personalizar la apariencia del JSpinner
        puzzleSize.setFont(new Font(fontName, Font.PLAIN, 14));

        // Change the size of the puzzle to be the same as the label size
        puzzleSize.setMaximumSize(new Dimension(
                (int) puzzleSizeLabel.getPreferredSize().getWidth() + 20,
                (int) puzzleSizeLabel.getPreferredSize().getHeight() + 15));

        // Put the same start location for both components
        puzzleSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        puzzleSize.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar el JSpinner al panel puzzleSizePanel
        puzzleSizePanel.add(puzzleSizeLabel);
        puzzleSizePanel.add(puzzleSize);
        puzzleSizePanel.add(Box.createVerticalStrut(20));

        sideBar.add(puzzleSizePanel);

        return sideBar;
    }

    private JPanel createInitPage() {
        logger.info("[VIEW] Creating init page...");
        JPanel panel = new JPanel();
        JButton button = new JButton("Iniciar Mapa");
        button.addActionListener(e -> {
            this.map.paintComponent(this.map.getGraphics());
            splitPane.setLeftComponent(this.map);
            Section body = new Section();
            body.createJSplitPaneSection(splitPane);
            this.window.updateSection(body, "Main", DirectionAndPosition.POSITION_CENTER);
        });
        panel.add(button);
        return panel;
    }

    public void stop() {
        logger.info("[VIEW] Stopping view...");
        this.window.stop();
    }

    private void initSplitPane() {
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(0.9);
        this.splitPane.setResizeWeight(1.0);
        this.splitPane.setOneTouchExpandable(true);
        this.splitPane.setBorder(null);
    }

    private void resetMap() {
        map.changeMapUIsize(mapSize, true); // Restablece el mapa al tamaño original
        map.repaint(); // Actualiza la vista
    }

    private void cleanMap() {
        map.cleanMap(); // Limpia el mapa
        map.repaint(); // Actualiza la vista
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menú");
        JMenuItem resetMenuItem = new JMenuItem("Reiniciar");
        JMenuItem cleanMenuItem = new JMenuItem("Limpiar");
        resetMenuItem.addActionListener(e -> resetMap());
        cleanMenuItem.addActionListener(e -> cleanMap());
        fileMenu.add(resetMenuItem);
        fileMenu.add(cleanMenuItem);
        menuBar.add(fileMenu);

        return menuBar;
    }
}
