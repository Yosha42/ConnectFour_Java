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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class ConnectFourViewer {
   /**      Creates and displays the application frame.   */   
    public static void runGame() {
        JFrame frame = new JFrame("Connect Four! Give us an A+");
        String message = "Press space to drop piece\n"
                + "Press left arrow to move cursor piece left\n"
                + "Press right arrow to move cursor piece right";
        JOptionPane.showMessageDialog (null, message, "Instructions",
                                       JOptionPane.PLAIN_MESSAGE);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 1000);
        ConnectFourPanel cp = new ConnectFourPanel();
        frame.setResizable(false);
        frame.add(cp);
        frame.pack();
        frame.setVisible(true);
    }
}