package com.example.ProjektArbete;

public class BarnData extends AnvandarData {

    public BarnData(String namn, String aktivitet,String dag) {
        super(namn, aktivitet,dag);

    }

        public static BarnData createBarnLista (String namn, String
        aktivitet,String dag)
        {
            return new BarnData(namn, aktivitet,dag);
        }


    }

