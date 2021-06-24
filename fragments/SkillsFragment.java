package com.example.abyteofbraille.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abyteofbraille.R;
import com.example.abyteofbraille.methods.QuestionGenerator;
import com.example.abyteofbraille.methods.Skill;

import java.util.List;

import static android.view.Gravity.LEFT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SkillsFragment extends Fragment {
    static List<Skill> dbImage;
    TableLayout skill_table;
    LinearLayout SL;
    Button backBtn;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        new Thread(SkillsFragment::getSkillTable).start();
        return inflater.inflate(R.layout.fragment_skills, container, false);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SL = view.findViewById(R.id.skill_list);
        backBtn = view.findViewById(R.id.back_button_skills);

        skill_table = new TableLayout(getContextThis());
        skill_table.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 2));
        skill_table.getLayoutParams().width = MATCH_PARENT;
        skill_table.getLayoutParams().height = WRAP_CONTENT;
        skill_table.requestLayout();

        for(Skill s: dbImage)
        {
            ImageView divider = new ImageView(getContextThis());
            divider.setBackgroundColor(Color.BLACK);
            divider.requestLayout();
            divider.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 2));
            divider.getLayoutParams().width = MATCH_PARENT;
            divider.getLayoutParams().height = 2;
            divider.requestLayout();

            TextView sym = new TextView(getContextThis());
            sym.setText(s.symbol);
            sym.setGravity(LEFT);
            sym.setTextSize(30);
            sym.setAllCaps(true);
            sym.setPadding(1, 1, 25, 1);

            TableRow symbols = new TableRow(getContextThis());
            symbols.addView(sym);

            System.out.println(s.symbol + " / " + s.skill);
            ProgressBar prog = new ProgressBar(getContextThis(), null, android.R.attr.progressBarStyleHorizontal);
            prog.setLayoutParams(new TableRow.LayoutParams(800, MATCH_PARENT));
            prog.setProgress((s.skill));
            symbols.addView(prog);

            symbols.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            symbols.getLayoutParams().width = MATCH_PARENT;
            symbols.setPadding(38, 16, 38, 2);
            symbols.setBackgroundColor(40000000);
            skill_table.addView(symbols);
            skill_table.addView(divider);
        }
        skill_table.setPadding(24, 24, 24, 24);
        SL.addView(skill_table);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SkillsFragment.this)
                        .navigate(R.id.skills_to_home);
            }
        });
    }

    private static void getSkillTable()
    {
        dbImage = QuestionGenerator.db.getAll();
    }

    private Context getContextThis()
    {
        return this.getContext();
    }
}
