package model;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ChargeurFichiers
{
    public static void chargerGenerique(String nomFichier, ChargerFichiers chargeur)
    {
        try
        {
            System.out.println("Tentative de chargement de : " + nomFichier);
            InputStream is = Main.class.getClassLoader().getResourceAsStream(nomFichier);

            if (is == null)
            {
                System.out.println("Échec de la lecture. Lecture directe du fichier...");
                chargeur.chargerDepuisFichier();
            }
            else
            {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)))
                {
                    chargeur.chargerDepuisBuffer(br);
                }
            }

        }
        catch (IOException e)
        {
            System.err.println("ERREUR Impossible de lire le fichier : " + nomFichier);
            System.err.println("Détail : " + e.getMessage());
        }
    }

}
