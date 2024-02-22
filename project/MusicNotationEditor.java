import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicNotationEditor extends JFrame {
    private JPanel staffPanel;
    private JButton wholeNoteButton, halfNoteButton, quarterNoteButton;
    private Point mousePt;

    public MusicNotationEditor() {
        setTitle("Music Notation Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        // Initialize staff panel
        staffPanel = new StaffPanel();
        staffPanel.setPreferredSize(new Dimension(800, 200));
        staffPanel.setBackground(Color.WHITE);
        staffPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
            }
        });
        staffPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mousePt.x;
                int dy = e.getY() - mousePt.y;
                Component component = (Component) e.getSource();
                component.setLocation(component.getX() + dx, component.getY() + dy);
            }
        });

        
        // Initialize note buttons with icons and names
        wholeNoteButton = new JButton("Whole Note");
        wholeNoteButton.setIcon(new ImageIcon("/Users/shanhe/Downloads/160/Image/whole_note.png")); // You need to replace "whole_note_icon.png" with your actual image file path
        wholeNoteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        wholeNoteButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        halfNoteButton = new JButton("Half Note");
        halfNoteButton.setIcon(new ImageIcon("half_note_icon.png")); // You need to replace "half_note_icon.png" with your actual image file path
        halfNoteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        halfNoteButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        quarterNoteButton = new JButton("Quarter Note");
        quarterNoteButton.setIcon(new ImageIcon("quarter_note_icon.png")); // You need to replace "quarter_note_icon.png" with your actual image file path
        quarterNoteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        quarterNoteButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Layout setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(wholeNoteButton);
        buttonPanel.add(halfNoteButton);
        buttonPanel.add(quarterNoteButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(staffPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicNotationEditor());
    }

    private class StaffPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw musical staff
            int y = getHeight() / 2;
            g.drawLine(0, y, getWidth(), y);
            g.drawLine(0, y - 10, getWidth(), y - 10);
            g.drawLine(0, y - 20, getWidth(), y - 20);
            g.drawLine(0, y + 10, getWidth(), y + 10);
            g.drawLine(0, y + 20, getWidth(), y + 20);
        }
    }
}

