package ui;

import fichiers.SeamCarving;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

/**
 * @author Jofrey Luc
 * @author Quentin Sonrel
 *         Classe pour l'interface graphique
 */
public class MainFrame extends JPanel {

    private JLabel jlChemin, jlSpinner;
    private JTextField jtfChemin;
    private JButton jbOpen, jbGo1, jbGo2;
    private JFileChooser jfc;
    private int retVal;
    private File f;
    private JSpinner js;
    private JCheckBox jcbLigne;
    private GridBagConstraints c;

    public MainFrame() {
        super(new GridBagLayout());

        c = new GridBagConstraints();

        jlChemin = new JLabel("Chemin image :");
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 0, 0, 0);
        add(jlChemin, c);

        jtfChemin = new JTextField();
        jtfChemin.setPreferredSize(new Dimension(300, 30));
        jtfChemin.setToolTipText("Chemin de l'image à traiter");
        c.gridx = 1;
        c.gridy = 0;
        add(jtfChemin, c);

        jbOpen = new JButton("Ouvrir");
        jbOpen.setToolTipText("Choisir un fichier");
        c.gridx = 2;
        c.gridy = 0;
        add(jbOpen, c);

        jcbLigne = new JCheckBox("Traitement en ligne");
        jcbLigne.setToolTipText("Traiter l'image verticalement");
        c.gridx = 0;
        c.gridy = 1;
        add(jcbLigne, c);

        jlSpinner = new JLabel("Qté à supprimer :");
        jlSpinner.setToolTipText("Quantité de pixels à supprimer");
        c.gridx = 0;
        c.gridy = 2;
        add(jlSpinner, c);

        js = new JSpinner();
        js.setEnabled(false);
        jlSpinner.setToolTipText("Quantité de pixels à supprimer");
        c.gridx = 1;
        c.gridy = 2;
        add(js, c);

        jbOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jfc = new JFileChooser(jtfChemin.getText());
                retVal = jfc.showOpenDialog((Component) e.getSource());

                if (retVal == JFileChooser.APPROVE_OPTION) {
                    f = jfc.getSelectedFile();
                    jtfChemin.setText(f.getAbsolutePath());

                    if (jcbLigne.isSelected()) {
                        js.setModel(new SpinnerNumberModel(1, 1, SeamCarving.getDims(f.getAbsolutePath())[1], 1));
                    } else {
                        js.setModel(new SpinnerNumberModel(1, 1, SeamCarving.getDims(f.getAbsolutePath())[0], 1));
                    }

                    js.setEnabled(true);
                }
            }
        });

        jbGo1 = new JButton("Go (méthode 1)");
        jbGo1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jcbLigne.isSelected()) {
                    SeamCarving.carve(f.getAbsolutePath(), (int) js.getValue(), f.getParent() + "/out", true);
                } else {
                    SeamCarving.carve(f.getAbsolutePath(), (int) js.getValue(), f.getParent() + "/out", false);
                }
            }
        });
        jbGo1.setToolTipText("Traiter l'image avec la méthode de la partie 1");
        c.gridx = 0;
        c.gridy = 3;
        add(jbGo1, c);

        jbGo2 = new JButton("Go (méthode 2)");
        jbGo2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jcbLigne.isSelected()) {
                    SeamCarving.carve2(f.getAbsolutePath(), (int) js.getValue(), f.getParent() + "/out", true);
                } else {
                    SeamCarving.carve2(f.getAbsolutePath(), (int) js.getValue(), f.getParent() + "/out", false);
                }
            }
        });
        jbGo2.setToolTipText("Traiter l'image avec la méthode de la partie 2");
        c.gridx = 1;
        c.gridy = 3;
        add(jbGo2, c);

        jcbLigne.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(jcbLigne.isSelected()) {
                    jbGo2.setEnabled(false);
                    jbGo2.setToolTipText("Traitement en ligne non implémenté pour la partie 2");
                }
                else {
                    jbGo2.setEnabled(true);
                    jbGo2.setToolTipText("Traiter l'image avec la méthode de la partie 2");
                }
            }
        });

        this.setPreferredSize(new Dimension(600, 250));
    }
}
