package core;


/**
 * A sorting algorithm, which is a stateless function object. It operates on an array to
 * sort it (completely) in non-descending order. Also it optionally sets the element
 * states (e.g. active/inactive/done) for visualization during the process of sorting.
 */
public interface SortAlgorithm {

    // Sorts the given array using this algorithm. The array is not null.
    public void sort(SortArray array);

    // Returns the name of this sorting algorithm, e.g. "Bubble sort". Not null.
    public String getName();

}
