package utils;

import java.util.Arrays;
import java.util.Scanner;

import agent.Direction;
import agent.Robot;

public class Tests {
    public static void runTests() {
        testCorners();
        testTrap();
        testWalls();
        testDefault();
        miniDemo();
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
        robot.update(t, t, t, t, t, t, t, t);
        System.out.println(robot.getDirection() == Direction.NORTH);
    }

    private static void testDefault() {
        System.out.println("---- Testing default ----");
        Robot robot = new Robot();
        boolean t = true;
        robot.update(t, t, t, t, t, t, t, t);
        System.out.println(robot.getDirection() == Direction.NORTH);
    }

    private static void miniDemo() {
        boolean t = true;
        boolean f = false;
        // Boolean[][] map = {
        // { f, f, f, f, f, f, f },
        // { f, t, t, t, t, t, f },
        // { f, t, t, t, t, t, f },
        // { f, t, t, t, t, t, f },
        // { f, t, t, t, t, t, f },
        // { f, t, t, t, t, t, f },
        // { f, f, f, f, f, f, f },
        // };
        Boolean[][] map = {
                { f, f, f, f, f, f, f },
                { f, t, f, t, t, t, f },
                { f, t, t, t, t, t, f },
                { f, t, t, t, t, t, f },
                { f, t, t, t, t, t, f },
                { f, t, t, t, t, f, f },
                { f, f, f, f, f, f, f },
        };
        System.out.println(map[0].length);
        System.out.println(map.length);
        boolean end = false;
        Scanner scanner = new Scanner(System.in);
        Robot robot = new Robot();
        while (!end) {
            map[robot.getPoint().y][robot.getPoint().x] = null;
            printMap(map);
            System.out.println("Next Step [y/n]: ");
            char x = (char) scanner.next().charAt(0);
            if (x == 'n') {
                end = true;
                continue;
            }
            boolean[] sensors = sense(map, robot);
            System.out.println("Sensors: " + Arrays.toString(sensors));
            map[robot.getPoint().y][robot.getPoint().x] = true;
            robot.update(sensors[0], sensors[1], sensors[2], sensors[3], sensors[4], sensors[5], sensors[6],
                    sensors[7]);
        }

    }

    private static boolean[] sense(Boolean[][] map, Robot robot) {
        // Get the surrounding cells
        int x = robot.getPoint().x;
        int y = robot.getPoint().y;
        boolean[] sensors = new boolean[8];
        // Top left
        sensors[0] = map[y - 1][x - 1];
        // Top
        sensors[1] = map[y - 1][x];
        // Top right
        sensors[2] = map[y - 1][x + 1];
        // Right
        sensors[3] = map[y][x + 1];
        // Bottom right
        sensors[4] = map[y + 1][x + 1];
        // Bottom
        sensors[5] = map[y + 1][x];
        // Bottom left
        sensors[6] = map[y + 1][x - 1];
        // Left
        sensors[7] = map[y][x - 1];
        return sensors;
    }

    private static void printMap(Boolean[][] map) {
        for (Boolean[] row : map) {
            for (Boolean cell : row) {
                if (cell == null) {
                    System.out.print("x");
                    continue;
                }
                System.out.print(cell ? "1" : "0");
            }
            System.out.println();
        }
    }
}
