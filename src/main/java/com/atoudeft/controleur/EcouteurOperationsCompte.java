package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.*;

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
        } else if ("FACTURE".equals(action)) {
            afficherPanneauFacture();
        } else if ("HIST".equals(action)) {
            panneauPrincipal.afficherHistorique();
        }else {
            client.envoyer(action); // Commande par défaut
        }
    }

    private void afficherPanneauDepot() {
        PanneauDepot panneauDepot = new PanneauDepot();

        panneauDepot.addEcouteurs(e -> {
            String montant = panneauDepot.getMontant();
            if (!montant.isEmpty()) {
                client.envoyer("DEPOT " + montant);
                panneauPrincipal.enregistrerOperation("Dépôt : " + montant + "$"); // Enregistre l'opération
                panneauPrincipal.afficherSolde("Double cliquer pour actualiser le solde du compte");
            }
            SwingUtilities.getWindowAncestor(panneauDepot).dispose();
        }, e -> {
            SwingUtilities.getWindowAncestor(panneauDepot).dispose();
        });

        JDialog dialog = new JDialog();
        dialog.setTitle("Dépôt");
        dialog.setModal(true);
        dialog.setContentPane(panneauDepot);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);
        dialog.setVisible(true);
    }


    private void afficherPanneauRetrait() {
        PanneauRetrait panneauRetrait = new PanneauRetrait();

        panneauRetrait.addEcouteurs(e -> {
            String montant = panneauRetrait.getMontant();
            if (!montant.isEmpty()) {
                client.envoyer("RETRAIT " + montant);
                panneauPrincipal.enregistrerOperation("Retrait : " + montant + "$"); // Enregistre l'opération
                panneauPrincipal.afficherSolde("Cliquer sur un compte pour avoir le nouveau solde mis a jour");
            }
            SwingUtilities.getWindowAncestor(panneauRetrait).dispose();
        }, e -> {
            SwingUtilities.getWindowAncestor(panneauRetrait).dispose();
        });

        JDialog dialog = new JDialog();
        dialog.setTitle("Retrait");
        dialog.setModal(true);
        dialog.setContentPane(panneauRetrait);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);
        dialog.setVisible(true);
    }


    private void afficherPanneauTransfert() {
        PanneauTransfert panneauTransfert = new PanneauTransfert();

        panneauTransfert.addEcouteurs(e -> {
            String montant = panneauTransfert.getMontant();
            String numeroCompte = panneauTransfert.getNumeroCompte();
            if (!montant.isEmpty() && !numeroCompte.isEmpty()) {
                client.envoyer("TRANSFER " + montant + " " + numeroCompte);
                panneauPrincipal.enregistrerOperation("Transfert " + numeroCompte + " : " + montant + "$"); // Enregistre l'opération
                panneauPrincipal.afficherSolde("Cliquer sur un compte pour avoir le nouveau solde mis a jour");
            }
            SwingUtilities.getWindowAncestor(panneauTransfert).dispose();
        }, e -> {
            SwingUtilities.getWindowAncestor(panneauTransfert).dispose();
        });

        JDialog dialog = new JDialog();
        dialog.setTitle("Transférer");
        dialog.setModal(true);
        dialog.setContentPane(panneauTransfert);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);
        dialog.setVisible(true);
    }


    private void afficherPanneauFacture() {
        // Créer une instance de PanneauFacture
        PanneauFacture panneauFacture = new PanneauFacture();

        // Ajouter les écouteurs pour les boutons Ok et Annuler
        panneauFacture.addEcouteurs(e -> {
            // Action pour le bouton Ok
            String montant = panneauFacture.getMontant();
            String numeroFacture = panneauFacture.getNumeroFacture();
            if (!montant.isEmpty() && !numeroFacture.isEmpty()) {
                // Envoi de la commande au serveur
                client.envoyer("FACTURE " + montant);

                // Enregistrer l'opération localement
                String operation = "Facture " + numeroFacture + " : " + montant + "$";
                panneauPrincipal.enregistrerOperation(operation); // Appelle une méthode pour ajouter à l'historique

                // Mettre à jour le solde dans l'interface (optionnel)
                panneauPrincipal.afficherSolde("Cliquer sur un compte pour avoir le nouveau solde mis a jour");
            }
            // Fermer la fenêtre pop-up
            SwingUtilities.getWindowAncestor(panneauFacture).dispose();
        }, e -> {
            // Action pour le bouton Annuler
            SwingUtilities.getWindowAncestor(panneauFacture).dispose();
        });

        // Créer un JDialog pour afficher PanneauFacture en pop-up
        JDialog dialog = new JDialog();
        dialog.setTitle("Facture");
        dialog.setModal(true);  // Rendre la fenêtre modale
        dialog.setContentPane(panneauFacture);
        dialog.pack();
        dialog.setLocationRelativeTo(panneauPrincipal);  // Centrer par rapport au panneau principal
        dialog.setVisible(true);  // Afficher la fenêtre
    }




}
