package game;

import supporters.difficultyEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.Vector;

import static supporters.difficultyEnum.easy;

public class BoardFrame extends JFrame implements ActionListener {
    Board board;
    int sizeOfBoard = -1;
    int winner = 5;
    String x = "X";
    String o = "O";
    private static final long serialVersionUID = 1L;

    JButton exitButton = new JButton("Exit");
    JButton moveButton = new JButton("Move");

    JButton changeButton = new JButton("Change");

    JLabel strategyLabel = new JLabel("Strategy:");
    JLabel levelLabel = new JLabel("Dificculty Level:");


    Vector<String> loadedStratedyList = new Vector<String>();
    Vector<JButton> buttonVector = new Vector<JButton>();

    difficultyEnum difficultyLevel = easy;
    String strategy;

    JPanel panel = new JPanel();
    JPanel panelBoard = new JPanel();

    JComboBox<String> strategyComboBox;
    JComboBox<String> levelComboBox;

    Loader loader;
    String pathName;
    int moveIndex = -1;

    Vector<String> stringButtonVector;
    private Constructor<?> gameConstructor;

    public BoardFrame(int size, Vector<String> loadedStratedyListVector, String pathNameTmp, String strategyName) {

        pathName = pathNameTmp;
        System.out.println("Name = " + strategyName);
        loader = new Loader(pathNameTmp,strategyName);
        loadedStratedyList = loadedStratedyListVector;
        strategyComboBox = new JComboBox<String>(loadedStratedyList);
        levelComboBox = new JComboBox(difficultyEnum.values());
        sizeOfBoard = size;

        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, panelBoard);
        setContentPane(splitPanel);

        moveButton.addActionListener(this);
        exitButton.addActionListener(this);
        changeButton.addActionListener(this);
        strategyComboBox.addActionListener(this);
        levelComboBox.addActionListener(this);


        panel.add(strategyLabel);
        panel.add(strategyComboBox);

        panel.add(levelLabel);
        panel.add(levelComboBox);

        panel.add(moveButton);
        panel.add(changeButton);
        panel.add(exitButton);


        for(int i = 0; i < size;i++ ) {
            for(int j =0; j < size; j++) {
                buttonVector.addElement(new JButton(" "));
            }
        }

        board = new Board(sizeOfBoard);

        panelBoard.setLayout(new GridLayout(sizeOfBoard, sizeOfBoard));

        for(int i = 0; i < size;i++ ) {
            for(int j = 0; j < size; j++) {
                buttonVector.get(j*size+i).addActionListener(this);
                panelBoard.add(buttonVector.get(j*size+i));
            }
        }

        splitPanel.setResizeWeight(0.5);
        splitPanel.setOneTouchExpandable(true);
        splitPanel.setContinuousLayout(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600,650);
        this.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == changeButton) {
            strategy = strategyComboBox.getSelectedItem().toString();
            loader = new Loader(pathName, strategy);
        }

        for (int i = 0; i < (sizeOfBoard * sizeOfBoard); i++) {
            if (source == buttonVector.get(i)) {
                moveIndex=i;
            }
        }

        if(source == moveButton) {
            buttonVector.get(moveIndex).setText(x);
            board.move(moveIndex, x);
            //checkForWin();
            difficultyLevel = (difficultyEnum) levelComboBox.getSelectedItem();

            int oponentMoveIndex =  loader.opponentMove(board.getBoard(),sizeOfBoard,difficultyLevel);
            board.move(oponentMoveIndex, o);
            System.out.println(oponentMoveIndex);
            buttonVector.get(oponentMoveIndex).setText(o);

            checkForWin();

        }

        if(source == exitButton) {
            System.exit(0);
            dispose();
        }
    }

    public void checkForWin() {

    }

}
