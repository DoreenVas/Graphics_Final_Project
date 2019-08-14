package View;
import javax.swing.*;

public class Instructions {

    protected void instructions(){
        JFrame frame = new JFrame();
        frame.setSize(800, 600);

        //Create Image and set in the given frame
        Main.CreateImage.createImage("resources/pics/instructions.PNG", frame);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Instructions");
        frame.setResizable(false);
        frame.validate();
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
