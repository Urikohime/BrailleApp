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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abyteofbraille.BrailleObject;
import com.example.abyteofbraille.R;
import com.example.abyteofbraille.methods.QuestionGenerator;

import java.util.Arrays;
import java.util.List;

public class LettersFragmentLTS extends Fragment {
    List<Boolean> Answer = Arrays.asList(false, false, false, false, false, false);
    ImageButton dt1;
    ImageButton dt2;
    ImageButton dt3;
    ImageButton dt4;
    ImageButton dt5;
    ImageButton dt6;
    List<ImageButton> dotList;

    Button hintBtn;
    Button checkBtn;
    Button backBtn;
    Button nextBtn;
    TextView letterView;
    BrailleObject Question;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_letters_lts, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dt1 = view.findViewById(R.id.dot_1);
        dt2 = view.findViewById(R.id.dot_2);
        dt3 = view.findViewById(R.id.dot_3);
        dt4 = view.findViewById(R.id.dot_4);
        dt5 = view.findViewById(R.id.dot_5);
        dt6 = view.findViewById(R.id.dot_6);
        dotList = Arrays.asList(dt1, dt2, dt3, dt4, dt5, dt6);

        hintBtn = view.findViewById(R.id.hint_letter_button);
        checkBtn = view.findViewById(R.id.check_letter_button);
        backBtn = view.findViewById(R.id.back_button);
        nextBtn = view.findViewById(R.id.next_question_button);
        letterView = view.findViewById(R.id.letter_view);

        if(nextBtn.getVisibility() == View.VISIBLE)
        {
            nextBtn.setVisibility(View.INVISIBLE);
            nextBtn.setClickable(false);
        }

        initiateDots(view, savedInstanceState);
        SetQuestionAndResetDots();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LettersFragmentLTS.this)
                        .navigate(R.id.letters_to_home_lts);
            }
        });

        hintBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ActivateHint();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAnswer();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                new Thread(() -> SetQuestionAndResetDots()).start();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetQuestionAndResetDots()
    {
        new Thread(QuestionGenerator::createDbImage).start();
        Question = QuestionGenerator.getQuestionLetter();

        letterView.setText(Question.GetSymbol());
        checkBtn.setClickable(true);
        hintBtn.setClickable(true);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setClickable(false);
        for(int i = 0; i < dotList.size(); i++)
        {
            dotList.get(i).setImageResource(R.drawable.ic_empty_circle);
            Answer.set(i, false);
            dotList.get(i).setClickable(true);
            dotList.get(i).setAlpha(1f);
        }

    }

    private void ActivateHint()
    {
        for(ImageButton IB: dotList) { IB.setAlpha(0.1f); }
        for(int i: QuestionGenerator.Hint(Question))
        {
            dotList.get(i).setAlpha(1f);
        }
        hintBtn.setClickable(false);
    }

    private void CheckAnswer()
    {
        if(QuestionGenerator.CheckAnswerLTS(Question, Answer))
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
        else
        {
            checkBtn.setTextColor(Color.RED);
        }
    }

    private void initiateDots(@NonNull View view, Bundle savedInstanceState)
    {
        dt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(0)) {
                    dt1.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(0, true);
                }
                else
                {
                    dt1.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(0, false);
                }
            }
        });

        dt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(1)) {
                    dt2.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(1, true);
                }
                else
                {
                    dt2.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(1, false);
                }
            }
        });

        dt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(2)) {
                    dt3.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(2, true);
                }
                else
                {
                    dt3.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(2, false);
                }
            }
        });

        dt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(3)) {
                    dt4.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(3, true);
                }
                else
                {
                    dt4.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(3, false);
                }
            }
        });

        dt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(4)) {
                    dt5.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(4, true);
                }
                else
                {
                    dt5.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(4, false);
                }
            }
        });

        dt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Answer.get(5)) {
                    dt6.setImageResource(R.drawable.ic_full_circle);
                    Answer.set(5, true);
                }
                else
                {
                    dt6.setImageResource(R.drawable.ic_empty_circle);
                    Answer.set(5, false);
                }
            }
        });
    }
}