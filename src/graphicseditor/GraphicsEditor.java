package graphicseditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

public class GraphicsEditor extends JFrame {

    private static final long serialVersionUID = 1L;
    private String currentAction = "rectangle";

    public static void main(String[] args) {

        new GraphicsEditor();
    }

    public  GraphicsEditor() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton lineButton = new JButton("Line");
        JButton rectangleButton = new JButton("Rectangle");
        JPanel TopPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        TopPanel.setBackground(Color.LIGHT_GRAY);
        TopPanel.setPreferredSize(new Dimension(400, 100));
        TopPanel.add(lineButton);
        TopPanel.add(rectangleButton);

        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.setPreferredSize(new Dimension(100, 400));

        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setPreferredSize(new Dimension(800, 100));

        rightPanel.setBackground(Color.LIGHT_GRAY);
        rightPanel.setPreferredSize(new Dimension(100, 400));

        this.add(TopPanel, BorderLayout.NORTH);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(rightPanel, BorderLayout.EAST);

        this.add(new PaintSurface(), BorderLayout.CENTER);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        lineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = "line";
            }
        });

        rectangleButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                currentAction = "rectangle";
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
                }

                public void mouseReleased(MouseEvent e) {
                    Shape r;
                    switch (currentAction){
                        case "rectangle":
                            r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                            shapes.add(r);
                            break;
                        case "line":
                            r = makeLine(startDrag.x, startDrag.y, e.getX(), e.getY());
                            shapes.add(r);
                            break;
                    };
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

        
        @Override
        public void paint(Graphics g) {
            getContentPane().setBackground(new Color(255, 255, 255));
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));

            for (Shape s : shapes) {
                g2.draw(s);
            }

            if (startDrag != null && endDrag != null) {
                Shape r;
                switch (currentAction){
                    case "rectangle":
                        r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                        g2.draw(r);
                        break;
                    case "line":
                        r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                        g2.draw(r);
                        break;
                }
                
            }
        }

        private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
            return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
        }
        
        private Line2D.Float makeLine(int x1, int y1, int x2, int y2){
            return new Line2D.Float(x1, y1, x2, y2);
        }

    }
}
