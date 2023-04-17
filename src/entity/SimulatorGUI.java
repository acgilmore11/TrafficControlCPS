package entity;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SimulatorGUI {

    private JFrame frame;
    private JComboBox<String> algTypeComboBox;
    private JButton startSimulationButton;
    private JLabel algTypeLabel;
    private JTextArea outputTextArea;

    public SimulatorGUI() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        algTypeComboBox = new JComboBox<>(new String[]{
                "TLCConstantTime",
                "TLCConstantTimeVehicleThreshold",
                "TLCConstantTimeVehicleThresholdModified",
                "TLCVarTime",
                "TLCVarTimeVehicleThreshold"
        });
        algTypeLabel = new JLabel("Select Algorithm Type:");
        startSimulationButton = new JButton("Start Simulation");
        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(algTypeLabel);
        controlPanel.add(algTypeComboBox);
        controlPanel.add(startSimulationButton);
        frame.add(controlPanel, BorderLayout.NORTH);

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void startSimulation() {
        int algType = algTypeComboBox.getSelectedIndex();
        Simulator sim = new Simulator(algType);

        // Disable the Start Simulation button to prevent multiple simulation runs
        startSimulationButton.setEnabled(false);

        // Run the simulation in a separate thread
        new Thread(() -> {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < 86400; i++) {
                try {
                    sim.run();
                } catch (Exception e) {
                    output.append("Error: ").append(e.getMessage()).append("\n");
                    e.printStackTrace();
                }
                if (i % 60 == 0) {
                    output.append("\n\n**********ROUND ").append(Global.round).append("**********\n\n");
                    output.append(sim.laneNS.toString()).append("\n");
                    output.append(sim.laneEW.toString()).append("\n");
                    output.append(sim.acc.toString()).append("\n");

                    // Update the output text area in the Event Dispatch Thread
                    final String currentOutput = output.toString();
                    SwingUtilities.invokeLater(() -> {
                        outputTextArea.setText(currentOutput);
                    });
                }
            }
            output.append(sim.acc.toString()).append("\n");

            // Update the output text area in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                outputTextArea.setText(output.toString());
                saveOutputToFile(output.toString());
                // Re-enable the Start Simulation button after the simulation is finished
                startSimulationButton.setEnabled(true);
            });
        }).start();
    }



    private void saveOutputToFile(String output) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimulatorGUI();
            }
        });
    }
}
