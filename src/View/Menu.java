package View;

import Scene.Sounds;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class Menu {

    public static void menu_window(String picPath, String labelText) throws IOException, SQLException {
        JFrame frame = new JFrame();
        ViewManager manager = ViewManager.getInstance();
        manager.setMenuFrame(frame);

        frame.setSize(800, 600);

        //Create Image and set in the given frame
        Main.CreateImage.createImage(picPath, frame);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game");
        frame.setResizable(false);

        //setting label
        JLabel label = new JLabel();
        label.setText(labelText);
        label.setBounds(125,100,550,50);
        label.setForeground(Color.BLACK);
        label.setFont(new java.awt.Font("Arial", Font.BOLD, 30));
        frame.add(label);

        //setting button with icon
        ImageIcon icon = new ImageIcon("resources/pics/start.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 150, 50,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon(newimg);
        JButton button = new JButton(icon);
        button.setBounds(325, 400, 150, 50);

        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    manager.startGame();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        frame.add(button);
        Sounds.makeLoopSound("resources/sounds/intro.wav");
        frame.validate();
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
