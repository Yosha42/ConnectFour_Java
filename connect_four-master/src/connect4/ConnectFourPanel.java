/*
 * Copyright (C) 2015 Thomas Kercehval, Josh Murphy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package connect4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
/**
 *
 * @author Thomas
 */
class ConnectFourPanel extends JPanel{
    
    private final Connect4 game;
    private int columnIndex;
    private final KeyListener listener;
    private final JPanel top;
    private final JPanel bottom;
    private final JPanel center;
    private final JPanel redWinPane;
    private final JPanel blackWinPane;
    private final JPanel tiePane;
    private boolean gameOver;
    private ChipComponentCursor chip;
    private int currentTurn;
    private final ChipComponent[][] chipBoard;
    
    ConnectFourPanel() {
        setSize(1000, 700);
        setLayout(new BorderLayout());
        
        chipBoard = new ChipComponent[6][7];
        game = new Connect4();
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[0].length; j++) {
                ChipComponent chipper = 
                        new ChipComponent(game.board[i][j], 0, 0, 100);
                chipper.setBounds(0, 0, 100, 100);
                chipBoard[i][j] = chipper;
            }
        }
        listener = new KeybListener();
        top = createTopPanel();
        center = createCenterPanel();
        bottom = createBottomPanel();
        redWinPane = createWinPane("RED PLAYER WINS!");
        blackWinPane = createWinPane("BLACK PLAYER WINS!");
        tiePane = createWinPane("TIE GAME, YOU SUCK!");
        gameOver = false;

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        this.addKeyListener(listener);
        this.setFocusable(true);
        
        repaint();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(700, 100));
        this.chip = new ChipComponentCursor(1, 0, 0, 100);
        topPanel.setLayout(null);
        this.chip.setBounds(100*columnIndex, 0, chip.size, chip.size);
        topPanel.add(this.chip);
        topPanel.repaint();
        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(700, 700));
        centerPanel.setBackground(Color.YELLOW);
        centerPanel.setLayout(new GridLayout(6,7));
        for (int i = 0; i < game.board.length; i++) {
            for (int j = 0; j < game.board[0].length; j++) {
                centerPanel.add(chipBoard[i][j]);
            }
        }
        centerPanel.repaint();
        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(700, 40));
        
        JButton buttonReset = createResetButton();
        JButton buttonExit = createExitButton();
        bottomPanel.setLayout(new GridLayout(0,2));
        bottomPanel.add(buttonReset);
        bottomPanel.add(buttonExit);
        
        bottomPanel.setFocusable(false);
        return bottomPanel;
    }

    private JButton createResetButton() {
        JButton reset = new JButton("Reset Game");
        reset.addActionListener(new ResetListener());
        reset.setFocusable(false);
        return reset;
    }

    private JButton createExitButton() {
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ExitListener());
        return exit;
    }

    private JPanel createWinPane(String winState) {
        JPanel winPanel = new JPanel();
        if (winState.equals("RED PLAYER WINS!")) {
            winPanel.setBackground(Color.RED);
        } else if (winState.startsWith("BLACK PLAYER WINS!")) {
            winPanel.setBackground(Color.BLUE);
        } else {
            winPanel.setBackground(Color.GRAY);
        }
        winPanel.setPreferredSize(new Dimension(700, 100));
        JLabel labz = new JLabel(winState);
        labz.setFont(new Font("Verdana",1,56));
        winPanel.add(labz);
        return winPanel;
    }
    
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            game.resetBoard();
            gameOver = false;
            for (int i = 0; i < game.board.length; i++) {
                for (int j = 0; j < game.board[0].length; j++) {
                    chipBoard[i][j].color = game.board[i][j];
                }
            }
            remove(blackWinPane);
            remove(redWinPane);
            remove(tiePane);
            add(top, BorderLayout.NORTH);
            game.gameWinBlack = false;
            game.gameWinRed = false;
            game.tieGame = false;
            repaint();
        }   
    }
    
    class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }   
    }
    
    class KeybListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent event) {
            String key = KeyStroke.getKeyStrokeForEvent(event).toString();
            key = key.replace("pressed ", "");
            if (key.equals("LEFT")) {
                columnIndex--;
                if (columnIndex < 0) {
                    columnIndex = 6;
                }
                while (game.chipsInX[columnIndex] > 5) {
                    columnIndex--;
                    if (columnIndex < 0) {
                        columnIndex = 6;
                    }
                }
            } else if (key.equals("RIGHT")) {
                columnIndex++;
                columnIndex = columnIndex % game.board[0].length;
                while (game.chipsInX[columnIndex] > 5) {
                    columnIndex++;
                    columnIndex = columnIndex % (game.board[0].length - 1);
                }
            } else if (key.equals("SPACE") && !gameOver) {
                if (game.isRedTurn == true) {
                    game.redTurn(columnIndex);
                } else {
                    game.blackTurn(columnIndex);
                }
            }
            if (columnIndex < 0) {
                columnIndex = 6;
            }
            columnIndex = columnIndex % game.board[0].length;
            System.out.println(columnIndex);
            if (!(game.gameWinRed || game.gameWinBlack || game.tieGame)) {
                while (game.chipsInX[columnIndex] > 5) {
                    columnIndex++;
                    columnIndex = columnIndex % game.chipsInX.length;
                }
            } else {
                gameOverM();    
            }
            int chipPos = columnIndex;
            chip.setBounds(100*chipPos, 0, chip.size, chip.size);
            if (game.isRedTurn == true) {
                currentTurn = 1;
            } else {
                currentTurn = 2;
            }
            chip.color = currentTurn;
            
            for (int i = 0; i < game.board.length; i++) {
                for (int j = 0; j < game.board[0].length; j++) {
                    chipBoard[i][j].color = game.board[i][j];
                }
            }
            
            repaint();
            if (game.gameWinRed || game.gameWinBlack || game.tieGame) {
                gameOverM();
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}

        private void gameOverM() {
            gameOver = true;
            remove(top);
            if (game.gameWinRed) {
                add(redWinPane, BorderLayout.NORTH);
            } else if (game.gameWinBlack) {
                add(blackWinPane, BorderLayout.NORTH);
            } else {
                add(tiePane, BorderLayout.NORTH);
            }
            repaint();
            revalidate();
        }
    }
}
