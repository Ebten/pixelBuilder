package build;
import javax.swing.*;
import java.awt.*;
class jfm{
            static Runtime runtime = Runtime.getRuntime();
            static JFrame jf = new JFrame("Pixel Builder!");
            static JLayeredPane jl = new JLayeredPane();
            static int refx = 0, refy = 0;
            static int dx = 800, dy = 800;
            static player p;
            public static void main(String[] arguments){
                        jf.setSize(dx,dy);
                        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        jf.setContentPane(jl);
                        jf.setBackground(Color.BLACK);
                        p = new player();
                        jf.add(p);
                        jf.setVisible(true);
            }
}