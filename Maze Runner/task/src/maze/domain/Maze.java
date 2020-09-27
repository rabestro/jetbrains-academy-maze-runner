package maze.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

public class Maze {
    private static final Logger LOG = Logger.getLogger(Maze.class.getName());
    private static final String CELL_EMPTY = "  ";
    private static final String CELL_WALL = "\u2588\u2588";
    private static final String CELL_PATH = "//";
    private static final int MAX_WEIGHT = 10;
    private static final Random RND = new Random();

    private int height;
    private int width;
    private int start;
    private int finish;
    private BitSet maze;
    private BitSet path;

    public Maze() {
    }

    public Maze(int height, int width) {
        this.height = height % 2 == 0 ? height - 1 : height;
        this.width = width % 2 == 0 ? width - 1 : width;
        maze = new BitSet(height * width);
        path = new BitSet(height * width);
        maze.set(0, maze.size());
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long[] getMap() {
        return maze.toLongArray();
    }

    public void setMap(long[] maze) {
        this.maze = BitSet.valueOf(maze);
        this.path = new BitSet(this.maze.size());
    }

    public Maze generate() {
        final var edgesNumber = height / 2 * (width - 2) - width / 2;
        final var edges = range(0, edgesNumber).mapToObj(Edge::new).toArray(Edge[]::new);

        clearFirstCell();
        final var cellsNumber = (height / 2) * (width / 2);
        range(1, cellsNumber)
                .forEach(i -> Arrays.stream(edges)
                        .filter(Edge::isBorder)
                        .min(comparing(Edge::getWeight))
                        .orElseThrow()
                        .clearEdge());
        clearDoors();
        return this;
    }

    private void clearFirstCell() {
        maze.clear(width + 1);
    }

    private void clearDoors() {
        start = width  + 2 * width * RND.nextInt(height / 2);
        finish = width * (height - (height % 2 == 0 ? 2 : 1)) - 1;

        maze.clear(start);
        maze.clear(finish);
    }


    @Override
    public String toString() {
        path.clear();
        return range(0, height * width).mapToObj(this::getCell).collect(joining());
    }

    public boolean findPath(int index) {
        if (index < 0 || index >= height * width || maze.get(index) || path.get(index)) {
            return false;
        }
        path.set(index);

        if (index == finish
                || findPath(index - 1)
                || findPath(index + 1)
                || findPath(index + width)
                || findPath(index - width)) {
            return true;
        }
        path.clear(index);
        return false;
    }

    @JsonIgnore
    public String getPath() {
        path.clear();
        var isFound = findPath(start);
        LOG.log(Level.FINER, "is the path found: {0}", isFound);
        return range(0, height * width).mapToObj(this::getCell).collect(joining());
    }

    @JsonIgnore
    private String getCell(int index) {
        var separator = index % width == 0 ? "\n" : "";
        if (maze.get(index)) {
            return separator + CELL_WALL;
        }
        if (path.get(index)) {
            return separator + CELL_PATH;
        }
        return separator + CELL_EMPTY;
    }

    class Edge {
        final int mapIndex;
        final int weight;
        final int nodeA;
        final int nodeB;

        Edge(int i) {
            final int cols = width / 2;
            final int edgesRow = width - 2;
            final int row = 1 + i / edgesRow * 2 + (i % edgesRow < cols - 1 ? 0 : 1);
            final int col = i % edgesRow < cols - 1 ? 2 + i % edgesRow * 2 : 1 + (i % edgesRow - cols + 1) * 2;
            var isHorizontal = i % (width - 2) < width / 2 - 1;
            int dx = isHorizontal ? 1 : width;

            this.mapIndex = row * width + col;
            this.nodeA = mapIndex - dx;
            this.nodeB = mapIndex + dx;
            this.weight = 1 + RND.nextInt(MAX_WEIGHT);
        }

        boolean isBorder() {
            return maze.get(nodeA) ^ maze.get(nodeB);
        }

        int getWeight() {
            return weight;
        }

        void clearEdge() {
            maze.clear(nodeA);
            maze.clear(nodeB);
            maze.clear(mapIndex);
        }
    }
}
