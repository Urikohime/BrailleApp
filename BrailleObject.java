package com.example.abyteofbraille;

import java.util.Arrays;
import java.util.List;

public class BrailleObject {
    private String Symbol;
    private int[] Dots;
    public BrailleObject(String symbol, int[] dots)
    {
        Symbol = symbol;
        Dots = dots;
    }

    public String GetSymbol()
    {
        return Symbol;
    }

    public int[] GetDots()
    {
        return Dots;
    }

    public List<Boolean> ToBoolList()
    {
        List<Boolean> Returner = Arrays.asList(false, false, false, false, false, false);
        for(int i: Dots)
        {
            if(i == 1) { Returner.set(0, true); }
            if(i == 2) { Returner.set(1, true); }
            if(i == 3) { Returner.set(2, true); }
            if(i == 4) { Returner.set(3, true); }
            if(i == 5) { Returner.set(4, true); }
            if(i == 6) { Returner.set(5, true); }
        }
        return Returner;
    }
}
