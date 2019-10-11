package com.example.ProjektArbete;

public class AnvandarData {

        private String namn;
        private String datum;
        private String dag;
        private String tid;
        private String aktivitet;


    public AnvandarData(String namn, String datum, String tid, String aktivitet){
            this.namn=namn;
            this.datum=datum;
            this.tid=tid;
            this.aktivitet=aktivitet;
        }

    public AnvandarData(String namn, String aktivitet, String dag) {
        this.namn=namn;
        this.aktivitet=aktivitet;
        this.dag=dag;
    }


    public String getNamn(){return namn;}
        public String getDatum(){return datum;}
        public String getDag(){return dag;}
        public String getTid(){return tid;}
        public String getAktivitet(){return aktivitet;}

    public static AnvandarData createAktivitetsLista (String namn, String datum, String tid, String aktivitet)
    { return new AnvandarData(namn,datum,tid,aktivitet);}

    }