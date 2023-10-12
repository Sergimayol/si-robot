package env;

import java.awt.Point;
import java.util.Arrays;
import java.util.logging.Logger;

import agent.BaseAgent;
import agent.Executable;

public class Environment<T extends BaseAgent<Executable>> {

    Logger logger = Logger.getLogger(Environment.class.getName());
    private boolean[][] map;
    private T agent;

    public Environment(int mapSize) {
        this.map = new boolean[mapSize][mapSize];
        for (int i = 0; i < mapSize; i++) {
            Arrays.fill(this.map[i], false);
        }
        this.agent = null;
    }

    public Environment(boolean[][] map) {
        this.map = map;
        this.agent = null;
    }

    public boolean hasObstacleIn(int x, int y) {
        return this.map[x][y];
    }

    public void setObstacleIn(int x, int y, boolean value) {
        this.map[y][x] = value;
    }

    public void setAgent(T agent) {
        this.agent = agent;
    }

    public T getAgent() {
        return this.agent;
    }

    public void runNextMovement() {
        printMap();
        boolean[] perceptions = this.getPerceptions(this.agent);
        System.out.println(Arrays.toString(perceptions));
        ((BaseAgent<Executable>) this.agent).processInputSensors(perceptions);
        ((BaseAgent<Executable>) this.agent).checkBC().execute(this.agent);
        logger.info("Agent position: " + ((BaseAgent<Executable>) agent).getPosition());
    }

    public boolean[] getPerceptions(T agent) {
        boolean[] perceptions = new boolean[8];
        int idx = 0;
        Point robotPos = ((BaseAgent<Executable>) agent).getPosition();
        System.out.println("Robot position: " + robotPos);
        for (int y = robotPos.y - 1; y <= robotPos.y + 1; y++) {
            for (int x = robotPos.x - 1; x <= robotPos.x + 1; x++) {
                if (x == robotPos.x && y == robotPos.y) {
                    continue;
                }
                try {
                    perceptions[idx] = map[x][y];
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Map outer perimeter
                    perceptions[idx] = true;
                }
                idx++;
            }
        }
        logger.info("Perceptions: " + Arrays.toString(perceptions));
        return perceptions;
    }

    public void printMap() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map.length; j++) {
                if (i == this.agent.getPosition().x && j == this.agent.getPosition().y) {
                    System.out.print("R ");
                    continue;
                }
                System.out.print(this.map[i][j] ? "X " : "O ");
            }
            System.out.println();
        }
    }

}
