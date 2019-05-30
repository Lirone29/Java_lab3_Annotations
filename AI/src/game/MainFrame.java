package game;

import supporters.difficultyEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Vector;

public class MainFrame extends JFrame implements ActionListener, ListSelectionListener {

    private static final long serialVersionUID = 1L;

    int sizeOfBoard = 5;
    int winner = 5;
    BoardFrame board;
    String classesPathString;
    String pathName;
    Paths classesPath;

    difficultyEnum dif;

    JFormattedTextField sizeArea;
    File file;
    Path dir;

    JPanel panel = new JPanel();
    JButton playButton = new JButton("Play");
    JButton catalogButton = new JButton("Choose Catalog");


    JLabel sizeLabel = new JLabel("Size of Board");
    JLabel strategyLabel = new JLabel("Strategy:");
    JLabel levelLabel = new JLabel("Dificculty Level:");
    JFileChooser fc = new JFileChooser();

    DefaultComboBoxModel<String> comboBoxStrategyModel = new DefaultComboBoxModel<>();

    Vector<String> strategyVector = new Vector<String>();

    JComboBox<String> levelComboBox;
    JComboBox<String> strategyComboBox;

    Loader loader;

    public MainFrame() {

        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        sizeArea = new JFormattedTextField(sizeOfBoard);
        sizeArea.setEditable(true);
        levelComboBox =new JComboBox(difficultyEnum.values());
        strategyComboBox = new JComboBox<String>( comboBoxStrategyModel);

        setContentPane(panel);

        playButton.addActionListener(this);
        catalogButton.addActionListener(this);
        strategyComboBox.addActionListener(this);
        levelComboBox.addActionListener(this);

        panel.add(sizeLabel);
        panel.add(sizeArea);
        panel.add(catalogButton);
        panel.add(strategyLabel);
        panel.add(strategyComboBox);

        panel.add(levelLabel);
        panel.add(levelComboBox);
        panel.add(playButton);

        sizeLabel.setBounds(10, 10, 60, 20);
        sizeArea.setLocation(1, 2);

        setLayout(new FlowLayout(FlowLayout.CENTER));

        setSize(600, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new MainFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == catalogButton) {

            int returnVal = fc.showOpenDialog(MainFrame.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();

                classesPathString = file.getAbsolutePath();
                pathName = file.getPath();
                dir = file.toPath();

                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    //Wybór wszystkich podkatalogów danego katalogu
                    for (Path filePath : stream) {
                        comboBoxStrategyModel.addElement(filePath.getFileName().toString());
                        strategyVector.addElement(filePath.getFileName().toString());
                    }

                } catch (IOException | DirectoryIteratorException x) {

                }

            }
        }
        if (source == playButton) {
            sizeOfBoard = (int) sizeArea.getValue();
            dif = (difficultyEnum)levelComboBox.getSelectedItem();
           // System.out.println(strategyComboBox.getSelectedItem().toString());
            board = new BoardFrame(sizeOfBoard,strategyVector,pathName,strategyComboBox.getSelectedItem().toString());
        }


    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub

    }

}