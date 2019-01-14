package main.java;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Reporter;

import java.time.LocalDateTime;

@Listeners
public class Main {
    @Test
    public static void main() {
        //Instanciation de l'objet NavigationEpiSaveurs sur lequel on va se baser pour simuler toutes les étapes de la navigation
        Test_time t = new Test_time();
        NavigationEpiSaveurs myNav = new NavigationEpiSaveurs("Chrome", ".\\chromedriver.exe");
        t.start();
        t.startTransaction("init");
        myNav.init();
        Reporter.log("Initialisation terminé !");
        t.stopTransaction("init");
        t.startTransaction("connect");
        myNav.connexion("alertes-systeme@groupe-pomona.fr", "pomona18");
        Reporter.log("Connexion réussi !");
        t.stopTransaction("connect");
        t.startTransaction("goEcommande");
        myNav.goToEcommande();
        Reporter.log("Ouverture du site de commande réussi");
        t.stopTransaction("goEcommande");
        t.startTransaction("search");
        myNav.searchEcommande("biscuit au chocolat");
        Reporter.log("Recherche fonctionnel !");
        t.stopTransaction("search");
        t.startTransaction("déco");
        myNav.logoutEcommande();
        myNav.logoutEpiSaveurs();
        t.stopTransaction("déco");
        myNav.end();
        Reporter.log("Déconnexion réussi !");
        t.stop();
    }
}
