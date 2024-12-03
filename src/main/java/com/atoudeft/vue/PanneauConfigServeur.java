package com.atoudeft.vue;

import com.atoudeft.client.Client;
import com.atoudeft.client.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel implements ActionListener{
    private JTextField txtAdrServeur, txtNumPort;
    private JButton bouton1;
    private JButton bouton2;
    private Client client;
    private JFrame popup;

    public PanneauConfigServeur(String adr, int port, Client client, JFrame popup) {
        //à compléter
        this.client = client;
        this.popup = popup;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // le popup et un boxlayout vertical
        JPanel boite1 = new JPanel(); // créer 3 groupes horizontaux pour les éléments
        JPanel boite2 = new JPanel();
        JPanel boite3 = new JPanel();
        boite1.setLayout(new FlowLayout(FlowLayout.CENTER)); // les groupes doivent être centrés
        boite2.setLayout(new FlowLayout(FlowLayout.CENTER));
        boite3.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel label1 = new JLabel("Adresse IP : "); // créer les 2 textes
        JLabel label2 = new JLabel("Port : ");
        txtAdrServeur = new JTextField(adr); // on crée le textField avec l'adresse recommendée
        txtAdrServeur.setPreferredSize(new Dimension(120,20));
        txtNumPort = new JTextField(String.valueOf(port)); // on crée le textField avec le port recommendé
        txtNumPort.setPreferredSize(new Dimension(120,20));
        bouton1 = new JButton("Ok"); // créer les 2 boutons
        bouton2 = new JButton("Annuler");
        boite1.add(label1); // ajouter les éléments aux groupes, puis les groupes au Panel
        boite1.add(txtAdrServeur);
        boite2.add(label2);
        boite2.add(txtNumPort);
        boite3.add(bouton1);
        boite3.add(bouton2);
        add(boite1);
        add(boite2);
        add(boite3);

        bouton1.addActionListener(this); // initialiser les ActionListener des boutons
        bouton2.addActionListener(this);
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) { // gestion des événements des boutons
        if (e.getSource() == bouton1) {
            boolean reussi = true;
            try { // vérification que le port entré est bel et bien un chiffre entier
                int numPort = Integer.parseInt(txtNumPort.getText());
            } catch(Exception excp) {
                reussi = false;
            }
            if (reussi) { // si les valeurs sont valides, envoyer l'IP et le port au client
                client.setAdrServeur(txtAdrServeur.getText());
                client.setPortServeur(Integer.parseInt(txtNumPort.getText()));
                popup.setVisible(false); // fermer le popup
            }

        } else if (e.getSource() == bouton2) { // bouton annuler
            popup.setVisible(false); // fermer le popup
        }
    }
}
