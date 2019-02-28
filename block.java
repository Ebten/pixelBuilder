package build;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
class block extends JComponent{
            int x, y;
            int six = 10, siy = 10;
            Color c;
            boolean filling = false;
            Rectangle2D.Float r = new Rectangle2D.Float();
            block(int inpx, int inpy, Color color, boolean filler){
                        setSize(jfm.dx, jfm.dy);
                        x = inpx;
                        y = inpy;
                        c = color;
                        filling = filler;
            }
            void changeSize(int sispx, int sispy){
                        six = sispx;
                        siy = sispy;
            }
            public void paintComponent(Graphics co){
                        Graphics2D g = (Graphics2D) co;
                        r.setFrame(x+jfm.refx,y+jfm.refy,six,siy);
                        g.setColor(c);
                        g.fill(r);
            }
}