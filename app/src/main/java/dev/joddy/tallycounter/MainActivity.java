/*
 * Copyright (c) joddy.dev 2022
 * All Rights Reserved
 */
package dev.joddy.tallycounter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum TallyType {
        ADD,
        REMOVE,
    }

    int[] AddButtonColors = new int[] {R.drawable.green_button_background, R.drawable.green_button_background_2, R.drawable.green_button_background_3};
    int[] RemoveButtonColors = new int[] {R.drawable.red_button_background, R.drawable.red_button_background_2, R.drawable.red_button_background_3};

    String[] ImageIcons = new String[] {
            "https://img.icons8.com/external-vitaliy-gorbachev-lineal-color-vitaly-gorbachev/452/external-numbers-support-vitaliy-gorbachev-lineal-color-vitaly-gorbachev.png",
            "https://img.icons8.com/color-glass/452/123.png",
            "https://img.icons8.com/stickers/452/123.png"
    };

    private Button btnAdd;
    private Button btnRemove;
    private TextView tallyText;
    private Activity activity;

    private int tallyCounter = 0;
    private int clickActionNumber = 0; // Used for button colors and icon moves
    private int textSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        tallyText = (TextView) findViewById(R.id.txtTally);
        activity = this;

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tallyAction(TallyType.ADD);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tallyAction(TallyType.REMOVE);
            }
        });

        btnRemove.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearTally(v);
                return true;
            }
        });
    }

    public void tallyAction(TallyType type)
    {
        switch (type) {
            case ADD:
                if (tallyCounter+1 > Integer.MAX_VALUE) {
                    Toast.makeText(this, "Congratulations, you have reached the integer limit", Toast.LENGTH_SHORT).show();
                    return;
                }
                tallyText.setText(Integer.toString(++tallyCounter));
                doTextEffect(type);
                changeButtonColor(type);
                break;
            case REMOVE:
                if (tallyCounter < 1) return;
                tallyText.setText(Integer.toString(--tallyCounter));
                doTextEffect(type);
                changeButtonColor(type);
                break;
        }
    }

    public void changeButtonColor(TallyType type) {
        switch (type) {
            case ADD:
                btnAdd.setBackgroundResource(getNextBtnColorId(type));
                break;
            case REMOVE:
                btnRemove.setBackgroundResource(getNextBtnColorId(type));
                break;
        }
    }

    private int getNextBtnColorId(TallyType tallyType) throws IllegalArgumentException {
        if (tallyType.equals(TallyType.ADD)) {
            if (clickActionNumber < 1 || clickActionNumber > 1) clickActionNumber = -1;
            return AddButtonColors[++clickActionNumber];
        }

        if (tallyType.equals(TallyType.REMOVE)) {
            if (clickActionNumber < 1 || clickActionNumber > 1) clickActionNumber = -1;
            return RemoveButtonColors[++clickActionNumber];
        }

        throw new IllegalArgumentException("Invalid tally type");
    }

    private void doTextEffect(TallyType type) {
        // Color Animation
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tallyText, "textColor",
                type == TallyType.ADD ? Color.GREEN : Color.RED , Color.BLACK);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();

        // Font Size
        tallyText.setTextSize(Utils.dip2px(activity, type == TallyType.ADD ? textSize+1 > 26 ? 26 : ++textSize : textSize <= 16 ? 16 : --textSize ));
    }

    private void doMovingIconActions() {
        changeMovingIcon();
        moveMovingIcon();
    }

    private void changeMovingIcon() {

    }

    private void moveMovingIcon() {

    }


    public void clearTally(View v) {
        tallyCounter = 0;
        tallyText.setText("0");
        tallyText.setTextSize(Utils.dip2px(this,16));
        textSize = 16;
        Toast.makeText(this, "Cleared the tally", Toast.LENGTH_SHORT).show();
    }


}