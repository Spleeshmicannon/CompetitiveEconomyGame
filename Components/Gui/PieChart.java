package Components.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends JComponent {
    HashMap<Color, Integer> slices;
    private int totalSize;

    /**
     * Initialises the object with default values.
     * @param map the slices of the pie chart
     */
    public PieChart(Map<Color, Integer> map) {
        super();
        this.isDoubleBuffered();

        totalSize = 0;
        this.slices = new HashMap<Color, Integer>();
        for(HashMap.Entry<Color, Integer> slice : map.entrySet()) {
            this.slices.put(slice.getKey(), slice.getValue());
        }
    }

    /**
     * Initialises the object.
     */
    public PieChart() {
        super();
        this.isDoubleBuffered();
        slices = new HashMap<Color, Integer>();
    }


    /**
     * Add a slice to the pie.
     * @param slice the slice
     */
    public void addSlice(HashMap.Entry<Color, Integer> slice) {
        slices.put(slice.getKey(), slice.getValue());
    }


    /**
     * Change a slices size.
     * @param key the slice color
     * @param value the slice size
     */
    public void changeSliceValue(Color key, int value) {
        slices.replace(key, value);
    }

    /**
     * Remove a slice from the pie.
     * @param key the Color of the slice
     */
    public void removeSlice(Color key) {
        slices.remove(key);
    }

    /**
     * Finalises the size of the pie. Must be executed before
     * the paint method runs.
     */
    public void finaliseSize() {
        for(Integer value : slices.values()) {
            totalSize += value;
        }
    }

    /**
     * Creates the graphic for the pie chart
     * @param g the pie chart to draw
     */
    public void paint(Graphics g) {
        if(totalSize == 0) {
            finaliseSize();
        }

        final Rectangle area = getBounds();

        // Initialise the pie chart given the slices
        g.fillOval(area.x - slices.size(), area.y - slices.size(), (area.width)/slices.size() + slices.size()*2, (area.height)/slices.size() + slices.size()*2);

        // Color the given slices into the pie chart
        int curValue = 0;
        for(HashMap.Entry<Color, Integer> entry : slices.entrySet()){
            int startAngle = (int) (curValue * 360 / totalSize);
            int arcAngle = (int) (entry.getValue() * 360 / totalSize);

            g.setColor(entry.getKey());
            g.fillArc(area.x,area.y,area.width/slices.size(),area.height/slices.size(), startAngle, arcAngle);

            curValue += entry.getValue();
        };
    }

    /**
     * Repaints all graphics
     */
    public void repaint() {
        removeAll();
        paint(this.getGraphics());
    }
}