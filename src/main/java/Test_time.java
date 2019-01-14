package main.java;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Hashtable;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class Test_time {
    private LocalDateTime gTime;
    private Hashtable<String, LocalDateTime> tTime = new Hashtable<String, LocalDateTime>();


    public void start () {
        gTime = LocalDateTime.now();
        System.out.println(gTime + " Starting test...");
        Reporter.log("Début de test:  " + String.valueOf(gTime));
    }

    public void stop () {
        LocalDateTime tmp = LocalDateTime.now();
        System.out.println(tmp + " Test ending : " + Duration.between( gTime, tmp));
        Reporter.log("Fin de test:  " + String.valueOf(tmp));
    }

    public void startTransaction (String transaction_name) {
        LocalDateTime tmp = LocalDateTime.now();
        tTime.put(transaction_name, tmp);
        System.out.println(tmp + " Starting transaction " + transaction_name + "...");
    }

    public void stopTransaction (String transaction_name) {
        LocalDateTime tmp = LocalDateTime.now();
        System.out.println(tmp + " Ending transaction " + transaction_name + " : " + Duration.between( tTime.remove(transaction_name), tmp));
    }
}