import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static int[] path = new int[1000];
    static int ctr=0;
    static int[][] a;

    public static void main(String[] args) throws FileNotFoundException, IOException{
        final File file = new File("/Users/jedstevenmorillo/Downloads/skirsesort.kitzbuehel/4x4.txt");
        final BufferedReader br = new BufferedReader(new FileReader(file));
        int j = 0;
        String st = br.readLine();
        addAsArray(st, 0, true);
        while ((st = br.readLine()) != null) {
            addAsArray(st, j++, false);
        }
        /*for (j=0; j<a.length; j++) {
            for (int i=0; i<a[j].length; i++) {
                System.out.print(a[j][i] + " ");
            }
            System.out.println();
        }*/

        System.out.println("Max Length of Calculated Path: " + String.valueOf(longestPath(a, 0,0,0)));
    }

    public static void addAsArray(final String input, final int j, final boolean init) {
        final String[] s = input.split(" ");
        if (init) {
            a = new int[Integer.parseInt(s[0])][Integer.parseInt(s[1])];
        } else {
            for (int i = 0; i < s.length; i++) {
                a[j][i] = Integer.parseInt(s[i]);
            }
        }
    }


    public static int longestPath(final int[][] array, final int i, final int j, int max) {
        if (j==array[0].length)
            return longestPath(array, i+1, 0, max);
        max = Math.max(getNextMax(array, i, j, array[i][j], true), max);
        return longestPath(array, i, j+1, max);
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
    public static int getNextMax(final int[][] array, final int i, final int j, final int prevValue, final boolean first) {

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
    public static int max(final int north, final int south, final int east, final int west) {
        return Math.max(Math.max(north, south), Math.max(east, west));
    }
}
