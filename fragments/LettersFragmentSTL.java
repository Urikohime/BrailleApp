package com.example.abyteofbraille.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abyteofbraille.BrailleObject;
import com.example.abyteofbraille.R;
import com.example.abyteofbraille.methods.QuestionGenerator;

import java.util.Arrays;
import java.util.List;

public class LettersFragmentSTL extends Fragment {
    ImageButton dt1;
    ImageButton dt2;
    ImageButton dt3;
    ImageButton dt4;
    ImageButton dt5;
    ImageButton dt6;
    List<ImageButton> dotList;
    EditText answer;

    Button hintBtn;
    Button checkBtn;
    Button backBtn;
    Button nextBtn;
    BrailleObject Question;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_letters_stl, container, false);
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
        answer = view.findViewById(R.id.letter_input);

        hintBtn = view.findViewById(R.id.hint_letter_button);
        checkBtn = view.findViewById(R.id.check_letter_button);
        backBtn = view.findViewById(R.id.back_button);
        nextBtn = view.findViewById(R.id.next_question_button);

        if(nextBtn.getVisibility() == View.VISIBLE)
        {
            nextBtn.setVisibility(View.INVISIBLE);
            nextBtn.setClickable(false);
        }

        SetQuestionAndResetDots();

        backBtn.setOnClickListener(v -> NavHostFragment.findNavController(LettersFragmentSTL.this)
                .navigate(R.id.letters_to_home_stl));

        hintBtn.setOnClickListener(v -> ActivateHint());

        checkBtn.setOnClickListener(v -> CheckAnswer());

        nextBtn.setOnClickListener(v -> {
            answer.setText("");
            answer.setHint(R.string.answer);
            new Thread(this::SetQuestionAndResetDots).start();
        });

        answer.setOnFocusChangeListener((v, hasFocus) -> {
            ConstraintSet constraintSet = new ConstraintSet();
            ConstraintLayout constraintLayout = view.findViewById(R.id.constrainer_letters_stl);
            constraintSet.clone(constraintLayout);
            if(hasFocus)
            {
                constraintSet.clear(R.id.letters_main_stl, ConstraintSet.BOTTOM);
            }
            else
            {
                constraintSet.connect(R.id.letters_main_stl, ConstraintSet.BOTTOM, R.id.constrainer_letters_stl, ConstraintSet.BOTTOM, 0);
            }
            constraintSet.applyTo(constraintLayout);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetQuestionAndResetDots()
    {
        new Thread(QuestionGenerator::createDbImage).start();
        Question = QuestionGenerator.getQuestionLetter();

        answer.setClickable(true);
        checkBtn.setClickable(true);
        hintBtn.setClickable(true);
        nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setClickable(false);
        for(ImageButton IB: dotList)
        {
            IB.setImageResource(R.drawable.ic_empty_circle);
        }

        for(int i: QuestionGenerator.Hint(Question))
        {
            dotList.get(i).setImageResource(R.drawable.ic_full_circle);
        }
    }

    private void ActivateHint()
    {
        answer.setHint(Question.GetSymbol());
        hintBtn.setClickable(false);
    }

    private void CheckAnswer()
    {
        if(QuestionGenerator.CheckAnswerSTL(Question, answer.getText().toString()))
        {
            nextBtn.setVisibility(View.VISIBLE);
            nextBtn.setClickable(true);
            checkBtn.setTextColor(Color.BLACK);
            checkBtn.setClickable(false);
            hintBtn.setClickable(false);
            answer.setClickable(false);
        }
        else
        {
            checkBtn.setTextColor(Color.RED);
        }
    }
}