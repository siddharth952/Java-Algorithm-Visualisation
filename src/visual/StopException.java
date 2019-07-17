package visual;



import core.SortAlgorithm;


/**
 * Thrown by {@link VisualSortArray} when a thread stop is requested,
 * in order to terminate the execution of a {@link SortAlgorithm}.
 */
@SuppressWarnings("serial")
final class StopException extends RuntimeException {

    public StopException() {}

}
