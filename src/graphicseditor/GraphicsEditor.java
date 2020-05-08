package graphicseditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.script.*;
import javax.swing.*;

public class GraphicsEditor extends JFrame {

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("js");

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        new GraphicsEditor();
    }

    public GraphicsEditor() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton lineButton = new JButton("Line");
        JButton rectangleButton = new JButton("Rectangle");

        JPanel TopPanel = new JPanel();
        TopPanel.setBackground(Color.WHITE);
        TopPanel.setLayout(new FlowLayout(5));
        TopPanel.add(lineButton);
        TopPanel.add(rectangleButton);
        this.add(TopPanel, BorderLayout.NORTH);
        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        lineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        rectangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private class PaintSurface extends JComponent {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        ArrayList<Shape> shapes = new ArrayList<Shape>();
        Point startDrag, endDrag;

        public PaintSurface() {
            this.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                    Object res = shapes.add(r);
                    startDrag = null;
                    endDrag = null;
                    repaint();
                }
            });

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    endDrag = new Point(e.getX(), e.getY());
                    repaint();
                }
            });
        }

        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));

            for (Shape s : shapes) {
                g2.draw(s);
            }

            if (startDrag != null && endDrag != null) {
                Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                g2.draw(r);
            }
        }

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }

    }
}
