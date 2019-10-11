/**
 *  <h1> ProjektArbete - AktivitetsLista</h1>
 *  Det här är ett program för att skapa en aktivitetslista/schema för en hel familj.
 * programmet är anpassat för hela familjen, dvs användaren kan välja om hen vill skriva in en aktivitet för vuxna eller barn.
 * programmet kommer därefter vara anpassat efter det tidigare valet.
 *
 * @author Hampus Nordenstein
 * @version 1.0
 * @since 2019-09-27
 */

package com.example.ProjektArbete;

import java.util.Scanner;

public class MainRun {

    private static Scanner scan = new Scanner(System.in);
    private static AnvandarLista aktivitetsLista = new AnvandarLista();
    private static BarnLista barnAktivitetsLista = new BarnLista();

    public static void main(String[] args) {

        boolean quit = false;
        String[] val = new String[2];
        val[0] = "barn";

        System.out.println(" \n Välkommen till håll koll på eran skit ");
        printAlternativ();

        while (!quit) {
            System.out.println("Välj: ");
            int avsluta = scan.nextInt();
            scan.nextLine();

            switch (avsluta) {
                case 0:
                    System.out.println("\n Avslutar...");
                    quit = true;
                    break;

                case 1:
                    aktivitetsLista.printAktivitet();
                    System.out.println("--------------------------------------------------------------------------------");
                    barnAktivitetsLista.printBarnAktivitet();
                    break;

                case 2:
                    System.out.println("Vill du lägga till en aktivitet för vuxna eller barn? ");
                    String valja= scan.nextLine();
                    if (valja.equals(val[0])) {addNewBarnAktivitet();}
                    else {addNewAktivitet();}
                    break;

                case 3:
                    updateAktivitet();
                    break;

                case 4:
                    System.out.println("Vill du ta bort en aktivitet för vuxna eller barn? ");
                    String valja2= scan.nextLine();
                    if (valja2.equals(val[0])) {removeBarnAktivitet();}
                    else {removeAktivitet();}
                    break;

                case 5:
                    printAlternativ();
                    break;

            }
        }

    }


    private static void addNewAktivitet() {

        System.out.println("Ange Namn: ");
        String namn = scan.nextLine();
        System.out.println("Ange din aktivitet: ");
        String aktivitet = scan.nextLine();
        System.out.println("Ange vilken datum: ");
        String datum = scan.nextLine();
        System.out.println("Ange vilken tid: ");
        String tid = scan.nextLine();

        AnvandarData nyAktivitet = AnvandarData.createAktivitetsLista(namn, datum, tid, aktivitet);

        if (aktivitetsLista.addNewAktivitet(nyAktivitet)) {
            System.out.println("\n Användare, Vuxna: " + namn );
            System.out.println("Aktiviteten kommer att äga rum den: " + datum + " klockan: " + tid);
        } else {
            System.out.println(" något gick snett...");
        }
    }

    private static void addNewBarnAktivitet() {

        System.out.println("Ange Namn: ");
        String namn = scan.nextLine();
        System.out.println("Ange din aktivitet: ");
        String aktivitet = scan.nextLine();
        System.out.println("Ange vilken dag: ");
        String dag = scan.nextLine();

        BarnData nyAktivitet = BarnData.createBarnLista(namn, aktivitet,dag);

        if (barnAktivitetsLista.addNewBarnAktivitet(nyAktivitet)) {
            System.out.println("\n Användare, Barn: " + namn );
            System.out.println("kommer att: " + aktivitet + " på en: " + dag );
        } else {
            System.out.println(" något gick snett...");
        }
    }

    private static void updateAktivitet() {
        System.out.println(" Skriv in aktiviteten som skall ändras: ");
        String aktivitet = scan.nextLine();
        AnvandarData nuvarandeAktivitet = aktivitetsLista.queryAktivitet(aktivitet);
        if (nuvarandeAktivitet == null) {
            System.out.println("Aktiviteten Hittades inte...");
            return;
        }
        System.out.println(" \n Skriv in ett nytt namn: ");
        String newNamn = scan.nextLine();
        System.out.println(" Skriv in en nytt datum: ");
        String newDatum = scan.nextLine();
        System.out.println(" Skriv in en ny tid: ");
        String newtid = scan.nextLine();
        System.out.println(" Slutligen skriv in en ny aktivitet: ");
        String newAktivitet = scan.nextLine();
        AnvandarData newHandelse = AnvandarData.createAktivitetsLista(newNamn,newDatum, newtid, newAktivitet);
        if (aktivitetsLista.updateAktivitet(nuvarandeAktivitet, newHandelse)) {
            System.out.println(" Aktiviteten har uppdaterats! ");
        } else {
            System.out.println(" Något gick snett...");
        }
    }


    private static void removeAktivitet() {
        System.out.println("Skriv in den Aktivitet som skall tas bort: ");
        String aktivitet = scan.nextLine();
        AnvandarData nuvarandeAktivitet = aktivitetsLista.queryAktivitet(aktivitet);
        if (nuvarandeAktivitet == null) {
            System.out.println("Hittade inte Aktiviteten...");
            return;
        }
        if (aktivitetsLista.removeAktivitet(nuvarandeAktivitet)) {
            System.out.println("Aktiviteten " + aktivitet + " har tagits bort!");
        } else {
            System.out.println("Något gick snett...");
        }

    }

    private static void removeBarnAktivitet() {
        System.out.println("Skriv in den Aktivitet som skall tas bort: ");
        String aktivitet = scan.nextLine();
        BarnData nuvarandeAktivitet = barnAktivitetsLista.queryBarnAktivitet(aktivitet);
        if (nuvarandeAktivitet == null) {
            System.out.println("Hittade inte Aktiviteten...");
            return;
        }
        if (barnAktivitetsLista.removeBarnAktivitet(nuvarandeAktivitet)) {
            System.out.println("Aktiviteten " + aktivitet + " har tagits bort!");
        } else {
            System.out.println("Något gick snett...");
        }

    }

    private static void printAlternativ() {
        System.out.println("\n Alternativ: ");
        System.out.println("\t 1 - Visa användare & aktiviteter");
        System.out.println("\t 2 - Lägg till en ny aktivitet.");
        System.out.println("\t 3 - Ändra aktivitet. ");
        System.out.println("\t 4 - Ta bort en aktivitet.");
        System.out.println("\t 5 - Visa alternativ.");
        System.out.println("\t 0 - Avslutar programmet.");
    }
}