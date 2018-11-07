package com.happy.trans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    Button change_lang;
    Button change_disease;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Intent intent=new Intent(this.getIntent());
        final String lang=intent.getStringExtra("lang");

        change_lang=(Button)findViewById(R.id.button_change_lang);
        change_disease=(Button)findViewById(R.id.button_change_disease);
        TextView tvSetting=(TextView)findViewById(R.id.tvSetting);

        if(lang.equalsIgnoreCase("English")) {
            change_disease.setText("Set Highlight List");
            change_lang.setText("Language change");
            tvSetting.setText("Setting");
        }

        else if(lang.equalsIgnoreCase("Thai")) {
            change_disease.setText("การตั้งค่ารายการรายชื่อ");
            change_lang.setText("การเปลี่ยนภาษา");
            tvSetting.setText("การตั้งราคา");
        }

        else if(lang.equalsIgnoreCase("Viet")) {
            change_disease.setText("Lập danh sách nhấn mạnh");
            change_lang.setText("Thay đổi ngôn ngữ");
            tvSetting.setText("Sự thiết lập");
        }

        else {
            change_disease.setText("강조 목록 설정");
            change_lang.setText("언어변경");
            tvSetting.setText("환경설정");
        }


        change_lang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Setting.this, Change_Lang.class);
                startActivity(intent);
            }
        });

        change_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Setting.this, List.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });
    }
}