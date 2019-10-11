package com.example.ProjektArbete;

import java.util.ArrayList;

public class AnvandarLista {

    private ArrayList<AnvandarData> aktivitetsLista;

    public AnvandarLista(){
        this.aktivitetsLista=new ArrayList<>();
    }

    public boolean addNewAktivitet(AnvandarData nyAktivitet){
        if(findAktivitet(nyAktivitet.getAktivitet()) >=0 ) {
            System.out.println("En aktivitet finns redan med det namnet. \n Var vänlig och välj ett annat namn." );
            return false;
        }
        aktivitetsLista.add(nyAktivitet);
        return true;
    }

    public boolean updateAktivitet(AnvandarData oldAktivitet,AnvandarData newAktivitet) {
        int foundAktivitet = findAktivitet(oldAktivitet);
        if (foundAktivitet < 0) {
            System.out.println("Aktiviteten hittades inte.");
            return false;
        }
        if (newAktivitet.equals(oldAktivitet)) {
            System.out.println(" Namnet har inte ändrats: " + oldAktivitet.getNamn());
            System.out.println(" Datumet har inte ändrats: " + oldAktivitet.getDatum());
            System.out.println(" Dagen har inte ändrats: " + oldAktivitet.getDag());
            System.out.println(" Tiden har inte ändrats: " + oldAktivitet.getTid());
            System.out.println(" Aktiviten har inte ändrats: " + oldAktivitet.getAktivitet());
            return true;

    }
        this.aktivitetsLista.set(foundAktivitet, newAktivitet);
        System.out.println(oldAktivitet.getAktivitet() + " har uppdaterats till: " + newAktivitet.getAktivitet());
        return true;
    }

    public boolean removeAktivitet(AnvandarData throwAktivitet) {
        int foundAktivitet = findAktivitet(throwAktivitet);
        if(foundAktivitet <0){
            System.out.println(throwAktivitet.getAktivitet() + "gick inte att hitta");
            return false;

        }
        this.aktivitetsLista.remove(foundAktivitet);
        System.out.println(throwAktivitet.getAktivitet() + "har tagits bort.");
        return true;
    }

    private int findAktivitet(AnvandarData hittaAktivitet) { return this.aktivitetsLista.indexOf(hittaAktivitet);}

    private int findAktivitet(String aktivitetsNamn){
        for(int i=0; i<this.aktivitetsLista.size();i++) {
            AnvandarData hittaAktivitet = this.aktivitetsLista.get(i);
            if (hittaAktivitet.getAktivitet().equals(aktivitetsNamn)) {
                return i;
            }
        }
        return -1;
    }

    public AnvandarData queryAktivitet(String aktivitet){
        int position = findAktivitet(aktivitet);
        if(position >=0) {
            return this.aktivitetsLista.get(position);
        }

        return null;
    }

    public void printAktivitet(){
        System.out.println("Aktiviteter, Vuxna: ");
        for ( int i=0; i<this.aktivitetsLista.size(); i++){
            System.out.println((i+1) + ".  Den " +
                    this.aktivitetsLista.get(i).getDatum() + " Klockan: " +
                    this.aktivitetsLista.get(i).getTid() + ", Har " + this.aktivitetsLista.get(i).getNamn() + " Aktiviteten: " +
                    this.aktivitetsLista.get(i).getAktivitet());

        }
    }



}
