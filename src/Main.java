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

/**
 * Gets the Maximum Value of Paths to take while skiing.
 * @author JedMorillo
 */
public class Main {

    private static HashMap<Integer, ArrayList<Integer>> path = new HashMap<>();
    private static ArrayList<Integer> path2;
    private static int ctr=0;
    private static int[][] a;

    /**
     *
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
        final File file = new File("/Users/jedstevenmorillo/Downloads/skirsesort.kitzbuehel/map.txt");
        final BufferedReader br = new BufferedReader(new FileReader(file));
        int j = 0;
        String st = br.readLine();
        addAsArray(st, 0, true);
        while ((st = br.readLine()) != null) {
            addAsArray(st, j++, false);
        }

        final int maximumPath = longestPath(a,0);
        System.out.println("Length of calculated path: " + String.valueOf(maximumPath));

        Set set = path.entrySet();
        Iterator iterator = set.iterator();
        int index = 0;
        int maxDrop = 0;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            ArrayList<Integer> maxPath = (ArrayList)entry.getValue();
            if (maxPath.size() == maximumPath) {
                int drop = maxPath.get(0) - maxPath.get(maxPath.size() - 1);
                if (drop > maxDrop) {
                    maxDrop = drop;
                    index = ((Integer) entry.getKey()).intValue();
                }
            }
        }
        System.out.println("Drop of calculated path: " + maxDrop);
        System.out.println("Calculated path: " + path.get(index));
    }

    /**
     * Adds all the values in an array
     * @param input - Line from file
     * @param j - row
     * @param init - if the line is for the size of the array
     */
    private static void addAsArray(final String input, final int j, final boolean init) {
        final String[] s = input.split(" ");
        if (init) {
            a = new int[Integer.parseInt(s[0])][Integer.parseInt(s[1])];
        } else {
            for (int i = 0; i < s.length; i++) {
                a[j][i] = Integer.parseInt(s[i]);
            }
        }
    }

    /**
     * gets the maximum number of paths.
     * @param array - the whole array
     * @param max - maximum number of paths, initially 0
     * @return - returns the maximum number of paths.
     */
    private static int longestPath(final int[][] array, int max) {
        for (int j = 0; j<array[0].length; j++) {
            for (int i = 0; i<array.length; i++) {
                max = Math.max(getNextMax(array, i, j, array[i][j], true), max);
            }
        }
        return max;
    }

    /**
     * gets the next maximum value.
     * @param array - the whole array
     * @param i - columnIndex
     * @param j - rowIndex
     * @param prevValue - the previous value to compare to current value
     * @param first - if it is the first time the value is being compared to adjacent values
     * @return - returns the next maximum value
     */
    private static int getNextMax(final int[][] array, final int i, final int j, int prevValue, final boolean first) {

        if (i<0 || j<0 || j==array.length || i == array[0].length)
            return 0;

        putPath(first, prevValue);

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
     * gets the highest number.
     * @param north - maximum value of paths if it takes north direction
     * @param south - maximum value of paths if it takes south direction
     * @param east - maximum value of paths if it takes east direction
     * @param west - maximum value of paths if it takes west direction
     * @return - returns highest number.
     */
    private static int max(final int north, final int south, final int east, final int west) {
        return Math.max(Math.max(north, south), Math.max(east, west));
    }

    /**
     * Puts the new path in a Map
     * @param first - if it is the first time the value is being compared to adjacent values
     * @param prevValue - the previous value to be compared to the last value in list
     */
    private static void putPath(final boolean first, final int prevValue) {
        if (first || (path2.size() != 0 && path2.get(path2.size()-1) < prevValue)) {
            path2 = new ArrayList<>();

            if (!first) {
                path2 = new ArrayList<>(path.get(ctr-1));
                while (path2.size() != 0 && prevValue > path2.get(path2.size() - 1)) {
                    path2.remove(path2.size() - 1);
                }
            }
            path.put(ctr++, path2);

        }
    }
}
