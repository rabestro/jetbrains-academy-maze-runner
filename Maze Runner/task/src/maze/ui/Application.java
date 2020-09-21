package maze.ui;

import maze.Maze;

import java.util.Scanner;

public class Application {
    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu("=== Menu ===")
            .set(Menu.Property.ERROR, "Incorrect option. Please try again");
    private Maze maze;

    public void startMenu() {
        addStartMenu();
        menu.addExit();
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
        enableFullMenu();
    }

    private void addStartMenu() {
        menu.add("Generate a new maze", this::generateMaze);
        menu.add("Load a maze", this::loadMaze);
    }

    private void enableFullMenu() {
        menu.clear();
        addStartMenu();
        menu.add("Save the maze", this::save);
        menu.add("Display the maze", () -> System.out.println(maze));
        menu.addExit();
    }

    private void save() {

    }
}
