package main.java;

import com.neotys.selenium.proxies.NLWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.SkipException;

import static java.lang.System.exit;


public class NavigationEpiSaveurs {
    //WebDriver est l'objet que l'on va instancier en choisissant le driver correspondant au navigateur de notre choix.
    private WebDriver driver;
    //NLDRIVER pour Neoload Driver, va lui se baser sur le chromedriver pour réaliser ses transaction dans Neoload
    private NLWebDriver nlDriver;
    //tabs représente les onglets du navigateur lors de la navigation
    private ArrayList<String> tabs;

    public NavigationEpiSaveurs(String nav, String path) {
        switch(nav) {
            case "Firefox":
                //Instanciation du driver pour Firefox
                this.setDriver(new FirefoxDriver());
                break;
            default:
                //Instanciation du driver pour Chrome
                System.setProperty("webdriver.chrome.driver", path);
                this.setDriver(new ChromeDriver());
                break;
        }
        System.setProperty(nav, path);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("acceptInsecureCerts", true);
        nlDriver = (NLWebDriverFactory.newNLWebDriver(driver, "EpiSaveursPath", null));

        //Permet de mettre en fullscreen le navigateur
        driver.manage().window().maximize();
    }


    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver(){
        return this.driver;
    }

    public NLWebDriver getNlDriver(){
        return this.nlDriver;
    }

    public void waitFullyLoaded(int seconds) {
        // Attente jusqu'au chargement complet de la page
        (new WebDriverWait(nlDriver, seconds))
                .until(driver1 -> String.valueOf(
                        ((JavascriptExecutor) driver1).executeScript("return document.readyState")
                ).equals("complete"));
    }

    public void init() {
        //Transaction que l'on retrouvera sur Neoload
        
        nlDriver.startTransaction("SC00_T00_Access");
        try {
            //Arrivée sur le site EpiSaveurs
            nlDriver.get("https://www.episaveurs.fr/");
            new ResponseTime(driver, "init");
            waitFullyLoaded(5);
            //Condition permettant de vérifier que la pop-up soit bien affichée avant de cliquer dessus afin d'éviter une erreur
            try{
                nlDriver.findElement(By.id("modal-footer")).findElement(By.className("close")).click();
            }catch (Exception e){
                System.err.println(e);
            }
        } catch (Exception e) {
            new LogFile("init", e.toString());
            new Screenshot(nlDriver, "init");
            nlDriver.quit();
            exit(1);
        }
    }


    public void connexion(String login, String mdp) {
        //Connexion sur le site en utilisant les arguments login et mdp
        nlDriver.startTransaction("SC00_T01_Login");
        try {
            //Click sur le bouton permettant d'afficher la petite fenêtre de login
            nlDriver.findElement(By.id("gp-account")).findElements(By.tagName("a")).get(0).click();
            //Envoie des informations à saisir
            nlDriver.findElement(By.id("edit-mail")).sendKeys(login);
            nlDriver.findElement(By.id("edit-mdp")).sendKeys(mdp);
            //Click sur le bouton submit pour lancer la connexion
            nlDriver.findElement(By.id("edit-login-submit")).click();
            new ResponseTime(nlDriver, "connexion");
            Thread.sleep(1000);
        } catch (Exception e) {
            new LogFile("connexion", e.toString());
            new Screenshot(nlDriver, "connexion");
            nlDriver.quit();
            exit(1);
        }
    }


    public void goToEcommande(){
        nlDriver.startTransaction("SC00_T02_ECommerce");
        try {
            nlDriver.findElement(By.className("link-main-navigation-ecommerce")).click(); //Click sur le bouton redirigeant vers l'onglet E-Commerce
            tabs = new ArrayList<>(nlDriver.getWindowHandles()); //tabs va récupérer la valeur du nombre d'onglets
            nlDriver.switchTo().window(tabs.get(1)); //On change d'onglet afin d'aller sur celui de E-Commerce
            Thread.sleep(10000);
            new ResponseTime(nlDriver, "goToEcommerce_pop_up");
            new WebDriverWait(nlDriver, 10).until(ExpectedConditions.titleContains("Site marchand du groupe Pomona"));
            try{ nlDriver.findElement(By.name("check_address_and_date")).findElement(By.tagName("button")).click(); }
            catch(Exception e){ System.err.println(e); }
            new ResponseTime(nlDriver, "goToEcommerce_confirm_informations");
            /*Le temps de chargement de la fonction de validation des informations étant relativement long, on effectue une vérification
            toutes les demi seconde jusqu'à ce que la pop-up disparaisse*/
            while (nlDriver.findElement(By.name("check_address_and_date")).isDisplayed())
            {
                Thread.sleep(500);
            }
            Thread.sleep(2000);
        }catch (Exception e){
            new LogFile("goToEcommerce", e.toString());
            new Screenshot(nlDriver, "goToEcommerce");
            nlDriver.quit();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            throw new SkipException(sw.toString());
            //exit(1);
        }
    }


    public void searchEcommande(String recherche){
        nlDriver.startTransaction("SC00_T03_Search");
        try {
            //Recherche en se basant sur l'argument <recherche> dans l'onglet E-COMMERCE
            waitFullyLoaded(15);
            nlDriver.findElement(By.id("search_mini_form")).click();
            (new Actions(driver)).sendKeys(recherche).perform();
            nlDriver.findElement(By.className("btn-searchcstm")).click();
            new ResponseTime(nlDriver, "searchEcommerce : "+recherche);
            (new WebDriverWait(nlDriver, 15)).until(ExpectedConditions.titleContains("Résultats de recherche"));
            try{
                if((nlDriver.findElement(By.xpath("/html/body/div[6]/div/div/div/p")).getText()).contains("Aucun résultat pour votre recherche")){
                    new LogFile("searchEcommerce", "No result for the search : "+recherche);
                    new Screenshot(nlDriver, "searchEcommerce");
                }

            }catch (Exception e){
                System.err.println(e);
            }
        }catch (Exception e){
            new LogFile("searchEcommerce",e.toString());
            new Screenshot(nlDriver, "searchEcommerce");
            nlDriver.quit();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            throw new SkipException(sw.toString());
            //exit(1);
        }
    }


    public void logoutEcommande(){
        nlDriver.startTransaction("SC00_T04_Logout_Ecommande");
        try {
            //Déconnexion du site E-COMMERCE
            nlDriver.findElement(By.id("button-logout")).click();
            new ResponseTime(nlDriver, "logoutEcommerce");
            (new WebDriverWait(nlDriver, 20)).until(ExpectedConditions.titleContains("Confirmation"));
            //Thread.sleep(1000);
        }catch (Exception e){
            new LogFile("logoutEcommerce", e.toString());
            new Screenshot(nlDriver, "logoutEcommerce");
            nlDriver.quit();
            exit(1);
        }
    }


    public void logoutEpiSaveurs(){
        nlDriver.startTransaction("SC00_T05_Logout_EpiSaveurs");
        try {
            //Déconnexion du site EpiSaveurs
            nlDriver.close();
            //Thread.sleep(1000);
            nlDriver.switchTo().window(tabs.get(0));
            nlDriver.findElement(By.className("fa-user")).click();
            new ResponseTime(nlDriver, "logoutEpiSaveurs");
            //Thread.sleep(1000);
            nlDriver.findElement(By.id("gp-navbar-customer-space"))
                    .findElement(By.xpath("//*[@id=\"gp-navbar-customer-space\"]/ul/li[6]/a"))
                    .click();
            (new WebDriverWait(nlDriver, 20)).until(ExpectedConditions.titleContains("EpiSaveurs"));
            //Thread.sleep(1000);
        }catch (Exception e){
            new LogFile("logoutEpiSaveurs", e.toString());
            new Screenshot(nlDriver, "logoutEpiSaveurs");
            nlDriver.quit();
            exit(1);
        }
    }


    public void end(){
        nlDriver.stopTransaction();
        nlDriver.quit();
    }
}
