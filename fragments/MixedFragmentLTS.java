package com.example.abyteofbraille.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abyteofbraille.BrailleObject;
import com.example.abyteofbraille.R;
import com.example.abyteofbraille.methods.QuestionGenerator;

import java.util.Arrays;
import java.util.List;

public class MixedFragmentLTS extends Fragment {
    List<Boolean> Answer = Arrays.asList(false, false, false, false, false, false);
    List<Boolean> IdentAnswer = Arrays.asList(false, false, false, false, false, false);
    ImageButton dt1; ImageButton dt2; ImageButton dt3;
    ImageButton dt4; ImageButton dt5; ImageButton dt6;
    ImageButton id1; ImageButton id2; ImageButton id3;
    ImageButton id4; ImageButton id5; ImageButton id6;
    List<ImageButton> dotList;
    List<ImageButton> identList;
    ConstraintLayout idents;

    Button hintBtn;
    Button checkBtn;
    Button backBtn;
    Button nextBtn;
    TextView mixedView;
    BrailleObject Question;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mixed_lts, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dt1 = view.findViewById(R.id.dot_1); dt2 = view.findViewById(R.id.dot_2);
        dt3 = view.findViewById(R.id.dot_3); dt4 = view.findViewById(R.id.dot_4);
        dt5 = view.findViewById(R.id.dot_5); dt6 = view.findViewById(R.id.dot_6);
        id1 = view.findViewById(R.id.ident_1); id2 = view.findViewById(R.id.ident_2);
        id3 = view.findViewById(R.id.ident_3); id4 = view.findViewById(R.id.ident_4);
        id5 = view.findViewById(R.id.ident_5); id6 = view.findViewById(R.id.ident_6);
        dotList = Arrays.asList(dt1, dt2, dt3, dt4, dt5, dt6);
        identList = Arrays.asList(id1, id2, id3, id4, id5, id6);
        idents = view.findViewById(R.id.Identifier_dots);

        hintBtn = view.findViewById(R.id.hint_mixed_button);
        checkBtn = view.findViewById(R.id.check_mixed_button);
        backBtn = view.findViewById(R.id.back_button);
        nextBtn = view.findViewById(R.id.next_question_button);
        mixedView = view.findViewById(R.id.mixed_view);

        if(nextBtn.getVisibility() == View.VISIBLE)
        {
            nextBtn.setVisibility(View.INVISIBLE);
            nextBtn.setClickable(false);
        }

        initiateDots(view, savedInstanceState);
        initiateIdents(view, savedInstanceState);
        SetQuestionAndResetDots();

        backBtn.setOnClickListener(view12 -> NavHostFragment.findNavController(MixedFragmentLTS.this)
                .navigate(R.id.mixed_to_home_lts));

        hintBtn.setOnClickListener(view1 -> ActivateHint());

        checkBtn.setOnClickListener(v -> CheckAnswer());

        nextBtn.setOnClickListener(v -> new Thread(this::SetQuestionAndResetDots).start());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetQuestionAndResetDots()
    {
        new Thread(QuestionGenerator::createDbImage).start();
        Question = QuestionGenerator.getQuestionMixed();
        SwitchIdentifier(tryParseInt(Question.GetSymbol()));

        mixedView.setText(Question.GetSymbol());
        checkBtn.setClickable(true);
        hintBtn.setClickable(true);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setClickable(false);
        for(int i = 0; i < dotList.size(); i++)
        {
            identList.get(i).setImageResource(R.drawable.ic_empty_circle);
            identList.get(i).setClickable(true);
            identList.get(i).setAlpha(1f);
            dotList.get(i).setImageResource(R.drawable.ic_empty_circle);
            dotList.get(i).setClickable(true);
            dotList.get(i).setAlpha(1f);
            Answer.set(i, false);
            IdentAnswer.set(i, false);
        }
    }

    private void ActivateHint()
    {
        for(ImageButton IB: dotList) { IB.setAlpha(0.1f); }
        for(ImageButton IB: identList) { IB.setAlpha(0.1f); }
        for(int i: QuestionGenerator.Hint(Question))
        {
            dotList.get(i).setAlpha(1f);
        }
        for(int i: Arrays.asList(2, 3, 4, 5))
        {
            identList.get(i).setAlpha(1f);
        }
        hintBtn.setClickable(false);
    }

    private void CheckAnswer()
    {
        if(!tryParseInt(Question.GetSymbol()) && QuestionGenerator.CheckAnswerLTS(Question, Answer))
        {
            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setClickable(true);
            checkBtn.setTextColor(Color.BLACK);
            checkBtn.setClickable(false);
            hintBtn.setClickable(false);
            for(int i = 0; i < dotList.size(); i++)
            {
                dotList.get(i).setClickable(false);
            }
        }
        else if(tryParseInt(Question.GetSymbol()) &&
                QuestionGenerator.CheckAnswerLTS(Question, Answer) &&
                QuestionGenerator.CheckAnswerIdent(IdentAnswer))
        {
            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setClickable(true);
            checkBtn.setTextColor(Color.BLACK);
            checkBtn.setClickable(false);
            hintBtn.setClickable(false);
            for(int i = 0; i < dotList.size(); i++)
            {
                dotList.get(i).setClickable(false);
                identList.get(i).setClickable(false);
            }
        }
        else
        {
            checkBtn.setTextColor(Color.RED);
        }
    }

    private void initiateDots(@NonNull View view, Bundle savedInstanceState)
    {
        dt1.setOnClickListener(v -> {
            if(!Answer.get(0)) {
                dt1.setImageResource(R.drawable.ic_full_circle);
                Answer.set(0, true);
            }
            else
            {
                dt1.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(0, false);
            }
        });

        dt2.setOnClickListener(v -> {
            if(!Answer.get(1)) {
                dt2.setImageResource(R.drawable.ic_full_circle);
                Answer.set(1, true);
            }
            else
            {
                dt2.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(1, false);
            }
        });

        dt3.setOnClickListener(v -> {
            if(!Answer.get(2)) {
                dt3.setImageResource(R.drawable.ic_full_circle);
                Answer.set(2, true);
            }
            else
            {
                dt3.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(2, false);
            }
        });

        dt4.setOnClickListener(v -> {
            if(!Answer.get(3)) {
                dt4.setImageResource(R.drawable.ic_full_circle);
                Answer.set(3, true);
            }
            else
            {
                dt4.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(3, false);
            }
        });

        dt5.setOnClickListener(v -> {
            if(!Answer.get(4)) {
                dt5.setImageResource(R.drawable.ic_full_circle);
                Answer.set(4, true);
            }
            else
            {
                dt5.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(4, false);
            }
        });

        dt6.setOnClickListener(v -> {
            if(!Answer.get(5)) {
                dt6.setImageResource(R.drawable.ic_full_circle);
                Answer.set(5, true);
            }
            else
            {
                dt6.setImageResource(R.drawable.ic_empty_circle);
                Answer.set(5, false);
            }
        });
    }

    private void initiateIdents(@NonNull View view, Bundle savedInstanceState)
    {
        id1.setOnClickListener(v -> {
            if(!IdentAnswer.get(0)) {
                id1.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(0, true);
            }
            else
            {
                id1.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(0, false);
            }
        });

        id2.setOnClickListener(v -> {
            if(!IdentAnswer.get(1)) {
                id2.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(1, true);
            }
            else
            {
                id2.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(1, false);
            }
        });

        id3.setOnClickListener(v -> {
            if(!IdentAnswer.get(2)) {
                id3.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(2, true);
            }
            else
            {
                id3.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(2, false);
            }
        });

        id4.setOnClickListener(v -> {
            if(!IdentAnswer.get(3)) {
                id4.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(3, true);
            }
            else
            {
                id4.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(3, false);
            }
        });

        id5.setOnClickListener(v -> {
            if(!IdentAnswer.get(4)) {
                id5.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(4, true);
            }
            else
            {
                id5.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(4, false);
            }
        });

        id6.setOnClickListener(v -> {
            if(!IdentAnswer.get(5)) {
                id6.setImageResource(R.drawable.ic_full_circle);
                IdentAnswer.set(5, true);
            }
            else
            {
                id6.setImageResource(R.drawable.ic_empty_circle);
                IdentAnswer.set(5, false);
            }
        });
    }

    private void SwitchIdentifier(boolean setTo)
    {
        if(!setTo) {
            idents.setAlpha(0f);
        }
        else {
            idents.setAlpha(1f);
        }
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}