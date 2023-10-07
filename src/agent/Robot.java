package agent;

import java.awt.Point;
import java.util.logging.Logger;

public class Robot {

    private Logger logger = Logger.getLogger(Robot.class.getName());
    private Point point;
    private Direction direction;
    private BC bc;

    public Robot() {
        point = new Point(0, 0);
        direction = Direction.NORTH;
        bc = new BC();
        this.initBC();
    }

    private void move() {
        switch (direction) {
            case NORTH -> point.y--;
            case WEST -> point.x--;
            case SOUTH -> point.y++;
            case EAST -> point.x++;
            case NONE -> {
                // Do nothing
            }
        }
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.NORTH;
            case NONE -> {
                // Do nothing
            }
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case WEST -> direction = Direction.NORTH;
            case SOUTH -> direction = Direction.WEST;
            case EAST -> direction = Direction.SOUTH;
            case NONE -> {
                // Do nothing
            }
        }
    }

    public Point getPoint() {
        return point;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * NORTE = x4 and not x1 -> (s8 v s1) and not (s2 v s3)
     * OESTE = x3 and not x4 -> (s6 v s7) and not (s8 v s1)
     * SUR = x2 and not x3 -> (s4 v s5) and not (s6 v s7)
     * ESTE = x1 and not x2 -> (s2 v s3) and not (s4 v s5)
     */
    private void initBC() {
        logger.info("[ROBOT] Initializing BC...");
        boolean t = true;
        boolean f = false;
        // Trapped
        this.bc.addRule(new Rule(f, f, f, f, f, f, f, f, Direction.NONE));
        // Corner top left
        this.bc.addRule(new Rule(f, f, t, t, t, t, t, f, Direction.EAST)); // DERECHA
        // Corner top right
        this.bc.addRule(new Rule(t, f, f, f, t, t, t, t, Direction.SOUTH)); // ABAJO
        // Corner bottom right
        this.bc.addRule(new Rule(t, t, t, f, f, f, t, t, Direction.WEST)); // IZQUIERDA
        // Corner bottom left
        this.bc.addRule(new Rule(t, t, t, t, t, f, f, f, Direction.NORTH)); // ARRIBA

        // Wall top
        this.bc.addRule(new Rule(f, f, f, t, t, t, t, t, Direction.EAST)); // DERECHA
        // Wall right
        this.bc.addRule(new Rule(t, t, f, f, f, t, t, t, Direction.SOUTH)); // ABAJO
        // Wall bottom
        this.bc.addRule(new Rule(t, t, t, t, f, f, f, t, Direction.WEST)); // IZQUIERDA
        // Wall left
        this.bc.addRule(new Rule(f, t, t, t, t, t, f, f, Direction.NORTH)); // ARRIBA

        // Default
        this.bc.addRule(new Rule(t, t, t, t, t, t, t, t, Direction.NORTH));
        logger.info("[ROBOT] BC initialized");
    }

    public void update(boolean s1, boolean s2, boolean s3, boolean s4, boolean s5, boolean s6, boolean s7, boolean s8) {
        logger.info("[ROBOT] received the following sensors: s1=" + s1 + ", s2=" + s2 + ", s3=" + s3 + ", s4=" + s4
                + ", s5=" + s5 + ", s6=" + s6 + ", s7=" + s7 + ", s8=" + s8);
        Rule rule = this.bc.getRule(s1, s2, s3, s4, s5, s6, s7, s8);
        this.direction = rule.direction();
        logger.info("[ROBOT] calculated the following direction: " + this.direction);
        this.move();
    }

    /**
     * !Made for testing purposes, DO NOT USE
     */
    public void setPoint(Point point) {
        this.point = point;
    }

}
