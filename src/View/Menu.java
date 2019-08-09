package View;

import Scene.Sounds;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    public static void menu_window(){
        JFrame frame = new JFrame();
        frame.setSize(800, 600);

        //Create Image and set in the given frame
        Main.CreateImage.createImage("resources/pics/temple.jpg", frame);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game");
        frame.setResizable(false);

        //setting button with icon
        ImageIcon icon = new ImageIcon("resources/pics/start.png");
        Image img = icon.getImage() ;
        Image newimg = img.getScaledInstance( 150, 50,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon(newimg);
        JButton button = new JButton(icon);
        button.setBounds(325, 275, 150, 50);


        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Game game = new Game();
                game.game_window();
            }
        });

        frame.add(button);
        Sounds.makeLoopSound("resources/sounds/intro.wav");
        frame.validate();
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
