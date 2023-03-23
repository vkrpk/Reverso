package fr.victork.java.DAO;

import fr.victork.java.DAO.mysql.MySQLDatabaseConnection;
import fr.victork.java.Exception.ExceptionDAO;
import fr.victork.java.Exception.ExceptionEntity;

import java.sql.Connection;
import java.util.List;

public interface DAO<T> {
    //--------------------- CONSTANTS ------------------------------------------
    //--------------------- STATIC VARIABLES -----------------------------------
    //--------------------- INSTANCE VARIABLES ---------------------------------
    //--------------------- CONSTRUCTORS ---------------------------------------
    //--------------------- STATIC METHODS -------------------------------------
    //--------------------- INSTANCE METHODS -----------------------------------
    //--------------------- ABSTRACT METHODS -----------------------------------
    void save(T obj) throws ExceptionEntity, ExceptionDAO;

    T find(Integer id) throws ExceptionEntity, ExceptionDAO;

    void delete(Integer id) throws ExceptionDAO;

    List<T> findAll() throws ExceptionEntity, ExceptionDAO;
    //--------------------- STATIC - GETTERS - SETTERS -------------------------
    //--------------------- GETTERS - SETTERS ----------------------------------
    //--------------------- TO STRING METHOD------------------------------------
}
