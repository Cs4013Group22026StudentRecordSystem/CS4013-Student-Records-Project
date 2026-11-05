package app;

import presentation.CliApp;
import presentation.ConsoleInput;

public class Main {
    public static void main(String[] args) {
        try (ConsoleInput input = new ConsoleInput()) {
            CliApp cliApp = new CliApp(input);
            cliApp.run();
        }
    }
}