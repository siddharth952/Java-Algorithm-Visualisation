package core;

public interface SortArray {

    // Must be a non-negative, unchanging value.
    public int length();


    // Compares the values at the two given array indices. Returns a negative number
    // if array[i] < array[j], zero if array[i] == array[j], or a positive number
    // if array[i] > array[j]. Do not assume that this returns only -1, 0, or 1.
    public int compare(int i, int j);


    // Swaps the values at the two given array indices.
    public void swap(int i, int j);


    // Compares the values at the two given array indices, swaps if and
    // only if array[i] > array[j], and returns whether a swap occurred.
    public boolean compareAndSwap(int i, int j);


    // Randomizes the order of all elements in this array.
    public void shuffle();


    // Sets the element at the given index to the given state.
    public void setElement(int index, ElementState state);

    // Sets all elements in the given range [start, end) to the given state.
    public void setRange(int start, int end, ElementState state);



    // The set of all possible states that each array element is in.
    public enum ElementState {
        ACTIVE, INACTIVE, COMPARING, DONE;
    }


}
