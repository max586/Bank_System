package src;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.sql.Statement;
import java.util.prefs.PreferenceChangeEvent;

public class Screen extends PreScreen {
    public User user;
    public Screen prev_screen;
    public Screen next_screen;
    public Screen(User user, Screen prev_screen, Screen next_screen){
        this.user = user;
        this.prev_screen = prev_screen;
        this.next_screen = next_screen;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public Screen(){
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void CreateScreen(){}
}
