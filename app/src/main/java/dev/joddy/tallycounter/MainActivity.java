/*
 * Copyright (c) joddy.dev 2022
 * All Rights Reserved
 */
package dev.joddy.tallycounter;

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

    Button btnAdd;
    Button btnRemove;
    TextView tallyText;

    private int tallyCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRemove = (Button) findViewById(R.id.btnRemove);
        tallyText = (TextView) findViewById(R.id.txtTally);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onTally(v,TallyType.ADD);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onTally(v,TallyType.REMOVE);
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

    public void onTally(View v, TallyType type)
    {
        switch (type) {
            case ADD:
                tallyText.setText(Integer.toString(++tallyCounter));
                break;
            case REMOVE:
                if (tallyCounter > 0) tallyText.setText(Integer.toString(--tallyCounter));
                else Toast.makeText(this, "Tally cannot be less than 0", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void clearTally(View v) {
        tallyCounter = 0;
        tallyText.setText("0");
        Toast.makeText(this, "Cleared the tally", Toast.LENGTH_SHORT).show();
    }
}