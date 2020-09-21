package maze;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.IntStream.range;

public class Maze {
    private static final Logger LOG = Logger.getLogger(Maze.class.getName());
    private static final String EMPTY_CELL = "  ";
    private static final String FILL_CELL = "\u2588\u2588";
    private static final int MAX_WEIGHT = 10;

    private int height;
    private int width;
    private BitSet maze;

    public Maze() {
    }

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        maze = new BitSet(height * width);
        maze.set(0, maze.size());
        generate();
    }

    public void generate() {
        final var random = new Random();
        final int rows = (height - 1) / 2;
        final int cols = (width - 1) / 2;
        final int step = 2 * cols - 1;
        final var edges = range(0, 2 * cols * rows - rows - cols)
                .mapToObj(i -> {
                    var isHorizontal = i % step < cols - 1;
                    int row = 1 + i / step * 2 + (i % step < cols - 1 ? 0 : 1);
                    int col = i % step < cols - 1 ? 2 + i % step * 2 : 1 + (i % step - cols + 1) * 2;
                    int mapIndex = row * width + col;
                    int nodeA = isHorizontal ? mapIndex - 1 : mapIndex - width;
                    int nodeB = isHorizontal ? mapIndex + 1 : mapIndex + width;
                    return new Edge(1 + random.nextInt(MAX_WEIGHT), nodeA, nodeB, row * width + col);
                }).toArray(Edge[]::new);

        maze.clear(width + 1);
        for (int i = rows * cols; i > 1; --i) {
            var edge = Arrays.stream(edges)
                    .filter(Edge::isBorder)
                    .min(comparing(Edge::getWeight))
                    .orElseThrow();
            maze.clear(edge.nodeA);
            maze.clear(edge.nodeB);
            maze.clear(edge.mapIndex);
        }
        clearDoors();
    }

    @Override
    public String toString() {
        return range(0, height * width)
                .mapToObj(i -> (i % width == 0 ? "\n" : "") + (maze.get(i) ? FILL_CELL : EMPTY_CELL))
                .collect(Collectors.joining());
    }

    void clearDoors() {
        maze.clear(width);
        int door = width * (height - (height % 2 == 0 ? 2 : 1)) - 1;
        maze.clear(door);
        if (width % 2 == 0) {
            maze.clear(--door);
        }
    }

    class Edge {
        final int weight;
        final int nodeA;
        final int nodeB;
        final int mapIndex;

        Edge(int weight, int nodeA, int nodeB, int mapIndex) {
            this.weight = weight;
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.mapIndex = mapIndex;
        }

        boolean isBorder() {
            return maze.get(nodeA) ^ maze.get(nodeB);
        }

        int getWeight() {
            return weight;
        }

    }
}
