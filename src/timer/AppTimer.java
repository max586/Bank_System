package src.timer;
import src.AuthenticationAndRegistration.AuthenticationScreen;
import src.PreScreen;
import src.Screen;
import src.mainFrame.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
public class AppTimer {
        JLabel timeLabel;
        public int initMinutes = 2;
        public int initSeconds = 0;
        public int initElapsedTime = 60000*initMinutes;
        public int minutes = initMinutes;
        public int seconds = initSeconds;
        public int elapsedTime = initElapsedTime;
        public Timer timer;
        public PreScreen frame;

        public AppTimer(JLabel timeLabel1, PreScreen mainFrame){
            frame = mainFrame;
            timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(!frame.getjFrame().isDisplayable()){stop();}
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


