package com.dimsoft.clinicStackProd.util;

import java.util.HashMap;
import java.util.Map;

public class PlaningConst {
    public Map<Integer, String> days = new HashMap<>();

    public PlaningConst() {
        days.put(1, "Lundi");
        days.put(2, "Mardi");
        days.put(3, "Mercredi");
        days.put(4, "Jeudi");
        days.put(5, "Vendredi");
        days.put(6, "Samedi");
        days.put(7, "Dimanche");
    }

}
