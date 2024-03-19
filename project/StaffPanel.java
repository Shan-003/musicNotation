
// project/Image/


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StaffPanel extends JPanel {
    private Map<String, List<Rectangle>> noteBoundsMap;
    private Map<String, BufferedImage> noteImageMap;

    private Point dragPoint;
    private Point lastMousePosition;
    private String selectedNoteType;
    private int numStaves; 

    private List<String> notes; // New list to store notes

    public StaffPanel() {
        noteBoundsMap = new HashMap<>();
        noteImageMap = new HashMap<>();
        numStaves = 1;
        notes = new ArrayList<>(); // Initialize the list

        try {
            // Load images from files
            BufferedImage wholeNoteImage = ImageIO.read(new File("project/Image/whole_note.png"));
            BufferedImage halfNoteImage = ImageIO.read(new File("project/Image/half_note.png"));
            BufferedImage quarterNoteImage = ImageIO.read(new File("project/Image/quarter_note.png"));
            BufferedImage bassClefImage = ImageIO.read(new File("project/Image/bass_clef.png"));
            BufferedImage trebleClefImage = ImageIO.read(new File("project/Image/treble_clef.png"));

            // Store images in the map
            noteImageMap.put("Whole Note", wholeNoteImage);
            noteImageMap.put("Half Note", halfNoteImage);
            noteImageMap.put("Quarter Note", quarterNoteImage);
            noteImageMap.put("Bass Clef", bassClefImage);
            noteImageMap.put("Treble Clef", trebleClefImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boolean noteRemoved = false;
                for (List<Rectangle> boundsList : noteBoundsMap.values()) {
                    for (Rectangle bounds : boundsList) {
                        if (bounds.contains(evt.getPoint())) {
                            boundsList.remove(bounds);
                            noteRemoved = true;
                            repaint();
                            break;
                        }
                    }
                    if (noteRemoved) {
                        break;
                    }
                }
                if (!noteRemoved && selectedNoteType != null) {
                    List<Rectangle> noteBounds = noteBoundsMap.computeIfAbsent(selectedNoteType, k -> new ArrayList<>());
                    noteBounds.add(new Rectangle(evt.getX() - noteImageMap.get(selectedNoteType).getWidth() / 2,
                            evt.getY() - noteImageMap.get(selectedNoteType).getHeight() / 2,
                            noteImageMap.get(selectedNoteType).getWidth(),
                            noteImageMap.get(selectedNoteType).getHeight()));
                    notes.add(selectedNoteType); // Add the selected note type to the list
                    repaint();
                }
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                for (List<Rectangle> boundsList : noteBoundsMap.values()) {
                    for (Rectangle bounds : boundsList) {
                        if (bounds.contains(evt.getPoint())) {
                            dragPoint = evt.getPoint();
                            lastMousePosition = evt.getPoint();
                            return;
                        }
                    }
                }
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dragPoint = null;
                lastMousePosition = null;
            }
        });

        addMouseMotionListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                if (dragPoint != null && lastMousePosition != null) {
                    int dx = evt.getX() - lastMousePosition.x;
                    int dy = evt.getY() - lastMousePosition.y;
                    for (List<Rectangle> boundsList : noteBoundsMap.values()) {
                        for (Rectangle bounds : boundsList) {
                            if (bounds.contains(dragPoint)) {
                                bounds.translate(dx, dy);
                                lastMousePosition = evt.getPoint();
                                repaint();
                                return;
                            }
                        }
                    }
                }
            }
        });

        JButton addStaffButton = new JButton("Add Staff");
        addStaffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                numStaves++;
                repaint();
            }
        });

        add(addStaffButton);
    }

    public void setSelectedNoteType(String noteType) {
        this.selectedNoteType = noteType;
    }
    

    // Inside StaffPanel class
    public ArrayList<String> getNotes() {
        ArrayList<String> actualNotes = new ArrayList<>();
        for (String note : notes) {
            // Map the note name to its actual pitch on the staff
            String actualNote = mapNoteToPitch(note);
            actualNotes.add(actualNote);
        }
        return actualNotes;
    }
    private String mapNoteToPitch(String note) {
        // Perform mapping based on the note name
        switch (note) {
            case "Whole Note":
                return "C"; // For demonstration, mapping to C note
            case "Half Note":
                return "D"; // Mapping to D note
            case "Quarter Note":
                return "E"; // Mapping to E note
            // Add more cases for other note types if needed
            default:
                return ""; // Return empty string for unknown notes
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int lineSpacing = 10;

        for (int j = 0; j < numStaves; j++) {
            int startY = (getHeight() / (numStaves + 1)) * (j + 1) - 2 * lineSpacing;
            int endY = (getHeight() / (numStaves + 1)) * (j + 1) + 2 * lineSpacing;
    
            for (int i = startY; i <= endY; i += lineSpacing) {
                g.drawLine(50, i, getWidth() - 50, i); // Draw the staff lines
            }
        }

        // Draw notes and clefs
        for (Map.Entry<String, List<Rectangle>> entry : noteBoundsMap.entrySet()) {
            String noteType = entry.getKey();
            BufferedImage noteImage = noteImageMap.get(noteType);
            List<Rectangle> boundsList = entry.getValue();
            for (Rectangle bounds : boundsList) {
                g.drawImage(noteImage, bounds.x, bounds.y, null);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}

