/**
 * @author Victor K
 * @version 1.00
 * La classe ClientTest est une classe de test pour la classe Client.
 * Elle utilise l'interface Tools pour centraliser les méthodes de test.
 */
package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.Tools;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest implements Tools {
    /**
     * Vérifie que la méthode setRaisonSociale lance une ExceptionEntity si la chaîne de caractère passée en paramètre est nulle ou vide.
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n", "\r"})
    void testSetRaisonSocialeInvalide(String raisonSociale) {
        assertThrows(ExceptionEntity.class, () -> new Client().setRaisonSociale(raisonSociale));
    }

    /**
     * Vérifie que la méthode setRaisonSociale ne lance pas d'exception si la chaîne de caractères passée en paramètre est valide.
     */
    @ParameterizedTest
    @ValueSource(strings = {"Société 1", "Client"})
    void testSetRaisonSocialeValide(String raisonSociale) {
        assertDoesNotThrow(() -> new Client().setRaisonSociale(raisonSociale));
    }

    /**
     * Vérifie que la méthode setAdresseMail ne lance pas d'exception si la chaîne de caractères passée en paramètre est valide.
     */
    @ParameterizedTest
    @ValueSource(strings = {"x@gmail.com", "victor.krupka@sfr.fr"})
    void testAdresseMailValide(String adresseMail) throws ExceptionEntity {
        assertDoesNotThrow(() -> new Client().setAdresseMail(adresseMail));
    }

    /**
     * Vérifie que la méthode setAdresseMail lance une ExceptionEntity si la chaîne de caractère passée en paramètre est invalide.
     */
    @ParameterizedTest
    @ValueSource(strings = {"@gmail.com", "victor.krupka@"})
    void testAdresseMailInvalide(String adresseMail) {
        assertThrows(ExceptionEntity.class, () -> new Client().setAdresseMail(adresseMail));
    }

    /**
     * Vérifie que la méthode setNombreEmployes lance une ExceptionEntity si le nombre d'employés est invalide (négatif ou nul).
     */
    @ParameterizedTest
    @ValueSource(ints = {-10, 0})
    void testNombreEmployesInvalide(int nombreEmployes) {
        assertThrows(ExceptionEntity.class, () -> new Client().setNombreEmployes(nombreEmployes));
    }

    /**
     * Vérifie que la méthode setNombreEmployes ne lance pas d'exception si le nombre d'employés est valide (supérieur à 0).
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void testNombreEmployesValide(int nombreEmployes) {
        assertDoesNotThrow(() -> new Client().setNombreEmployes(nombreEmployes));
    }
}