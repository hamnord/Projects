package com.example.ProjektArbete;

import java.util.ArrayList;

public class BarnLista {

    private ArrayList<BarnData> barnAktivitetsLista;

    public BarnLista(){
        this.barnAktivitetsLista=new ArrayList<>();
    }

    public boolean addNewBarnAktivitet(BarnData nyAktivitet){
        if(findBarnAktivitet(nyAktivitet.getAktivitet()) >=0 ) {
            System.out.println("En aktivitet finns redan med det namnet. \n Var vänlig och välj ett annat namn." );
            return false;

        }
        barnAktivitetsLista.add(nyAktivitet);
        return true;
    }

    public boolean removeBarnAktivitet(BarnData throwAktivitet) {
        int foundAktivitet = findBarnAktivitet(throwAktivitet);
        if(foundAktivitet <0){
            System.out.println(throwAktivitet.getAktivitet() + " gick inte att hitta");
            return false;

        }
        this.barnAktivitetsLista.remove(foundAktivitet);
        System.out.println(throwAktivitet.getAktivitet() + " har tagits bort.");
        return true;
    }

    private int findBarnAktivitet(BarnData hittaAktivitet) { return this.barnAktivitetsLista.indexOf(hittaAktivitet);}

    private int findBarnAktivitet(String aktivitetsNamn){
        for(int i=0; i<this.barnAktivitetsLista.size();i++) {
            BarnData hittaAktivitet = this.barnAktivitetsLista.get(i);
            if (hittaAktivitet.getAktivitet().equals(aktivitetsNamn)) {
                return i;
            }
        }
        return -1;
    }

    public BarnData queryBarnAktivitet(String aktivitet){
        int position = findBarnAktivitet(aktivitet);
        if(position >=0) {
            return this.barnAktivitetsLista.get(position);
        }

        return null;
    }

    public void printBarnAktivitet(){
        System.out.println("Aktiviteter, Barn: ");
        for ( int i=0; i<this.barnAktivitetsLista.size(); i++){
            System.out.println((i+1) + " På: " +
                    this.barnAktivitetsLista.get(i).getDag() + " Skall: " + this.barnAktivitetsLista.get(i).getNamn() + " " +
                    this.barnAktivitetsLista.get(i).getAktivitet());

        }
    }


}

