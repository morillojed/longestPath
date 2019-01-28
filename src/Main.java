import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    int[] path;
    int ctr=0;
    int[][] a;

    public static void main(String[] args) throws FileNotFoundException, IOException{
        final File file = new File("/Users/jedstevenmorillo/Downloads/skirsesort.kitzbuehel/4x4.txt");
        final BufferedReader br = new BufferedReader(new FileReader(file));
        int j = 0;
        while (br.readLine() != null) {
            addAsArray(br.readLine(), j++);
        }
    }

    public static void addAsArray(final String input, final int j) {
        final String[] s = input.split(" ");
        for (int i=0; i<s.length; i++) {

        }
    }


    public int longestPath(final int[][] array, final int i, final int j, final int max) {
        return 1;
    }

    /**
     * gets the next maximum value.
     * @param array
     * @param i
     * @param j
     * @param prevValue
     * @param first
     * @return
     */
    public int getNextMax(final int[][] array, final int i, final int j, final int prevValue, final boolean first) {

        if (prevValue > array[i][j] || first) {
            path[ctr++] = array[i][j];

            int west = j != 0 ? getNextMax(array, i, j-1, array[i][j], false) : 0;
            int east = j < array[0].length - 1 ? getNextMax(array, i, j+1, array[i][j], false) : 0;
            int south = i < array.length - 1 ? getNextMax(array, i+1, j, array[i][j], false) : 0;
            int north = i != 0 ? getNextMax(array, i-1, j, array[i][j], false) : 0;

            return 1 + max(north, south, east, west);
        }

        return 0;
    }

    /**
     * gets the highest number
     * @param north
     * @param south
     * @param east
     * @param west
     * @return
     */
    public int max(final int north, final int south, final int east, final int west) {
        return Math.max(Math.max(north, south), Math.max(east, west));
    }
}
