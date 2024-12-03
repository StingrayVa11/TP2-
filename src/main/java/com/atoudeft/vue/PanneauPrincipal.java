package com.atoudeft.vue;

import com.atoudeft.client.Client;
import com.atoudeft.controleur.EcouteurConnexion;
import com.atoudeft.controleur.EcouteurListeComptes;
import com.atoudeft.controleur.EcouteurOperationsCompte;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2024-11-01
 */
public class PanneauPrincipal  extends JPanel {
    private Client client;
    private PanneauConnexion panneauConnexion;
    private JPanel panneauCompteClient;
    private PanneauOperationsCompte panneauOperationsCompte;

    private DefaultListModel<String> numerosComptes;
    private JList<String> jlNumerosComptes;
    private JDesktopPane bureau;
    //private JLabel soldeLabel = new JLabel("Solde : 0.00");


    public PanneauPrincipal(Client client) {
        this.client = client;

        panneauConnexion = new PanneauConnexion();
        panneauConnexion.setEcouteur(new EcouteurConnexion(client,panneauConnexion));

        panneauOperationsCompte = new PanneauOperationsCompte();
        /**
         * Tristan G
         * 2.1 créer un écouteur de type EcouteurOperationsCompte dans le panneau
         * principal et le fournir au panneau des opérations. Cette écouteur détient une
         * référence vers le client (fourni au constructeur);
         */
        panneauOperationsCompte.setEcouteur(new EcouteurOperationsCompte(client));


        panneauCompteClient = new JPanel();
        panneauCompteClient.setLayout(new BorderLayout());
        panneauCompteClient.setBackground(Color.WHITE);
        panneauOperationsCompte.setBackground(Color.WHITE);

        numerosComptes = new DefaultListModel<>();

        jlNumerosComptes = new JList<>(numerosComptes);
        jlNumerosComptes.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        jlNumerosComptes.setBorder(BorderFactory.createTitledBorder("Comptes bancaires"));
        jlNumerosComptes.setPreferredSize(new Dimension(250,500));

        //jlNumerosComptes = new JList<>();
        // exemple d'affichage du compte apres l'avoir selectionne (a finir)
        //jlNumerosComptes.setListData(new String[]{"Compte 1", "Compte 2", "Compte 3"});

        //enregistrer l'ecouteur de double-clic
        //jlNumerosComptes.addMouseListener(new EcouteurListeComptes(client, this));




        panneauCompteClient.add(panneauOperationsCompte, BorderLayout.NORTH);
        panneauCompteClient.add(jlNumerosComptes, BorderLayout.WEST);
        //Enregistrement de l'écouteur de souris:
        jlNumerosComptes.addMouseListener(new EcouteurListeComptes(client, this));

        this.setLayout(new BorderLayout());
        this.add(panneauConnexion, BorderLayout.NORTH);
        this.add(panneauCompteClient, BorderLayout.CENTER);
        panneauCompteClient.setVisible(false);
    }

    /**
     * Vide les éléments d'interface du panneauPrincipal.
     */
    public void vider() {
        this.numerosComptes.clear();
        this.bureau.removeAll();
    }
    public void cacherPanneauConnexion() {
        panneauConnexion.effacer();
        panneauConnexion.setVisible(false);
    }
    public void montrerPanneauConnexion() {
        panneauConnexion.setVisible(true);
    }
    public void cacherPanneauCompteClient() {
        panneauCompteClient.setVisible(false);
        this.numerosComptes.clear();
    }
    public void montrerPanneauCompteClient() {
        panneauCompteClient.setVisible(true);
    }
    /**
     * Affiche un numéro de compte dans la JList des comptes.
     * @param str Chaîne contenant le numéro de compte
     */
    public void ajouterCompte(String str) {
        numerosComptes.addElement(str);
    }

    /**
     * Met à jour l'affichage du solde dans le panneau des opérations.
     * @param solde Le solde à afficher
     */
    public void afficherSolde(String solde) {
        panneauOperationsCompte.afficherSolde(solde); // Mise à jour du label avec le solde
    }
}
