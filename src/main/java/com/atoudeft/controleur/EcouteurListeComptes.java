package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauPrincipal;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeComptes extends MouseAdapter {

    private Client client;
    private PanneauPrincipal panneauPrincipal;

    public EcouteurListeComptes(Client client, PanneauPrincipal panneauPrincipal) {
        this.client = client;
        this.panneauPrincipal = panneauPrincipal;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // Vérifier si c'est un double-clic
        if (evt.getClickCount() == 2) {
            // On récupère l'élément sélectionné dans la liste
            JList<String> list = (JList<String>) evt.getSource();
            String selectedAccount = list.getSelectedValue();

            // Si un compte est sélectionné
            if (selectedAccount != null) {
                //envoyer commande SELECT au serveur pour ce compte

                if (selectedAccount.contains("CHEQUE")) {
                    selectedAccount = "cheque";
                } else if (selectedAccount.contains("EPARGNE")) {
                    selectedAccount = "epargne";
                }
                System.out.println("[DEBUG] selecting: "+selectedAccount);
                client.envoyer("SELECT " + selectedAccount);

                String solde = "1000";//client.getSolde(); //recupere le solde dans le compte
                System.out.println("[DEBUG] solde:"+solde);
                //mettre a jour l'affichage du solde dans le panneau des operations
                panneauPrincipal.afficherSolde(solde);
            }
        }
    }

}
