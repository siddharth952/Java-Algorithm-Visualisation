package core;

import java.util.Objects;


/**
 * A convenience base class for sort algorithms.
 * This class implements {@link #getName()} so that subclasses don't need to.
 */
public abstract class AbstractSortAlgorithm implements SortAlgorithm {

    /*---- Fields ----*/

    private final String name;



    /*---- Constructors ----*/

    protected AbstractSortAlgorithm(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }



    /*---- Methods ----*/

    public final String getName() {
        return name;
    }

}
