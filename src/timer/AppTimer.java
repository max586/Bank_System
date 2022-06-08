package src.timer;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.Screen;
import src.mainFrame.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import src.mainPanel.MainPanel;
public class AppTimer {
        JLabel timeLabel;
        private int initMinutes = 2;
        private int initSeconds = 0;
        private int initElapsedTime = 60000*initMinutes;
        private int minutes = initMinutes;
        private int seconds = initSeconds;
        private int elapsedTime = initElapsedTime;
        private Timer timer;
        private MainFrame frame;

        public AppTimer(JLabel timeLabel1,MainFrame mainFrame){
            frame = mainFrame;
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    elapsedTime=elapsedTime-1000;
                    if(elapsedTime==0) {
                        stop();
                        frame.getjFrame().dispose();
                        new AuthenticationScreen(null,null,new Screen()).CreateScreen();

                        //nawrotka do panelu logowania
                    }
                    minutes = (elapsedTime/60000) % 60;
                    seconds = (elapsedTime/1000) % 60;
                    timeLabel.setText(String.valueOf(minutes)+":"+String.format("%02d",seconds));
                    if(elapsedTime<=10000){
                        timeLabel.setForeground(Color.red);
                    }
                }
            });
            timeLabel = timeLabel1;
            timeLabel.setText(initMinutes +":"+String.format("%02d",seconds));
        }

        public void start() {
            timer.start();
        }

        public void stop() {
            timer.stop();
        }

        public void reset() {
            timer.stop();
            elapsedTime = initElapsedTime;
            seconds = initSeconds;
            minutes = initMinutes;
            timeLabel.setText(initMinutes +":"+String.format("%02d",seconds));
        }
    }


