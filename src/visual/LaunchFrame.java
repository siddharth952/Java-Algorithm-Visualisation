package visual;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import core.SortAlgorithm;


/**
 * The window (frame) for choosing which sorting algorithm, array size, etc. to launch.
 */
@SuppressWarnings("serial")
final class LaunchFrame extends Frame implements ActionListener {

    /*---- Fields ----*/

    private final List<SortAlgorithm> algorithms;

    private final TextField arraySizeInput;
    private final TextField scaleInput;
    private final TextField speedInput;

    private final Choice algorithmInput;
    private final Button runButton;



    /*---- Constructor ----*/

    public LaunchFrame(List<SortAlgorithm> algos) {
        // Set window title and closing action
        super("QuickSort Visualization ");
        Objects.requireNonNull(algos);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Create layout manager and set default values
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbl);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(64, 16, 16, 16);
        gbc.weighty = 0;


        /*-- First column --*/
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Create and add label elements
        {
            Label label = new Label("Algorithm Select:");
            gbc.gridy = 0;
            gbl.setConstraints(label, gbc);
            this.add(label);

            label = new Label("Select a Array size:");
            gbc.gridy = 1;
            gbl.setConstraints(label, gbc);
            this.add(label);

            label = new Label("Scale:");
            gbc.gridy = 2;
            gbl.setConstraints(label, gbc);
            this.add(label);

            label = new Label("Speed of Visualization:");
            gbc.gridy = 3;
            gbl.setConstraints(label, gbc);
            this.add(label);
        }


        /*-- Second column --*/
        gbc.gridx = 1;
        gbc.weightx = 2;

        // Drop-down selector for sort algorithm
        algorithms = new ArrayList<>(algos);
        algorithmInput = new Choice();
        for (SortAlgorithm algo : algos)
            algorithmInput.add(algo.getName());
        gbc.gridy = 0;
        gbl.setConstraints(algorithmInput, gbc);
        this.add(algorithmInput);

        // Text field for array size
        arraySizeInput = new TextField("30");
        arraySizeInput.addActionListener(this);
        gbc.gridy = 1;
        gbl.setConstraints(arraySizeInput, gbc);
        this.add(arraySizeInput);

        // Text field for scale
        scaleInput = new TextField("12");
        scaleInput.addActionListener(this);
        gbc.gridy = 2;
        gbl.setConstraints(scaleInput, gbc);
        this.add(scaleInput);

        // Text field for speed
        speedInput = new TextField("10");
        speedInput.addActionListener(this);
        gbc.gridy = 3;
        gbl.setConstraints(speedInput, gbc);
        this.add(speedInput);

        // Run button
        runButton = new Button("Run Algorithm");
        runButton.addActionListener(this);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 8;
        gbc.weighty = 1;
        gbl.setConstraints(runButton, gbc);
        this.add(runButton);

        // Do layout and show
        this.pack();
        Rectangle rect = getGraphicsConfiguration().getBounds();
        this.setLocation(
                (rect.width - this.getWidth()) / 2,
                (rect.height - this.getHeight()) / 3);
        this.setVisible(true);
    }



    /*---- Methods ----*/

    // Called when the run button is clicked or entered is pressed in a text field.
    // Called by the AWT event loop, not by user code.
    public void actionPerformed(ActionEvent ev) {
        // Parse and check input numbers from text fields
        int size, scale;
        double speed;
        try {
            size = Integer.parseInt(arraySizeInput.getText());
            scale = Integer.parseInt(scaleInput.getText());
            speed = Double.parseDouble(speedInput.getText());
        } catch (NumberFormatException e) {
            return;
        }
        if (size <= 0 || scale <= 0 || speed <= 0 || Double.isInfinite(speed) || Double.isNaN(speed))
            return;

        // Initialize objects and worker thread
        final VisualSortArray array = new VisualSortArray(size, scale, speed);
        final SortAlgorithm algorithm = algorithms.get(algorithmInput.getSelectedIndex());
        final int startDelay = 1000;  // In milliseconds
        new Thread() {
            public Thread thread = this;

            public void run() {
                initFrame();
                doSort();
            }

            private void initFrame() {
                // Do component layout
                final Frame sortFrame = new Frame(algorithm.getName());
                sortFrame.add(array.canvas);
                sortFrame.setResizable(false);
                sortFrame.pack();

                // Set window closing action
                sortFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        thread.interrupt();
                        sortFrame.dispose();
                    }
                });

                // Set window position and show
                Rectangle rect = getGraphicsConfiguration().getBounds();
                sortFrame.setLocation(
                        (rect.width - sortFrame.getWidth()) / 8,
                        (rect.height - sortFrame.getHeight()) / 8);
                sortFrame.setVisible(true);
            }

            private void doSort() {
                // Wait and sort
                try {
                    Thread.sleep(startDelay);
                    algorithm.sort(array);
                } catch (StopException|InterruptedException e) {
                    return;
                }

                // Check and print
                String msg;
                try {
                    array.assertSorted();
                    msg = String.format("%s: %d comparisons were made, and %d  swaps",
                            algorithm.getName(), array.getComparisonCount(), array.getSwapCount());
                } catch (AssertionError e) {
                    msg = algorithm.getName() + ": Sorting failed";
                }
                synchronized (System.err) {
                    System.err.println(msg);
                }
            }
        }.start();
    }

}
