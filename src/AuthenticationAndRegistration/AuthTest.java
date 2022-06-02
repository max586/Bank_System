package src.AuthenticationAndRegistration;
import src.Screen;
import javax.swing.*;

import src.User;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JPanel;


public class AuthTest {
    private JFrame jFrame;
    private JPanel AuthPanel;
    private JButton PROFILButton;
    private JButton BLIKButton;
    private JButton KREDYTYButton;
    private JButton wylogujButton;
    private JList list1;
    private JButton zróbPrzelewButton;
    private JButton nextButton;
    private JButton prevButton;
    String []options = {"one","two"};

    public User user;
    public Screen prev_screen;
    public Screen next_screen;

    public AuthTest(){
            //(User user, Screen prev_screen, Screen next_screen)throws IOException {

        //this.user = user;
       // this.prev_screen = prev_screen;
        //this.next_screen = next_screen;

        jFrame = new JFrame();
        jFrame.setSize(1080,720);
        jFrame.setTitle("MainScreen");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        list1.setListData(options);
        list1.getSelectionModel().addListSelectionListener(e ->
        {
            int index = list1.getSelectedIndex();
            if(index!=-1) {
                if (options[index] == "one") {

                    JFrame okienko = new JFrame();
                    okienko.setVisible(true);

                }else if(options[index] == "two")
                {

                }
            }
        });

        BLIKButton.addActionListener(e->
                                     {

                                     });
        KREDYTYButton.addActionListener(e->
        {

        });
        PROFILButton.addActionListener(e->
        {

        });
        wylogujButton.addActionListener(e->
        {
            jFrame.dispose();
        });
        nextButton.addActionListener(e->
        {

        });
        prevButton.addActionListener(e->
        {

            jFrame.dispose();
            if(prev_screen!=null){
                prev_screen.CreateScreen();
            }

        });






        jFrame.setContentPane(AuthPanel);
        jFrame.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        new AuthTest();
    }


}

