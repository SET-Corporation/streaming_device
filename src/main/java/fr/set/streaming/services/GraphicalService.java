package fr.set.streaming.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;

@Service
public class GraphicalService {

    public JFrame getJframe(Canvas canvas){
        JFrame f = new JFrame();
        f.setLocation(100,100);
        f.setSize(1000,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.add(getJpanel(canvas));
        return f;
    }

    public Canvas getCanvas(){
        Canvas c = new Canvas();
        c.setBackground(Color.BLACK);
        return c;
    }

    private JPanel getJpanel(Canvas canvas){
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(canvas);
        return p;
    }
}
