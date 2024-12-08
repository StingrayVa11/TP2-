package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauDepot;
import com.atoudeft.vue.PanneauPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;
    private PanneauPrincipal panneauPrincipal; // Référence pour accéder et changer les panneaux

    public EcouteurOperationsCompte(Client client, PanneauPrincipal panneauPrincipal) {
        this.client = client;
        this.panneauPrincipal = panneauPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        // Gérer le clic sur le bouton "Déposer"
        if ("DEPOT".equals(action)) {
            afficherPanneauDepot();
        } else {
            // Envoi de la commande par défaut au serveur
            client.envoyer(action);
        }
    }

    private void afficherPanneauDepot() {
        // Créer une instance de PanneauDepot
        PanneauDepot panneauDepot = new PanneauDepot();

        // Ajouter les écouteurs pour les boutons Ok et Annuler
        panneauDepot.addEcouteurs(e -> {
            // Action pour le bouton Ok
            String montant = panneauDepot.getMontant();
            if (!montant.isEmpty()) {
                client.envoyer("DEPOT " + montant); // Envoi au serveur
                panneauPrincipal.afficherSolde("Mise à jour en attente..."); // Affiche un état temporaire
            }
            // Fermer la fenêtre pop-up
            SwingUtilities.getWindowAncestor(panneauDepot).dispose();
        }, e -> {
            // Action pour le bouton Annuler
            SwingUtilities.getWindowAncestor(panneauDepot).dispose();
        });

        // Créer un JDialog pour afficher PanneauDepot en pop-up
        JDialog dialog = new JDialog();
        dialog.setTitle("Dépôt");
        dialog.setModal(true);  // Rendre la fenêtre modale
        dialog.setContentPane(panneauDepot);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);  // Centrer par rapport au panneau principal
        dialog.setVisible(true);  // Afficher la fenêtre
    }
}
