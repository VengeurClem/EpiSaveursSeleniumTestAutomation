package main.java;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {
    public Screenshot(WebDriver driver, String function) {
        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy HH-mm-ss";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);

        //Un screenshot du driver est pris et sera placé dans le dossier de logEpiSaveurs, l'image sera nommé avec
        //la date, l'heure et la fonction dans laquelle a eu lieu l'erreur
        try {
            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(
                    ".\\logEpiSaveurs"+"\\"+formattedDate+"  "+function+".png"));
        } catch (Exception e) {
            new LogFile("Screenshot", "SCREENSHOT ERROR : "+e.toString());
        }
    }
}
