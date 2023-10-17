package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;

import org.smm.betterswing.Section;
import org.smm.betterswing.Window;
import org.smm.betterswing.utils.DirectionAndPosition;

import agent.Robot;
import env.Environment;
import utils.Config;
import utils.FileLogger;
import utils.Helpers;

public class View {

    private Window window;
    private Map map;
    private Environment<Robot> env;
    private Robot robot;
    private JSplitPane splitPane;
    private int mapSize;
    private boolean stop;

    public View() {
        this.window = new Window(Config.VIEW_MAIN_WIN_CONFIG_PATH);
        this.window.initConfig();
        this.mapSize = 10;
        this.robot = new Robot();
        this.env = new Environment<>(this.mapSize);
        this.map = new Map(this.mapSize, this.env);
        this.stop = true;
    }

    public void start() {
        FileLogger.info("[VIEW] Starting view...");
        this.window.addMenuBar(this.creatMenuBar());
        this.map.initMapTiles();
        this.initSplitPane();
        this.splitPane.setRightComponent(this.sideBar());
        Section body = new Section();
        body.createFreeSection(this.createInitPage());
        this.window.addSection(body, DirectionAndPosition.POSITION_CENTER, "Main");
        this.window.start();
        FileLogger.info("[VIEW] View started");
    }

    private void runRobot(Point robotPosition) {
        List<Point> obstaclePositions = this.map.getObstaclePositions();
        this.robot.setPosition(robotPosition.x, robotPosition.y);
        this.map.setEnvironment(this.env);
        this.env.setAgent(this.robot);
        for (Point obstaclePosition : obstaclePositions) {
            this.env.setObstacleIn(obstaclePosition.x, obstaclePosition.y, true);
        }
        final int sleepTime = 500;
        while (!this.stop) {
            // env.printMap();
            Tile tile = this.map.getTile(this.robot.getPosition().x, this.robot.getPosition().y);
            tile.setRobot(false);
            this.env.runNextMovement();
            final Point robotPos = this.env.getAgent().getPosition();
            tile = this.map.getTile(robotPos.x, robotPos.y);
            tile.setRobot(true);
            this.map.setTile(tile);
            // this.map.repaint();
            this.map.paintComponent(this.map.getGraphics());
            this.map.revalidate();
            Helpers.wait(sleepTime);
        }
    }

    private JPanel sideBar() {
        FileLogger.info("[VIEW] Creating side bar...");
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
            this.stop = !this.stop;
            final String action = this.stop ? "Stopping" : "Starting";
            FileLogger.info("[VIEW] " + action + " robot movement ...");
            if (!this.stop) {
                final Point robotPosition = this.map.getRobotPosition();
                if (robotPosition == null) {
                    // Igual es mejor opción que si no se ha seleccionado un punto de inicio, se
                    // seleccione uno aleatorio o (0, 0)
                    JOptionPane.showMessageDialog(null, "No se ha encontrado un robot en el mapa", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    this.stop = !this.stop;
                    return;
                }
                Thread.startVirtualThread(() -> runRobot(robotPosition));
            }
        });
        roboPanel.add(roboButton);
        actionsPanel.add(Box.createVerticalStrut(5));
        actionsPanel.add(solveStrategyPanel);
        actionsPanel.add(Box.createVerticalStrut(15));
        actionsPanel.add(heuristicPanel);
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
            // Solo se puede cambiar el tamaño del mapa si el robot no está en ejecución
            this.stop = true;
            this.mapSize = (int) spinnerModel.getValue();
            this.map.changeMapUIsize(this.mapSize, true);
            this.env = new Environment<>(this.mapSize);
            this.robot = new Robot();
            this.env.setAgent(this.robot);
            this.map.setEnvironment(this.env);
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
        FileLogger.info("[VIEW] Creating init page...");
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
        FileLogger.info("[VIEW] Stopping view...");
        this.window.stop();
    }

    private void initSplitPane() {
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        this.splitPane.setDividerLocation(0.9);
        this.splitPane.setResizeWeight(1.0);
        this.splitPane.setOneTouchExpandable(true);
        this.splitPane.setBorder(null);
    }

    private void showHelpDialog() {
        String helpMessage = "Click derecho: Añadir/Quitar Robot\nClick izquierdo: Añadir/Quitar Obstáculo";
        JOptionPane.showMessageDialog(null, helpMessage, "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menú");
        JMenuItem exitMenuItem = new JMenuItem("Salir");
        JMenuItem helpMenuItem = new JMenuItem("Ayuda");

        exitMenuItem.addActionListener(e -> stop());
        helpMenuItem.addActionListener(e -> showHelpDialog());

        exitMenuItem.setToolTipText("Salir de la aplicación");
        helpMenuItem.setToolTipText("Mostrar ayuda");

        fileMenu.add(helpMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        return menuBar;
    }
}
