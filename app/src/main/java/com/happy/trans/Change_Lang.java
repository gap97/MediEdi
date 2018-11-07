package com.happy.trans;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Change_Lang extends AppCompatActivity {

    Button selectKorean;
    Button selectEnglish;
    Button selectThai;
    Button selectViet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_lang);

        selectKorean=(Button)findViewById(R.id.select_button_Korean);
        selectEnglish=(Button)findViewById(R.id.select_button_English);
        selectThai=(Button)findViewById(R.id.select_button_Thai);
        selectViet=(Button)findViewById(R.id.select_button_Viet);

        selectKorean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Change_Lang.this, Select_Function.class);
                intent.putExtra("lang","한국어");
                startActivity(intent);
            }
        });

        selectEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Change_Lang.this, Select_Function.class);
                intent.putExtra("lang","English");
                startActivity(intent);
            }
        });

        selectThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Change_Lang.this, Select_Function.class);
                intent.putExtra("lang","Thai");
                startActivity(intent);
            }
        });

        selectViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Change_Lang.this, Select_Function.class);
                intent.putExtra("lang","Viet");
                startActivity(intent);
            }
        });

    }


    }
