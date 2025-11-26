package model;

import model.particulier.Profil;

import java.io.BufferedReader;
import java.io.IOException;

public interface ChargerFichiers
{
    void chargerDepuisBuffer(BufferedReader br) throws IOException;
    void chargerDepuisFichier() throws IOException;
}
