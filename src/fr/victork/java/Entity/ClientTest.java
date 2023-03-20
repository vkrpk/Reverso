package fr.victork.java.Entity;

import fr.victork.java.Exception.ExceptionEntity;
import fr.victork.java.Tools.Tools;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest implements Tools {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n", "\r"})
    void testSetRaisonSocialeInvalide(String raisonSociale) {
        assertThrows(ExceptionEntity.class, () -> new Client().setRaisonSociale(raisonSociale));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Société 1", "Client"})
    void testSetRaisonSocialeValide(String raisonSociale) {
        assertDoesNotThrow(() -> new Client().setRaisonSociale(raisonSociale));
    }

    @ParameterizedTest
    @ValueSource(strings = {"x@gmail.com", "victor.krupka@sfr.fr"})
    void testAdresseMailValide(String adresseMail) throws ExceptionEntity {
        assertDoesNotThrow(() -> new Client().setAdresseMail(adresseMail));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@gmail.com", "victor.krupka@"})
    void testAdresseMailInvalide(String adresseMail) {
        assertThrows(ExceptionEntity.class, () -> new Client().setAdresseMail(adresseMail));
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 0})
    void testNombreEmployesInvalide(int nombreEmployes) {
        assertThrows(ExceptionEntity.class, () -> new Client().setNombreEmployes(nombreEmployes));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10})
    void testNombreEmployesValide(int nombreEmployes) {
        assertDoesNotThrow(() -> new Client().setNombreEmployes(nombreEmployes));
    }
}