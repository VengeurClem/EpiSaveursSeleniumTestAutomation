package main.java;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class ResponseTime {
    FileOutputStream fos;

    public ResponseTime(WebDriver driver, String function) {
        try {
            //Instancie un FileOutputStream qui permettra de créer/écrire dans un fichier du nom de ResponseTimeEpiSaveurs.txt
            //Le deuxième argument est un booleen servant à préciser si l'on souhaite écire à la suite dans le fichier ou bien tout remplacer
            fos = new FileOutputStream(".\\logEpiSaveurs\\ResponseTimeEpiSaveurs.txt",true);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long loadtime = (Long) ((JavascriptExecutor) driver).executeScript(
                //Execute le javascript permettant de récupérer les temps de réponses d'une requête.
                "return performance.timing.loadEventEnd - performance.timing.navigationStart;");

        try {
            //Ecriture dans le fichier de log avec date, fonction en cours et temps de réponse
            Date date = new Date();
            fos.write(("\n" + date + " " + function + " : " + loadtime + "ms").getBytes());
            fos.flush();
        } catch (Exception e) {
            System.err.println("\n\nERROR WRITING THE RESPONSETIME FILE IN navigationStart: " + e);
        }
    }
}