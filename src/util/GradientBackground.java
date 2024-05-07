package util;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * The GradientBackground class represents a JPanel with a gradient background.
 */
public class GradientBackground extends JPanel {
    private final Color[] colors;
    private final float[] fractions;

    /**
     * Constructs a new GradientBackground with the specified gradient colors and fractions.
     *
     * @param colors     An array of colors representing the gradient.
     * @param fractions  An array of floats representing the distribution of colors in the gradient.
     */
    public GradientBackground(Color[] colors, float[] fractions) {
        this.colors = colors;
        this.fractions = fractions;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Define the gradient
        Point2D start = new Point2D.Float(0, 0);
        Point2D end = new Point2D.Float(0, getHeight());
        LinearGradientPaint gradient = new LinearGradientPaint(start, end, fractions, colors);

        // Fill the background with the gradient
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Example usage
            Color[] colors = {new Color(255, 227, 194), Color.WHITE, Color.WHITE, new Color(202, 240, 206)};
            float[] fractions = {0.0f, 0.4f, 0.8f, 1.0f};
            JFrame frame = new JFrame("Gradient Background");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new GradientBackground(colors, fractions));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
