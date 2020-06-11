package gui;

import factory.Factory;

import javax.swing.*;

public class gui {
    private Factory factory;
    JFrame jf;
    public gui(){
        factory=Factory.getInstance();
        jf=new JFrame();
        jf.setSize(1300,700);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
        jf.setVisible(true);
    }

}
