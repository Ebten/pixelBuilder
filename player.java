package build;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
class player extends JComponent implements Runnable, KeyListener, MouseListener, MouseMotionListener{
            Thread t;
            boolean hold = true;
            int threadTime = 100;
            
            int middlex = jfm.dx/2, middley = jfm.dy/2;
            int x = jfm.dx/2, y = jfm.dy/2;
            int sisp = 10;
            
            int r = 255, g = 255, b = 255;
            Color c = new Color(r,g,b);
            int colorAttSel = 0;
            String selColAtt;
            int colChaAm = 32;
            
            JLabel location = new JLabel();
            JLabel colorInfo = new JLabel();
            Font f = new Font("Verdana",Font.ITALIC,16);
            
            boolean moveUp = false, moveDown = false, moveLeft = false, moveRight = false, building = false;
            Rectangle2D.Float re = new Rectangle2D.Float();
            Rectangle2D.Float line1 = new Rectangle2D.Float();
            Rectangle2D.Float line2 = new Rectangle2D.Float();
            
            block fillBlock;
            boolean filling = false;
            boolean showLines = false;
            
            JLabel controls = new JLabel();
            JLabel lines = new JLabel();
            JLabel fill = new JLabel();
            JLabel reset = new JLabel();
            JLabel colors = new JLabel();
            boolean listControls = false;
            Font f2 = new Font("Verdana",Font.BOLD,12);
            
            player(){
                        setSize(jfm.dx, jfm.dy);
                        displaySetup();
                        controlsListSetup();
                        addKeyListener(this);
                        addMouseListener(this);
                        addMouseMotionListener(this);
                        setFocusable(true);
                        start();
            }
            void displaySetup(){
                        location.setSize(jfm.dx, jfm.dy);
                        location.setText("x: " + x + " | y: " + y);
                        location.setVerticalAlignment(JLabel.TOP);
                        location.setForeground(Color.WHITE);
                        location.setFont(f);
                        add(location);
                        colorInfo.setSize(jfm.dx, jfm.dy);
                        updateDisplayText();
                        colorInfo.setVerticalAlignment(JLabel.BOTTOM);
                        colorInfo.setHorizontalAlignment(JLabel.CENTER);
                        colorInfo.setLocation(0,-50);
                        colorInfo.setForeground(Color.WHITE);
                        colorInfo.setFont(f);
                        add(colorInfo);
            }
            void controlsListSetup(){
                        JLabelSetup(controls,20,"Controls: W (up), A (left), S (down), D (right)",Color.GREEN);
                        JLabelSetup(lines,40,"To display location lines: L",Color.BLUE);
                        JLabelSetup(fill,60,"To start filling an area, and to press again to stop filling: F",Color.YELLOW);
                        JLabelSetup(reset,80,"To erase everything: N",Color.RED);
                        JLabelSetup(colors,100,"Switch between r, g, and b and increase or decrease values of selected attribute: ARROW KEYS",Color.PINK);
            }
            void JLabelSetup(JLabel jla, int locy, String t, Color fgnd){
                        jla.setSize(jfm.dx,jfm.dy);
                        jla.setVerticalAlignment(JLabel.TOP);
                        jla.setFont(f2);
                        jla.setLocation(0,locy);
                        jla.setText(t);
                        jla.setForeground(fgnd);
                        add(jla);
            }
            void setControlListVisibility(boolean vis){
                        controls.setVisible(vis);
                        lines.setVisible(vis);
                        fill.setVisible(vis);
                        reset.setVisible(vis);
                        colors.setVisible(vis);
            }
            void updateDisplayText(){
                        selColAtt = "NULL";
                        if(colorAttSel == 0){
                                    selColAtt = "r";
                        }else if(colorAttSel == 1){
                                    selColAtt = "g";
                        }else if(colorAttSel == 2){
                                    selColAtt = "b";
                        }
                        colorInfo.setText("Color Attribute Selected: " + selColAtt + " | r: " + r + ", g: " + g + ", b: " + b);
                        location.setText("x: " + x + " | y: " + y + " | Press 'C' for controls");
                        updateColor();
            }
            void updateColor(){
                        c = new Color(r,g,b);
            }
            public void paintComponent(Graphics co){
                        Graphics2D g = (Graphics2D) co;
                        re.setFrame(middlex,middley,sisp,sisp);
                        if(showLines == true){
                                    line1.setFrame(middlex,0,sisp,jfm.dy);
                                    g.setColor(Color.GRAY);
                                    g.fill(line1);
                                    line2.setFrame(0,middley,jfm.dx,sisp);
                                    g.fill(line2);
                        }
                        g.setColor(c);
                        g.draw(re);
            }
            void start(){if(t == null){t = new Thread(this);t.start();}}
            public void run(){
                        while(hold == true){
                                    // Motion controls
                                    if(moveUp == true){
                                                jfm.refy+=sisp;
                                                y-=sisp;
                                    }
                                    if(moveDown == true){
                                                jfm.refy-=sisp;
                                                y+=sisp;
                                    }
                                    if(moveLeft == true){
                                                jfm.refx+=sisp;
                                                x-=sisp;
                                    }
                                    if(moveRight == true){
                                                jfm.refx-=sisp;
                                                x+=sisp;
                                    }
                                    // To build a standard block
                                    if(building == true){
                                                block newBlock = new block(x,y,c,false);
                                                add(newBlock, 0);
                                    }
                                    // If using a filling block
                                    if(filling == true){
                                                fillBlock.changeSize(x - fillBlock.x, y - fillBlock.y);
                                    }
                                    // Displaying the controls list
                                    if(listControls == true){
                                                setControlListVisibility(true);
                                    }else{
                                                setControlListVisibility(false);
                                    }
                                    // Updating texts on screen
                                    updateDisplayText();
                                    repaint();
                                    try{t.sleep(threadTime);}catch(Exception e){}
                        }
            }
            public void keyPressed(KeyEvent k){
                        // Motion controls
                        if(k.getKeyChar() == 'w'){
                                    moveUp = true;
                        }
                        if(k.getKeyChar() == 's'){
                                    moveDown = true;
                        }
                        if(k.getKeyChar() == 'a'){
                                    moveLeft = true;
                        }
                        if(k.getKeyChar() == 'd'){
                                    moveRight = true;
                        }
                        // To show grid lines intersecting at the player
                        if(k.getKeyChar() == 'l'){
                                    if(showLines == false){
                                                showLines= true;
                                    }else{
                                                showLines = false;
                                    }
                        }
                        // To display the controls list
                        if(k.getKeyChar() == 'c'){
                                    if(listControls == false){
                                                listControls = true;
                                    }else{
                                                listControls = false;
                                    }
                        }
                        // Deleting all items from the player to clear the screen
                        if(k.getKeyChar() == 'n'){
                                    removeAll();
                                    add(location);
                                    add(colorInfo);
                                    add(controls);
                                    add(lines);
                                    add(fill);
                                    add(reset);
                                    add(colors);
                        }
                        // Using the filling block
                        if(k.getKeyChar() == 'f'){
                                    if(filling == false){
                                                fillBlock = new block(x,y,c,true);
                                                add(fillBlock, -1);
                                                filling = true;
                                    }else{
                                                filling = false;
                                    }
                                    
                        }
                        // FOR WHEN THE WINDOW IS UNDECORATED, CAREFUL
                        if(k.getKeyChar() == KeyEvent.VK_ESCAPE){
                                    jfm.jf.removeAll();
                                    jfm.jf.setVisible(false);
                                    jfm.runtime.exit(0);
                        }
                        // Arrow switching between color attributes, going down
                        if(k.getKeyCode() == 37){
                                    if(colorAttSel == 0){
                                                colorAttSel = 2;
                                    }else{
                                                colorAttSel--;
                                    }
                        }
                        // Arrow switching between color attributes, going up
                        if(k.getKeyCode() == 39){
                                    if(colorAttSel == 2){
                                                colorAttSel = 0;
                                    }else{
                                                colorAttSel++;
                                    }
                        }
                        // Changing the value of the color attributes, going up
                        if(k.getKeyCode() == 38){
                                    if(colorAttSel == 0){
                                                if(r < 255){
                                                            r+=colChaAm;
                                                            if(r > 255){
                                                                        r = 255;
                                                            }
                                                }
                                    }else if(colorAttSel == 1){
                                                if(g < 255){
                                                            g+=colChaAm;
                                                            if(g > 255){
                                                                        g = 255;
                                                            }
                                                }
                                    }else if(colorAttSel == 2){
                                                if(b < 255){
                                                            b+=colChaAm;
                                                            if(b > 255){
                                                                        b = 255;
                                                            }
                                                }
                                    }
                        }
                        // Changing the value of the color attributes, going down
                        if(k.getKeyCode() == 40){
                                    if(colorAttSel == 0){
                                                if(r > 0){
                                                            if(r == 255){
                                                                        r-=(colChaAm-1);
                                                            }else{
                                                                        r-=colChaAm;
                                                            }
                                                            if(r < 0){
                                                                        r = 0;
                                                            }
                                                }
                                    }else if(colorAttSel == 1){
                                                if(g > 0){
                                                            if(g == 255){
                                                                        g-=(colChaAm-1);
                                                            }else{
                                                                        g-=colChaAm;
                                                            }
                                                            if(g < 0){
                                                                        g = 0;
                                                            }
                                                }
                                    }else if(colorAttSel == 2){
                                                if(b > 0){
                                                            if(b == 255){
                                                                        b-=(colChaAm-1);
                                                            }else{
                                                                        b-=colChaAm;
                                                            }
                                                            if(b < 0){
                                                                        b = 0;
                                                            }
                                                }
                                    }
                        }
            }
            public void keyReleased(KeyEvent k){
                        // Motion controls, stopping
                        if(k.getKeyChar() == 'w'){
                                    moveUp = false;
                        }
                        if(k.getKeyChar() == 's'){
                                    moveDown = false;
                        }
                        if(k.getKeyChar() == 'a'){
                                    moveLeft = false;
                        }
                        if(k.getKeyChar() == 'd'){
                                    moveRight = false;
                        }
            }
            public void keyTyped(KeyEvent k){}
            public void mousePressed(MouseEvent me){
                        // Making it so that the player makes a block
                        building = true;
            }
            public void mouseClicked(MouseEvent me){}
            public void mouseReleased(MouseEvent me){
                        // Making it so that the player stops making blocks
                        building = false;
            }
            public void mouseEntered(MouseEvent me){}
            public void mouseExited(MouseEvent me){}
            public void mouseMoved(MouseEvent me){}
            public void mouseDragged(MouseEvent me){}
}