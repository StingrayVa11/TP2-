package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauTransfert extends JPanel {
    private JTextField champMontant;
    private JTextField champNumeroCompte;
    private JButton btnOk, btnAnnuler;

    public PanneauTransfert() {
        // Initialisation des composants
        JLabel lblMontant = new JLabel("Montant à transférer:");
        JLabel lblNumeroCompte = new JLabel("Numéro du compte destinataire:");
        champMontant = new JTextField(10);
        champNumeroCompte = new JTextField(10);
        btnOk = new JButton("Ok");
        btnAnnuler = new JButton("Annuler");

        // Disposition du panneau
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Ajout des composants
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lblMontant, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(champMontant, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(lblNumeroCompte, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(champNumeroCompte, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel panelBoutons = new JPanel();
        panelBoutons.add(btnOk);
        panelBoutons.add(btnAnnuler);

        this.add(panelBoutons, gbc);
    }

    // Getters pour interagir avec les composants
    public String getMontant() {
        return champMontant.getText();
    }

    public String getNumeroCompte() {
        return champNumeroCompte.getText();
    }

    public void addEcouteurs(ActionListener ecouteurOk, ActionListener ecouteurAnnuler) {
        btnOk.addActionListener(ecouteurOk);
        btnAnnuler.addActionListener(ecouteurAnnuler);
    }
}
