package visual;
import java.util.Arrays;
import algo.*;
import core.SortAlgorithm;



public class Main {

    public static void main(String[] args) {
        System.out.println("Processing!");

        // Set up list of algorithms
        SortAlgorithm[] algos = {
                QuickSortSliding.INSTANCE

        };

        new LaunchFrame(Arrays.asList(algos));

    }
}
