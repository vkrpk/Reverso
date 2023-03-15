/**
 * @author Victor K
 * @version 1.00
 * La classe WriteFile est responsable de lire et d'écrire des données de
 * Clients et Prospects
 * dans un fichier.
 */
package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.FormatterDate;

import javax.swing.*;
import java.io.*;
import java.time.DateTimeException;

/**
 * La classe WriteFile est responsable de lire et d'écrire des données de
 * Clients et Prospects
 * dans un fichier.
 */
public class WriteFile {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------

    /**
     * Méthode principale pour tester les fonctionnalités de la classe
     * WriteFile.
     *
     * @param args Les arguments de la ligne de commande.
     * @throws ExceptionEntity, IOException Exceptions pouvant être levées
     *                          lors de l'exécution.
     */
    public static void main(String[] args) throws ExceptionEntity, IOException {
        WriteFile.litUnFichierEtRempliLesCollections(
                new File("sauvegarde.txt"));
        for (Client client : CollectionClients.getCollection()) {
            System.out.println(client.getRaisonSociale());
        }
    }

    /**
     * Récupère les données des collections de Clients et Prospects et les
     * insère dans un fichier.
     *
     * @param file Le fichier dans lequel insérer les données.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    public static void recupereCollectionEtLesInsereDansUnFichier(File file)
            throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (Client client : CollectionClients.getCollection()) {
                bufferedWriter.write("Client" + ";");
                ecritLesProprietesDUneSociete(bufferedWriter, (Societe) client);
                bufferedWriter.write(
                        String.valueOf(client.getChiffreAffaires()) + ";");
                bufferedWriter.write(
                        String.valueOf(client.getNombreEmployes()) + ";");
                bufferedWriter.write(
                        String.valueOf(client.getIdentifiant()) + ";");
                bufferedWriter.newLine();
            }


            for (Prospect prospect : CollectionProspects.getCollection()) {
                bufferedWriter.write("Prospect" + ";");
                ecritLesProprietesDUneSociete(bufferedWriter,
                        (Societe) prospect);
                bufferedWriter.write(FormatterDate.convertiEtFormatDateEnChaine(
                        prospect.getDateProsprection()) + ";");
                bufferedWriter.write(prospect.getProspectInteresse() + ";");
                bufferedWriter.write(
                        String.valueOf(prospect.getIdentifiant()) + ";");
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Écrit les propriétés d'une société dans un BufferedWriter.
     *
     * @param bufferedWriter Le BufferedWriter dans lequel écrire les
     *                       propriétés.
     * @param societe        La société dont les propriétés doivent être
     *                       écrites.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private static void ecritLesProprietesDUneSociete(
            BufferedWriter bufferedWriter, Societe societe) throws IOException {
        bufferedWriter.write(societe.getRaisonSociale() + ";");
        bufferedWriter.write(societe.getNumeroDeRue() + ";");
        bufferedWriter.write(societe.getNomDeRue() + ";");
        bufferedWriter.write(societe.getCodePostal() + ";");
        bufferedWriter.write(societe.getVille() + ";");
        bufferedWriter.write(societe.getTelephone() + ";");
        bufferedWriter.write(societe.getAdresseMail() + ";");
        bufferedWriter.write(societe.getCommentaires() + ";");
    }

    /**
     * Lit un fichier et remplit les collections de Clients et Prospects avec
     * les données lues.
     * Cette méthode parcourt le fichier ligne par ligne, extrait les
     * informations de chaque
     * ligne et crée des objets Client et Prospect en conséquence. Ensuite,
     * elle ajoute ces
     * objets aux collections appropriées.
     *
     * @param file Le fichier à lire contenant les données des Clients et
     *             Prospects.
     */
    public static void litUnFichierEtRempliLesCollections(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String tab[] = line.split(";");
                if (tab[0].equals("Client")) {
                    Client client =
                            new Client(tab[1], tab[2], tab[3], tab[4], tab[5],
                                    tab[6], tab[7], tab[8],
                                    Double.parseDouble(tab[9]),
                                    Integer.parseInt(tab[10]));
                    CollectionClients.getCollection().add(client);
                }
                if (tab[0].equals("Prospect")) {
                    System.out.println(tab[10]);
                    Prospect prospect =
                            new Prospect(tab[1], tab[2], tab[3], tab[4], tab[5],
                                    tab[6], tab[7], tab[8],
                                    FormatterDate.convertiEtFormatDateEnLocalDate(
                                            tab[9]), tab[10]);
                    CollectionProspects.getCollection().add(prospect);
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (DateTimeException dte) {
            JOptionPane.showMessageDialog(null,
                    "La date doit être dans le format suivant : dd/MM/yyyy",
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException nft) {
            JOptionPane.showMessageDialog(null,
                    "La valeur saisie doit être uniquement composé de " +
                            "chiffres", "Erreur de saisie",
                    JOptionPane.ERROR_MESSAGE);
        } catch (ExceptionEntity ee) {
            JOptionPane.showMessageDialog(null, ee.getMessage(),
                    "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }
    //--------------------- ABSTRACT METHODS -----------------------------------
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
