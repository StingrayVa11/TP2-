package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauDepot;
import com.atoudeft.vue.PanneauPrincipal;
import com.atoudeft.vue.PanneauRetrait;
import com.atoudeft.vue.PanneauTransfert;

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

        if ("DEPOT".equals(action)) {
            afficherPanneauDepot();
        } else if ("RETRAIT".equals(action)) {
            afficherPanneauRetrait();
        } else if ("TRANSFER".equals(action)) {
            afficherPanneauTransfert();
        } else {
            client.envoyer(action); // Commande par défaut
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

    private void afficherPanneauRetrait() {
        // Créer une instance de PanneauRetrait
        PanneauRetrait panneauRetrait = new PanneauRetrait();

        // Ajouter les écouteurs pour les boutons Ok et Annuler
        panneauRetrait.addEcouteurs(e -> {
            // Action pour le bouton Ok
            String montant = panneauRetrait.getMontant();
            if (!montant.isEmpty()) {
                client.envoyer("RETRAIT " + montant); // Envoi au serveur
                panneauPrincipal.afficherSolde("Cliquer sur un compte pour avoir le nouveau solde mis a jour"); // Affiche un état temporaire
            }
            // Fermer la fenêtre pop-up
            SwingUtilities.getWindowAncestor(panneauRetrait).dispose();
        }, e -> {
            // Action pour le bouton Annuler
            SwingUtilities.getWindowAncestor(panneauRetrait).dispose();
        });

        // Créer un JDialog pour afficher PanneauRetrait en pop-up
        JDialog dialog = new JDialog();
        dialog.setTitle("Retrait");
        dialog.setModal(true);  // Rendre la fenêtre modale
        dialog.setContentPane(panneauRetrait);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);  // Centrer par rapport au panneau principal
        dialog.setVisible(true);  // Afficher la fenêtre
    }

    private void afficherPanneauTransfert() {
        // Créer une instance de PanneauTransfert
        PanneauTransfert panneauTransfert = new PanneauTransfert();

        // Ajouter les écouteurs pour les boutons Ok et Annuler
        panneauTransfert.addEcouteurs(e -> {
            // Action pour le bouton Ok
            String montant = panneauTransfert.getMontant();
            String numeroCompte = panneauTransfert.getNumeroCompte();
            if (!montant.isEmpty() && !numeroCompte.isEmpty()) {
                client.envoyer("TRANSFER " + montant + " " + numeroCompte); // Envoi au serveur
                panneauPrincipal.afficherSolde("Cliquer sur un compte pour avoir le nouveau solde mis a jour"); // Affiche un état temporaire
            }
            // Fermer la fenêtre pop-up
            SwingUtilities.getWindowAncestor(panneauTransfert).dispose();
        }, e -> {
            // Action pour le bouton Annuler
            SwingUtilities.getWindowAncestor(panneauTransfert).dispose();
        });

        // Créer un JDialog pour afficher PanneauTransfert en pop-up
        JDialog dialog = new JDialog();
        dialog.setTitle("Transférer");
        dialog.setModal(true);  // Rendre la fenêtre modale
        dialog.setContentPane(panneauTransfert);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);  // Centrer par rapport au panneau principal
        dialog.setVisible(true);  // Afficher la fenêtre
    }


}
