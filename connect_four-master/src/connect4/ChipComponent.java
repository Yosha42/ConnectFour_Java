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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Thomas
 */
public class ChipComponent extends JPanel {
    int color;
    int x;
    int y;
    int size;
    private final Ellipse2D.Double circle;
    
    public ChipComponent(int color, int x, int y, int size) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
        this.circle = new Ellipse2D.Double(x+10, y+10, size-10, size-10);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(100,100));
        Graphics2D g2 = (Graphics2D) g;
        if (color == 1) {
            g2.setColor(Color.RED);
        } else if (color == 2) {
            g2.setColor(Color.BLACK);
        } else if (color == 0) {
            g2.setColor(Color.GRAY);
        }
        setBackground(Color.YELLOW);
        g2.fill(circle);
        g2.draw(circle);
        
        
    }
    
    public void moveCircle(int dx) {
        x += size;
        repaint();
    }
}
