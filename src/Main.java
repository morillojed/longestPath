import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Main {

    static HashMap<Integer, ArrayList<Integer>> path = new HashMap<>();
    static ArrayList<Integer> path2;
    static int ctr=0;
    static int[][] a;
    static int maximumValue = 0;

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

        Set set = path.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            // for (Integer value : entry.getValue())
            System.out.println(entry);
        }
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
        if (i==array.length)
            return max;
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
    public static int getNextMax(final int[][] array, final int i, final int j, int prevValue, final boolean first) {

        if (i<0 || j<0 || j==array.length || i == array[0].length)
            return 0;

        if (first || (path2.size() != 0 && path2.get(path2.size()-1) < prevValue)) {
            path2 = new ArrayList<>();
            path.put(ctr++, path2);

        }

        if (prevValue > array[i][j] || first) {
            prevValue = array[i][j];
            path2.add(Integer.valueOf(prevValue));

            int west = getNextMax(array, i, j-1, prevValue, false);
            int east = getNextMax(array, i, j+1, prevValue, false);
            int south = getNextMax(array, i+1, j, prevValue, false);
            int north = getNextMax(array, i-1, j, prevValue, false);

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
