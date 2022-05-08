import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainScreen {
    public static void CreateScreen(User user){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(frame, "That's all Folks!");
    }
}
