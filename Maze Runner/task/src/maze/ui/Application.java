package maze.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import maze.Maze;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    private static final ObjectMapper MAPPER = new XmlMapper();

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
        final var numbers = scanner.nextLine().split(" ");
        final var height = Integer.parseInt(numbers[0]);
        final var width = Integer.parseInt(numbers[1]);
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
        System.out.println("Enter the file name:");
        var fileName = scanner.nextLine();
        LOG.log(Level.INFO, "Saving the maze to file: {0}", fileName);
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), maze);
            LOG.info("The maze has saved successful.");
        } catch (IOException error) {
            LOG.log(Level.SEVERE, "Could not save the maze.", error);
        }
    }
}
