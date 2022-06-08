package src.timer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseAction implements MouseMotionListener {
    AppTimer appTimer;
    public MouseAction(AppTimer appTimer1){
        appTimer = appTimer1;
    }
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        appTimer.reset();
        appTimer.start();
    }
}
