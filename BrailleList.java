package com.example.abyteofbraille;

import com.example.abyteofbraille.methods.QuestionGenerator;
import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.List;

public class BrailleList {
    public static List<BrailleObject> BrailleLetters = new ArrayList<>();
    public static List<BrailleObject> BrailleNumbers = new ArrayList<>();
    public static List<BrailleObject> BrailleMixed = new ArrayList<>();
    private BrailleList()
    {
        List<String> Symbols = JsonPath.parse(BList.List).read("letters[*].symbol");
        List<String> Dots = JsonPath.parse(BList.List).read("letters[*].dots");
        for (int i = 0; i < Symbols.size(); i++) {
            String[] temp = Dots.get(i).split(",");
            int[] DotsToList = new int[temp.length];
            for (int ii = 0; ii < temp.length; ii++) {
                DotsToList[ii] = Integer.parseInt(temp[ii]);
            }
            BrailleLetters.add(new BrailleObject(Symbols.get(i), DotsToList));
        }

        Symbols = JsonPath.parse(BList.List).read("numbers[*].symbol");
        Dots = JsonPath.parse(BList.List).read("numbers[*].dots");
        for (int i = 0; i < Symbols.size(); i++) {
            String[] temp = Dots.get(i).split(",");
            int[] DotsToList = new int[temp.length];
            for (int ii = 0; ii < temp.length; ii++) {
                DotsToList[ii] = Integer.parseInt(temp[ii]);
            }
            BrailleNumbers.add(new BrailleObject(Symbols.get(i), DotsToList));
        }

        CreateDBEntries();
        BrailleMixed.addAll(BrailleLetters);
        BrailleMixed.addAll(BrailleNumbers);
        BrailleMixed.addAll(BrailleNumbers);
    }

    public static void InitBrailleList()
    {
        new BrailleList();
    }

    public static BrailleObject GetBrailleLetter(int index)
    {
        return BrailleLetters.get(index);
    }

    public static BrailleObject GetBrailleNumber(int index)
    {
        return BrailleNumbers.get(index);
    }

    public static BrailleObject GetBrailleMixed(int index)
    {
        return BrailleMixed.get(index);
    }

    public static int GetLettersListLength()
    {
        return BrailleLetters.size();
    }

    public static int GetNumbersListLength()
    {
        return BrailleNumbers.size();
    }

    public static int GetMixedListLength()
    {
        return BrailleMixed.size();
    }

    public static void CreateDBEntries()
    {
        int id = 0;
        for(BrailleObject BO: BrailleLetters)
        {
            if (QuestionGenerator.db.findBySymbol(BO.GetSymbol().toUpperCase()) == null) {
                QuestionGenerator.db.insert(id, BO.GetSymbol().toUpperCase(), 50);
                id++;
            }
            {}
        }
        for(BrailleObject BO: BrailleNumbers)
        {
            if(QuestionGenerator.db.findBySymbol(BO.GetSymbol().toUpperCase()) == null){
                QuestionGenerator.db.insert(id, BO.GetSymbol().toUpperCase(), 50);
                id++;
            }
            {}
        }
    }
}
