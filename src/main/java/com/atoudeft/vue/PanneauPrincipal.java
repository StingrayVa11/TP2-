package com.atoudeft.vue;

import com.atoudeft.client.Client;
import com.atoudeft.controleur.EcouteurConnexion;
import com.atoudeft.controleur.EcouteurListeComptes;
import com.atoudeft.controleur.EcouteurOperationsCompte;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> historiqueOperations = new ArrayList<>();




    public PanneauPrincipal(Client client) {
        this.client = client;

        panneauConnexion = new PanneauConnexion();
        panneauConnexion.setEcouteur(new EcouteurConnexion(client,panneauConnexion));

        panneauOperationsCompte = new PanneauOperationsCompte();
        /**
         * 2.1 créer un écouteur de type EcouteurOperationsCompte dans le panneau
         * principal et le fournir au panneau des opérations. Cette écouteur détient une
         * référence vers le client (fourni au constructeur);
         */
        panneauOperationsCompte.setEcouteur(new EcouteurOperationsCompte(client, this));


        panneauCompteClient = new JPanel();
        panneauCompteClient.setLayout(new BorderLayout());
        panneauCompteClient.setBackground(Color.WHITE);
        panneauOperationsCompte.setBackground(Color.WHITE);

        numerosComptes = new DefaultListModel<>();

        jlNumerosComptes = new JList<>(numerosComptes);
        jlNumerosComptes.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        jlNumerosComptes.setBorder(BorderFactory.createTitledBorder("Comptes bancaires"));
        jlNumerosComptes.setPreferredSize(new Dimension(250,500));






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

    public void enregistrerOperation(String operation) {
        historiqueOperations.add(operation);
        System.out.println("Opération enregistrée : " + operation); // Debug
    }

    public void afficherHistorique() {
        // Créer une chaîne de texte pour l'historique
        StringBuilder historiqueTexte = new StringBuilder();
        for (String operation : historiqueOperations) {
            historiqueTexte.append(operation).append("\n");
        }

        // Créer une zone de texte en lecture seule pour afficher l'historique
        JTextArea zoneHistorique = new JTextArea(historiqueTexte.toString());
        zoneHistorique.setEditable(false); // Lecture seule
        zoneHistorique.setLineWrap(true); // Retour à la ligne automatique
        zoneHistorique.setWrapStyleWord(true); // Retour à la ligne sur les mots entiers
        zoneHistorique.setFont(new Font("Arial", Font.PLAIN, 14)); // Style uniforme

        // Créer un JScrollPane pour permettre le défilement si nécessaire
        JScrollPane defileur = new JScrollPane(zoneHistorique);
        defileur.setPreferredSize(new Dimension(300, 200)); // Taille de la boîte

        // Afficher la boîte de dialogue
        JOptionPane.showMessageDialog(this, defileur,
                "Historique du compte", JOptionPane.INFORMATION_MESSAGE);
    }




}
