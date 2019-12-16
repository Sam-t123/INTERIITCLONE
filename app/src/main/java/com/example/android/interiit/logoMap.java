package com.example.android.interiit;

import java.util.HashMap;
import java.util.Map;


public class logoMap {

    private Map<String,Integer> logo = new HashMap<>();

    public logoMap()
    {
        logo.put("Madras"      ,R.drawable.madras);
        logo.put("madras"      ,R.drawable.madras);
        logo.put("Indore"      ,R.drawable.indore);
        logo.put("Kharagpur"   ,R.drawable.kharagpur);
        logo.put("Bhilai"      ,R.drawable.bhilai);
        logo.put("Bombay"      ,R.drawable.bombay);
        logo.put("Dhanbad"     ,R.drawable.dhanbad);
        logo.put("Delhi"       ,R.drawable.delhi);
        logo.put("Roorkee"     ,R.drawable.roorkee);
        logo.put("Ropar"       ,R.drawable.ropar);
        logo.put("Tirupati"    ,R.drawable.tirupati);
        logo.put("Kanpur"      ,R.drawable.kanpur);
        logo.put("BHU"         ,R.drawable.bhu);
        logo.put("Gandhinagar" ,R.drawable.gandhinagar);
        logo.put("Guwahati"    ,R.drawable.guwahati);
        logo.put("Bhubaneswar" ,R.drawable.bhubaneswar);
        logo.put("Hyderabad"   ,R.drawable.hyderabad);
        logo.put("Jodhpur"     ,R.drawable.jodhpur);
        logo.put("Patna"       ,R.drawable.patna);
        logo.put("Mandi"       ,R.drawable.mandi);
        logo.put("Palakkad"    ,R.drawable.palakkad);
        logo.put("Goa"         ,R.drawable.goa);
        logo.put("Jammu"       ,R.drawable.jammu);
        logo.put("Dharwad"     ,R.drawable.dharwad);
        logo.put("NA"          ,R.drawable.na);
    }

    public Map getMap()
    {
        return logo;
    }

}
