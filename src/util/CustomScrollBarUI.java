package util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * The CustomScrollBarUI class provides a custom UI for scroll bars.
 */
public class CustomScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        // Set the colors for the scroll bar thumb and track
        thumbColor = new Color(225, 129, 9); // Orange
        trackColor = new Color(240, 240, 240); // Light gray
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        // Create the appearance of the up or left button
        JButton button = super.createDecreaseButton(orientation);
        button.setBackground(new Color(180, 180, 180));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        // Create the appearance of the down or right button
        JButton button = super.createIncreaseButton(orientation);
        button.setBackground(new Color(180, 180, 180));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder());
        return button;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        // Paint the track of the scroll bar
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        // Paint the thumb of the scroll bar
        g.setColor(thumbColor);
        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
    }
}
