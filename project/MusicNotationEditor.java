// MusicNotationEditor

// wholeNoteButton = createButton("Whole Note", "project/Image/whole_note.png");
// halfNoteButton = createButton("Half Note", "project/Image/half_note_icon.png");
// quarterNoteButton = createButton("Quarter Note", "project/Image/quarter_note_icon.png");
// eighthNoteButton = createButton("Eighth Note", "project/Image/eighth_note_icon.png");
// sixteenthNoteButton = createButton("Sixteenth Note", "project/Image/sixteenth_note_icon.png");
// bassClefButton = createButton("Bass Clef", "project/Image/bass_clef_icon.png");
// trebleClefButton = createButton("Treble Clef", "project/Image/treble_clef_icon.png");

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MusicNotationEditor extends JFrame {
    private StaffPanel staffPanel;
    private JButton wholeNoteButton;
    private JButton halfNoteButton;
    private JButton quarterNoteButton;
    private JButton bassClefButton;
    private JButton trebleClefButton;
    private JButton playButton; // New play button

    public MusicNotationEditor() {
        setTitle("Standard Musical Staff");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
     
    }

    private void initComponents() {
        staffPanel = new StaffPanel();



        wholeNoteButton = new JButton("Whole Note");
        wholeNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffPanel.setSelectedNoteType("Whole Note");
            }
        });

        halfNoteButton = new JButton("Half Note");
        halfNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffPanel.setSelectedNoteType("Half Note");
            }
        });

        quarterNoteButton = new JButton("Quarter Note");
        quarterNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffPanel.setSelectedNoteType("Quarter Note");
            }
        });

        bassClefButton = new JButton("Bass Clef");
        bassClefButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffPanel.setSelectedNoteType("Bass Clef");
            }
        });

        trebleClefButton = new JButton("Treble Clef");
        trebleClefButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                staffPanel.setSelectedNoteType("Treble Clef");
            }
        });

        playButton = new JButton("Play"); // Create a play button
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMelody(); // Call the method to play the melody
            }
        });
        

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(wholeNoteButton);
        buttonPanel.add(halfNoteButton);
        buttonPanel.add(quarterNoteButton);
        buttonPanel.add(bassClefButton);
        buttonPanel.add(trebleClefButton);
        buttonPanel.add(playButton); // Add the play button

        getContentPane().setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(staffPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        // getContentPane().add(staffPanel, BorderLayout.CENTER);
    }

    private void playMelody() {
        ArrayList<String> notes = staffPanel.getNotes(); // Get the notes from StaffPanel
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            for (String note : notes) {
                int midiNote = convertToMidi(note);
                if (midiNote != -1) {
                    channels[0].noteOn(midiNote, 100); // Play the note
                    Thread.sleep(1000); // Increase sleep duration to 1 second
                    channels[0].noteOff(midiNote); // Stop the note
                }
            }
            synth.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    

    private int convertToMidi(String note) {
        // This is a simple mapping, you might need a more comprehensive one
        switch (note) {
            case "C":
                return 60;
            case "D":
                return 62;
            case "E":
                return 64;
            case "F":
                return 65;
            case "G":
                return 67;
            case "A":
                return 69;
            case "B":
                return 71;
            default:
                return -1; // Invalid note
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusicNotationEditor::new);
    }
}







