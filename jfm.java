package build;
import javax.swing.*;
import java.awt.*;
class jfm{
            static JFrame jf = new JFrame("Pixel Builder!");
            static JLayeredPane jl = new JLayeredPane();
            // The reference point - everything will move in the opposite
            // direction of the player
            static int refx = 0, refy = 0;
            static int dx = 800, dy = 800;
            static player p;
            public static void main(String[] arguments){
                        // Setting up the JFrame
                        jf.setSize(dx,dy);
                        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        // Adding our JLayeredPane as our content pane
                        jf.setContentPane(jl);
                        jf.setBackground(Color.BLACK);
                        // Adding in our player
                        p = new player();
                        jf.add(p);
                        jf.setVisible(true);
            }
}