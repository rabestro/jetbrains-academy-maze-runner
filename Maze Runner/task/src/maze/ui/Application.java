package maze.ui;

import maze.Maze;

import java.util.Scanner;

public class Application {
    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu("=== Menu ===")
            .set(Menu.Property.ERROR, "Incorrect option. Please try again")
            .add("Generate a new maze", this::generateMaze)
            .add("Load a maze", this::loadMaze)
            .addExit();
    private Maze maze;

    public void startMenu() {
        menu.run();
    }

    private void loadMaze() {

    }

    private void generateMaze() {
        System.out.println("Please, enter the size of a maze");
        final var height = scanner.nextInt();
        final var width = scanner.nextInt();
        maze = new Maze(height, width).generate();
        System.out.println(maze);
    }
}
