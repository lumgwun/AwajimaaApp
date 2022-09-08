package com.skylightapp.MarketDealFrags;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.skylightapp.Classes.App;
import com.skylightapp.Classes.AppController;
import com.skylightapp.MarketClasses.BaseAsyncTask;
import com.skylightapp.MarketClasses.MyPreferences;
import com.skylightapp.MarketClasses.PersonalityTrait;
import com.skylightapp.MarketClasses.TestRequest;
import com.skylightapp.MarketInterfaces.ServerMethodsConsts;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class TestFragment extends Fragment {
    private ScrollView scrollView;
    private SeekBar SliderQ1;
    private SeekBar SliderQ2;
    private SeekBar SliderQ3;
    private SeekBar SliderQ4;
    private SeekBar SliderQ5;
    private SeekBar SliderQ6;
    private SeekBar[] sliders;
    private StateProgressBar pageProgressBar;

    private TextView[] textViews;
    private PersonalityTrait[] personalityTraits;
    String[] allQuestions;
    private ArrayList<Integer> shuffledQuestionIndexes;

    private int numberOfScreens;
    private int actualScreen;
    private int numberOfQuestionsPerPage;
    private float range;
    private MyPreferences myPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        myPreferences = App.getPreferences();

        range = 120;
        actualScreen = 0;
        numberOfScreens = 4;
        numberOfQuestionsPerPage = 6;
        allQuestions = new String[numberOfScreens * numberOfQuestionsPerPage];
        shuffledQuestionIndexes = new ArrayList<>();

        for (int i = 0; i < allQuestions.length; i++)
            shuffledQuestionIndexes.add(i + 1);

        Collections.shuffle(shuffledQuestionIndexes);

        scrollView = (ScrollView) view.findViewById(R.id.test_container_scrollView);
        SliderQ1 = (SeekBar) view.findViewById(R.id.sliderQ1);
        SliderQ2 = (SeekBar) view.findViewById(R.id.sliderQ2);
        SliderQ3 = (SeekBar) view.findViewById(R.id.sliderQ3);
        SliderQ4 = (SeekBar) view.findViewById(R.id.sliderQ4);
        SliderQ5 = (SeekBar) view.findViewById(R.id.sliderQ5);
        SliderQ6 = (SeekBar) view.findViewById(R.id.sliderQ6);
        sliders = new SeekBar[]{SliderQ1, SliderQ2, SliderQ3, SliderQ4, SliderQ5, SliderQ6};
        for (SeekBar s : sliders) {
            s.setOnSeekBarChangeListener(seekBarChangeListener);
            s.setProgress(51);
            s.setProgress(50);
        }

        TextView textQ1 = (TextView) view.findViewById(R.id.TextQ1);
        TextView textQ2 = (TextView) view.findViewById(R.id.TextQ2);
        TextView textQ3 = (TextView) view.findViewById(R.id.TextQ3);
        TextView textQ4 = (TextView) view.findViewById(R.id.TextQ4);
        TextView textQ5 = (TextView) view.findViewById(R.id.TextQ5);
        TextView textQ6 = (TextView) view.findViewById(R.id.TextQ6);
        textViews = new TextView[]{textQ1, textQ2, textQ3, textQ4, textQ5, textQ6};

        Button nextQuestions = (Button) view.findViewById(R.id.nextQuestions);
        nextQuestions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawQuestions();
            }
        });

        pageProgressBar = (StateProgressBar) view.findViewById(R.id.pageProgressBar);

        String[] questionsJ = new String[4];
        questionsJ[0] = "I create to-do list and stick to it";
        questionsJ[1] = "I focus on one thing at a time";
        questionsJ[2] = "My work is methodical and organized";
        questionsJ[3] = "I don't like unexpected events";
        int[] numbers = new int[]{1, 2, 3, 4};
        System.arraycopy(questionsJ, 0, allQuestions, 0, questionsJ.length);
        PersonalityTrait JTrait = new PersonalityTrait("J", questionsJ, numbers);

        String[] questionsP = new String[2];
        questionsP[0] = "I am most effective when I complete my tasks at the last minute";
        questionsP[1] = "I often make decisions impulsively";
        numbers = new int[]{5, 6};
        System.arraycopy(questionsP, 0, allQuestions, 4, questionsP.length);
        PersonalityTrait PTrait = new PersonalityTrait("P", questionsP, numbers);


        String[] questionsN = new String[3];
        questionsN[0] = "I live more in my head than in the real world";
        questionsN[1] = "Fantasizing often give more joy than real sensations";
        questionsN[2] = "I prefer to invent new ways to solve problems, than using a proven ones";
        numbers = new int[]{7, 8, 9};
        System.arraycopy(questionsN, 0, allQuestions, 6, questionsN.length);
        PersonalityTrait NTrait = new PersonalityTrait("N", questionsN, numbers);
        NTrait.setScore(40);

        String[] questionsS = new String[3];
        questionsS[0] = "I keep my feet firmly on the ground";
        questionsS[1] = "I prefer to focus on reality than indulge in fantasies";
        questionsS[2] = "Psychical activity is more enjoyable than mental one";
        numbers = new int[]{10, 11, 12};
        System.arraycopy(questionsS, 0, allQuestions, 9, questionsS.length);
        PersonalityTrait STrait = new PersonalityTrait("S", questionsS, numbers);
        STrait.setScore(60);

        String[] questionsE = {
                "It is not difficult for me to talk about my problems",
                "I like being the center of attention",
                "I easily make new friendships",
                "I start conversations often"};
        numbers = new int[]{13, 14, 15, 16};
        System.arraycopy(questionsE, 0, allQuestions, 12, questionsE.length);
        PersonalityTrait ETrait = new PersonalityTrait("E", questionsE, numbers);

        String[] questionsI = new String[2];
        questionsI[0] = "I like to go for lonely walks away from the hustle and bustle";
        questionsI[1] = "I prefer to listen to the discussion than to participate in it";
        numbers = new int[]{17, 18};
        System.arraycopy(questionsI, 0, allQuestions, 16, questionsI.length);
        PersonalityTrait ITrait = new PersonalityTrait("I", questionsI, numbers);


        String[] questionsF = new String[3];
        questionsF[0] = "I avoid arguing, even if I know I'm right";
        questionsF[1] = "Subjective feelings have a big influence on my decisions";
        questionsF[2] = "I have no problem expressing my feelings";
        numbers = new int[]{19, 20, 21};
        System.arraycopy(questionsF, 0, allQuestions, 18, questionsF.length);
        PersonalityTrait FTrait = new PersonalityTrait("F", questionsF, numbers);

        String[] questionsT = new String[3];
        questionsT[0] = "I'd rather be seen as rude than illogical";
        questionsT[1] = "I believe logical solutions are always the best";
        questionsT[2] = "I am straightforward, even if it can hurt somebody";
        numbers = new int[]{22, 23, 24};
        System.arraycopy(questionsT, 0, allQuestions, 21, questionsT.length);
        PersonalityTrait TTrait = new PersonalityTrait("T", questionsT, numbers);

        personalityTraits = new PersonalityTrait[]{ETrait, ITrait, NTrait, STrait, TTrait, JTrait, FTrait, PTrait};
        drawQuestions();
        return view;
    }


    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double barProgress = seekBar.getProgress();
            float max = (float) seekBar.getMax();
            float h = 15 + (float) ((max / range) * barProgress);
            float s = 100;
            float v = 90;
            String hexColor = hsvToRgb(h, s, v);
            //String hexColor = String.format("#%06X", (0xFFFFFF & color));
            seekBar.getProgressDrawable().setColorFilter(Color.parseColor(hexColor), PorterDuff.Mode.SRC_IN);
            seekBar.getThumb().setColorFilter(Color.parseColor(hexColor), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void saveAnswers() {
        for (int i = 0; i < numberOfQuestionsPerPage; i++) {
            for (PersonalityTrait temp : personalityTraits) {
                if (temp.containsNumber(shuffledQuestionIndexes.get(numberOfQuestionsPerPage * (actualScreen - 1) + i))) {
                    temp.saveScore(shuffledQuestionIndexes.get(numberOfQuestionsPerPage * (actualScreen - 1) + i), Math.round(sliders[i].getProgress()));
                    break;
                }
            }
        }
    }

    public void drawQuestions() {

        if (actualScreen < numberOfScreens) {

            if (actualScreen > 0)
                saveAnswers();
            actualScreen++;
            switch (actualScreen) {
                case 2:
                    pageProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                    break;
                case 3:
                    pageProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                    break;
                case 4:
                    pageProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    break;
                default:
                    pageProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            }
            SliderQ1.setProgress(50);
            SliderQ2.setProgress(50);
            SliderQ3.setProgress(50);
            SliderQ4.setProgress(50);
            SliderQ5.setProgress(50);
            SliderQ6.setProgress(50);

            for (int i = 0; i < numberOfQuestionsPerPage; i++) {
                textViews[i].setText(allQuestions[shuffledQuestionIndexes.get(numberOfQuestionsPerPage * (actualScreen - 1) + i) - 1]);
            }
            scrollView.scrollTo(0, 0);
        } else {
            saveAnswers();

            HashMap<String, String> answers = new HashMap<>();

            for (PersonalityTrait tr : personalityTraits) {
                for (int i = 0; i < tr.getNumbersOfQuestions().length; i++) {
                    answers.put("q" + tr.getNumbersOfQuestions()[i], String.valueOf(tr.getAnswerPoints()[i]));
                }
            }
            TestRequest testRequest = new TestRequest(myPreferences.getUserId(), 24, answers);
            BaseAsyncTask<TestRequest> sendResults = new BaseAsyncTask<>(ServerMethodsConsts.TEST, testRequest);
            sendResults.setHttpMethod("POST");
            sendResults.execute();

            showResults();
        }
    }

    private void showResults() {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, new TestResultsFragment());
        ft.commit();
    }

    public static String hsvToRgb(float H, float S, float V) {

        float R, G, B;
        H /= 360f;
        S /= 100f;
        V /= 100f;

        if (S == 0) {
            R = V * 255;
            G = V * 255;
            B = V * 255;
        } else {
            float var_h = H * 6;
            if (var_h == 6)
                var_h = 0; // H must be < 1
            int var_i = (int) Math.floor((double) var_h); // Or ... var_i =
            // floor( var_h )
            float var_1 = V * (1 - S);
            float var_2 = V * (1 - S * (var_h - var_i));
            float var_3 = V * (1 - S * (1 - (var_h - var_i)));

            float var_r;
            float var_g;
            float var_b;
            if (var_i == 0) {
                var_r = V;
                var_g = var_3;
                var_b = var_1;
            } else if (var_i == 1) {
                var_r = var_2;
                var_g = V;
                var_b = var_1;
            } else if (var_i == 2) {
                var_r = var_1;
                var_g = V;
                var_b = var_3;
            } else if (var_i == 3) {
                var_r = var_1;
                var_g = var_2;
                var_b = V;
            } else if (var_i == 4) {
                var_r = var_3;
                var_g = var_1;
                var_b = V;
            } else {
                var_r = V;
                var_g = var_1;
                var_b = var_2;
            }

            R = var_r * 255;
            G = var_g * 255;
            B = var_b * 255;
        }
        String rs = Integer.toHexString((int) (R));
        String gs = Integer.toHexString((int) (G));
        String bs = Integer.toHexString((int) (B));

        if (rs.length() == 1)
            rs = "0" + rs;
        if (gs.length() == 1)
            gs = "0" + gs;
        if (bs.length() == 1)
            bs = "0" + bs;
        return "#" + rs + gs + bs;
    }
}
