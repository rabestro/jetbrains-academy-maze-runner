package maze;

import java.util.BitSet;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class Maze {
    private static final int MAX_WEIGHT = 10;
    private final int height;
    private final int width;
    private final int rows;
    private final int cols;
    private final int step;
    private final BitSet nodes;
    private final int[] edges;
    private final BitSet maze;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        maze = new BitSet(height * width);
        maze.set(0, maze.size());

        rows = (height - 1) / 2;
        cols = (width - 1) / 2;
        step = 2 * cols - 1;
        nodes = new BitSet(rows * cols);
        edges = new Random().ints(rows * step, 1, MAX_WEIGHT).toArray();
        generate();
    }

    boolean isBorder(int edgeIndex) {
        int[] twoNodes = getTwoNodes(edgeIndex);
        return nodes.get(twoNodes[0]) ^ nodes.get(twoNodes[1]);
    }

    int[] getTwoNodes(int edgeIndex) {
        final var isHorizontal = edgeIndex % step < cols;
        int row = edgeIndex / step;
        int col = isHorizontal ? edgeIndex % step : edgeIndex % step - cols + 1;
        int firstNodeIndex = row * cols + col;
        int secondNodeIndex = isHorizontal ? firstNodeIndex + 1 : firstNodeIndex + cols;
        return new int[]{firstNodeIndex, secondNodeIndex};
    }

    void generate() {
        nodes.clear();
        nodes.set(0);
        while (nodes.cardinality() < rows * cols) {
            int edgeIndex = range(0, edges.length).filter(this::isBorder).sorted().findFirst().orElseThrow();
            var twoNodes = getTwoNodes(edgeIndex);
            nodes.set(twoNodes[0]);
            nodes.set(twoNodes[1]);
        }
    }

    @Override
    public String toString() {
        return range(0, height * width)
                .mapToObj(i -> (i % width == 0 ? "\n" : "") + (maze.get(i) ? "\u2588\u2588" : "  "))
                .collect(Collectors.joining());
    }
}
