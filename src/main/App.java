package main;

import ui.View;
import utils.Config;
import utils.Tests;

public class App {
    public static void main(String[] args) {
        if (Config.RUN_TESTS) {
            // Run tests redirecting output to a file " > tests.txt"
            Tests.runTests();
        }

        View view = new View();
        view.start();
    }

}
