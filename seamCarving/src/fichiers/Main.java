package fichiers;

import ui.MainFrame;

import javax.swing.*;

/**
 * @author Jofrey Luc
 * @author Quentin Sonrel
 *         Classe principale (instancie et lance l'interface)
 */
public class Main extends JFrame {

    public Main() {
        super("SeamCarving");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new MainFrame());

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
