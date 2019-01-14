package main.java;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import org.testng.annotations.Test;

public class LogFile {
    FileOutputStream fos;
    {
        try {
            //Instancie un FileOutputStream qui permettra de créer/écrire dans un fichier du nom de logEpiSaveurs.txt
            //Le deuxième argument est un booleen servant à préciser si l'on souhaite écire à la suite dans le fichier ou bien tout remplacer
            fos = new FileOutputStream(".\\logEpiSaveurs\\logEpiSaveurs.txt", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public LogFile(String function, String error){
        //Ecriture dans le logFile comprenant date, fonction et l'erreur
        try {
            Date date = new Date();
            fos.write(("\n\n" + date +" "+function+" Error : " + error).getBytes());
            fos.flush();
            fos.close();
        }catch (Exception e){
            System.err.println("\n\nERROR WRITING THE LOGFILE : "+e);
        }
    }

}

