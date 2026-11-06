package presentation;

import java.util.Scanner;

public class ConsoleInput implements Input, AutoCloseable {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
