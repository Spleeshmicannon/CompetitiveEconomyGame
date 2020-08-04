package Components.Gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PieChart extends JComponent {
    HashMap<Color, Double> slices;
    private Double totalSize;

    /**
     * Initialises the object with default values.
     * @param map the slices of the pie chart
     */
    public PieChart(Map<Color, Double> map) {
        super();
        this.isDoubleBuffered();

        this.slices = new HashMap<Color, Double>();
        for(HashMap.Entry<Color, Double> slice : map.entrySet()) {
            this.slices.put(slice.getKey(), slice.getValue());
        }
    }

    /**
     * Initialises the object.
     */
    public PieChart() {
        super();
        this.isDoubleBuffered();
        slices = new HashMap<Color, Double>();
    }


    /**
     * Add a slice to the pie.
     * @param slice the slice
     */
    public void addSlice(HashMap.Entry<Color, Double> slice) {
        slices.put(slice.getKey(), slice.getValue());
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
        totalSize = 0.0D;
        for(Double value : slices.values()) {
            totalSize += value;
        }
    }

    public void paint(Graphics g) {
        if(totalSize == null) {
            finaliseSize();
        }

        final Rectangle area = getBounds();

        g.fillOval(area.x - 5, area.y - 5, (area.width)/4 + 10, (area.height)/4 + 10);

        int curValue = 0;
        for(HashMap.Entry<Color, Double> entry : slices.entrySet()){
            int startAngle = (int) (curValue * 360 / totalSize);
            int arcAngle = (int) (entry.getValue() * 360 / totalSize);

            g.setColor(entry.getKey());
            g.fillArc(area.x,area.y,area.width/slices.size(),area.height/slices.size(), startAngle, arcAngle);

            curValue += entry.getValue();
        };
    }
}