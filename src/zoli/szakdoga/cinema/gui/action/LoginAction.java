package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.entity.Felhasznalo;

/**
 *
 * @author Zoli
 */
public class LoginAction implements ActionListener {

    private JFrame parent;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(parent, "login pop-up");
    }
    
}
