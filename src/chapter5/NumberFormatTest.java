package chapter5;

import javax.swing.*;
import java.awt.*;

/**
 * Created by v11424 on 25/09/2015.
 */
public class NumberFormatTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame= new NumberFormatFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
