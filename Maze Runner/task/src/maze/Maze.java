package maze;

import java.util.BitSet;
import java.util.Random;

public class Maze {
    private final Random random;
    private final int height;
    private final int width;
    private final int rows;
    private final int cols;
    private final BitSet nodes;
    private final int[] edges;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        rows = (height - 1) / 2;
        cols = (width - 1) / 2;
        nodes = new BitSet(rows * cols);
        random = new Random();
        edges = random.ints(rows * (2 * cols - 1), 1, 5).toArray();
    }
}
