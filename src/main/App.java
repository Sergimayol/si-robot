package main;

import agent.Robot;
import env.Environment;
import ui.View;
import utils.Config;

public class App {
    public static void main(String[] args) throws InterruptedException {
        if (Config.RUN_TESTS) {
            // Run tests redirecting output to a file " > tests.txt"
            Robot robot = new Robot();
            robot.setPosition(2, 2);
            Environment<Robot> env = new Environment<>(4);
            env.setAgent(robot);
            env.setObstacleIn(2, 0, true);
            env.setObstacleIn(1, 1, true);
            while (true) {
                env.runNextMovement();
                Thread.sleep(500);
            }
        }
        View view = new View();
        view.start();
    }

}
