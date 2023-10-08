package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import javax.swing.JPanel;

public class Tile extends JPanel {

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private BufferedImage image;
    private BufferedImage robotImage;
    private Color tileColor;
    private Point position;
    private boolean isObstacle;
    private boolean isRobot;

    public Tile(int x, int y, BufferedImage image, BufferedImage robotImage, Color tileColor) {
        this.position = new Point(x, y);
        this.image = image;
        this.robotImage = robotImage;
        this.tileColor = tileColor;
        this.isObstacle = false;
        this.addMouseListener(createMouseListner());
    }

    public void setObstacle(boolean isObstacle) {
        this.isObstacle = isObstacle;
    }

    public void setRobot(boolean isRobot) {
        this.isRobot = isRobot;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        final int width = this.getWidth();
        final int height = this.getHeight();
        this.setBackground(this.tileColor);
        g2d.setColor(this.tileColor);
        g2d.fillRect(0, 0, width, height);
        if (this.isObstacle || this.isRobot) {
            g2d.drawImage(this.isRobot ? this.robotImage : this.image, 0, 0, width, height, null);
        }
    }

    private MouseListener createMouseListner() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                logger.info("[TILE] Clicked on tile: " + Tile.this.position);
                if (Tile.this.isRobot) {
                    return;
                }
                Tile.this.isObstacle = !Tile.this.isObstacle;
                Tile.this.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Do nothing
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Do nothing
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // tileColor = Color.RED;
                // Tile.this.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // tileColor = Color.WHITE;
                // Tile.this.repaint();
            }
        };
    }

}