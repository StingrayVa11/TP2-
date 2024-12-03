package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.programmes.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;

    public EcouteurOperationsCompte(Client client) {
        this.client = client;
    }

    /**
     * Tristan G
     *  2.2 traiter le clic sur le bouton dans la méthode actionPerformed() de l’écouteur;
     *  Chaque bouton dans PanneauOperaitonsCompte ce font attribuer une ActionCommand qui
     *  ensuite seront envoyer au serveur au travers la commande envoyer de client.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.envoyer(e.getActionCommand());
    }
}
