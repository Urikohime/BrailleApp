package com.example.abyteofbraille.methods;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.abyteofbraille.BrailleList;
import com.example.abyteofbraille.BrailleObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class QuestionGenerator {
    public static DBA db;
    public static List<Skill> dbImage = new ArrayList<>();
    private static boolean canCheck = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static BrailleObject getQuestionLetter()
    {
        return getBO(BrailleList.BrailleLetters);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static BrailleObject getQuestionNumber()
    {
        return getBO(BrailleList.BrailleNumbers);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static BrailleObject getQuestionMixed()
    {
        return getBO(BrailleList.BrailleMixed);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static BrailleObject getBO(List<BrailleObject> list)
    {
        BrailleObject returner;

        List<BrailleObject> BOList = new ArrayList<>();

        for(BrailleObject BO: list)
        {
            Optional<BrailleObject> index = list.stream()
                    .filter(BrailleObject -> BO.GetSymbol().equals((BrailleObject.GetSymbol())))
                    .findFirst();
            if(BO.GetSymbol().equals(index.get().GetSymbol()))
            {
                BOList.add(index.get());
                for(int i = 0; i < dbImage.stream().filter(Skill -> BO.GetSymbol().toUpperCase().equals((Skill.symbol))).findFirst().get().skill; i++)
                {
                    BOList.add(index.get());
                }
            }
        }

        returner = BOList.get(new Random().nextInt(BOList.size()));

        return returner;
    }

    public static void createDbImage()
    {
        dbImage = db.getAll();
    }

    public static boolean CheckAnswerLTS(BrailleObject BO, List<Boolean> answers)
    {
        boolean returner = Arrays.equals(new List[]{BO.ToBoolList()}, new List[]{answers});

        if(canCheck) {
            canCheck = false;
            new Thread(() -> updateSkill(returner, BO.GetSymbol())).start();
        }

        if(returner)
        {
            canCheck = true;
        }

        return returner;
    }

    public static boolean CheckAnswerSTL(BrailleObject BO, String answer)
    {
        boolean returner = BO.GetSymbol().toUpperCase().equals(answer.toUpperCase());

        System.out.println(BO.GetSymbol() + " :" + answer);
        if(canCheck) {
            canCheck = false;
            new Thread(() -> updateSkill(returner, BO.GetSymbol())).start();
        }

        if(returner)
        {
            canCheck = true;
        }

        return returner;
    }

    public static boolean CheckAnswerIdent(List<Boolean> Identanswers)
    {
        return Arrays.equals(new List[]{
            Arrays.asList(false, false, true, true, true, true)
        }, new List[]{Identanswers});
    }

    public static List<Integer> Hint(BrailleObject BO)
    {
        List<Integer> Returner = new ArrayList<>();

        if(canCheck) {
            canCheck = false;
            new Thread(() -> updateSkill(false, BO.GetSymbol())).start();
        }
        for(int i = 0; i < BO.ToBoolList().size(); i++)
        {
            if(BO.ToBoolList().get(i)) { Returner.add(i); }
        }

        return Returner;
    }

    private static void updateSkill(boolean checker, String symbol)
    {
        if (checker && db.findBySymbol(symbol).skill > 0) {
            db.updateSkill(
                    db.findBySymbol(symbol).id,
                    (db.findBySymbol(symbol).skill + 10));
        } else if (!checker && db.findBySymbol(symbol).skill < 100) {
            db.updateSkill(
                    db.findBySymbol(symbol).id,
                    (db.findBySymbol(symbol).skill - 10));
        }

        if (db.findBySymbol(symbol).skill < 0) {
            db.updateSkill(db.findBySymbol(symbol).id, 0);
        }
        else if(db.findBySymbol(symbol).skill > 100) {
            db.updateSkill(db.findBySymbol(symbol).id, 100);
        }
    }
}
