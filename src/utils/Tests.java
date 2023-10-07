package utils;

import agent.Direction;
import agent.Robot;

public class Tests {
    public static void runTests() {
        testCorners();
        testTrap();
        testWalls();
        testDefault();
    }

    private static void testCorners() {
        System.out.println("---- Testing corners ----");
        Robot robot = new Robot();
        // 0,0
        boolean t = true;
        boolean f = false;
        // TOP LEFT
        robot.update(f, f, t, t, t, t, t, f);
        System.out.println(robot.getDirection() == Direction.EAST);
        // TOP RIGHT
        robot.update(t, f, f, f, t, t, t, t);
        System.out.println(robot.getDirection() == Direction.SOUTH);
        // BOTTOM RIGHT
        robot.update(t, t, t, f, f, f, t, t);
        System.out.println(robot.getDirection() == Direction.WEST);
        // BOTTOM LEFT
        robot.update(t, t, t, t, t, f, f, f);
        System.out.println(robot.getDirection() == Direction.NORTH);
    }

    private static void testTrap() {
        System.out.println("---- Testing trap ----");
        Robot robot = new Robot();
        boolean f = false;
        robot.update(f, f, f, f, f, f, f, f);
        System.out.println(robot.getDirection() == Direction.NONE);
    }

    private static void testWalls() {
        System.out.println("---- Testing walls ----");
        Robot robot = new Robot();
        boolean t = true;
        boolean f = false;
        // TOP
        robot.update(f, f, f, t, t, t, t, t);
        System.out.println(robot.getDirection() == Direction.EAST);
        // RIGHT
        robot.update(t, t, f, f, f, t, t, t);
        System.out.println(robot.getDirection() == Direction.SOUTH);
        // BOTTOM
        robot.update(t, t, t, t, f, f, f, t);
        System.out.println(robot.getDirection() == Direction.WEST);
        // LEFT
        robot.update(f, t, t, t, t, t, f, f);
        System.out.println(robot.getDirection() == Direction.NORTH);
    }

    private static void testDefault() {
        System.out.println("---- Testing default ----");
        Robot robot = new Robot();
        boolean t = true;
        robot.update(t, t, t, t, t, t, t, t);
        System.out.println(robot.getDirection() == Direction.NORTH);
    }

}
