package maze;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import maze.domain.Maze;
import maze.ui.Menu;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    private static final ObjectMapper MAPPER = new XmlMapper();
    private Maze maze;

    private final Scanner scanner = new Scanner(System.in);
    private final Menu menu = new Menu("=== Menu ===")
                    .add("Generate a new maze", this::generateMaze)
                    .add("Load a maze", this::loadMaze)
                    .add("Save the maze", this::save).disable()
                    .add("Display the maze", this::printMaze).disable()
                    .add("Find the escape", this::printEscape).disable()
                    .set(Menu.Property.ERROR, "Incorrect option. Please try again")
                    .addExit();

    public void run() {
        menu.run();
    }

    private void enableFullMenu() {
        menu.enable("3").enable("4").enable("5");
    }

    private void generateMaze() {
        System.out.println("Please, enter the size of a maze");
        final var size = Integer.parseInt(scanner.nextLine());
        maze = new Maze(size, size).generate();
        System.out.println(maze);
        enableFullMenu();
    }

    private void printMaze() {
        System.out.println(maze);
    }

    private void printEscape() {
        System.out.println(maze.getPath());
    }

    private void loadMaze() {
        try {
            maze = MAPPER.readValue(askFile(), Maze.class);
            LOG.info("The maze has loaded successful.");
            enableFullMenu();
        } catch (IOException error) {
            LOG.log(Level.WARNING, "The maze has not been loaded.", error);
        }
    }

    private void save() {
        try {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(askFile(), maze);
            LOG.info("The maze has saved successful.");
        } catch (IOException error) {
            LOG.log(Level.SEVERE, "Could not save the maze.", error);
        }
    }

    private File askFile() {
        System.out.println("Enter the file name:");
        final var fileName = scanner.nextLine();
        LOG.log(Level.INFO, "The file name is {0}", fileName);
        return new File(fileName);
    }
}
