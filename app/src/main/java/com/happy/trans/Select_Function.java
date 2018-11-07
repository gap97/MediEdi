package com.happy.trans;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Select_Function extends AppCompatActivity {

    Button selectTranslation;
    Button selectHospital;
    Button selectLink;
    Button selectSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_function);

        selectTranslation=(Button)findViewById(R.id.select_button_translation);
        selectHospital=(Button)findViewById(R.id.select_button_hospital);
        selectLink=(Button)findViewById(R.id.select_button_link);
        selectSetting=(Button)findViewById(R.id.select_button_setting);

        Intent intent=new Intent(this.getIntent());
        final String s=intent.getStringExtra("lang");
        TextView textView=(TextView)findViewById(R.id.selected_lang);
        textView.setText(s);

        if(s.equalsIgnoreCase("English")) {
            selectSetting.setText("Setting");
            selectTranslation.setText("Medical translation");
            selectLink.setText("Link for information");
            selectHospital.setText("Locate hospital");
        }

        else if(s.equalsIgnoreCase("Thai")) {
            selectSetting.setText("การตั้งค่าสิ่งแวดล้อม");
            selectTranslation.setText("การแปลยา");
            selectLink.setText("เว็บไซต์");
            selectHospital.setText("การหาตำแหน่งในโรงพยาบาล");
        }

        else if(s.equalsIgnoreCase("Viet")) {
            selectSetting.setText("Thiết lập hoàn cảnh");
            selectTranslation.setText(" phiên dịch");
            selectLink.setText("Trang web");
            selectHospital.setText("Tìm vị trí của bệnh viện");
        }

        else {
            selectSetting.setText("환경설정");
            selectTranslation.setText("의약품 번역");
            selectLink.setText("다문화 가정 링크");
            selectHospital.setText("병원 위치 찾기");
        }

        selectTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected_lang=s;
                Intent intent=new Intent(Select_Function.this, Translation.class);
                intent.putExtra("lang",selected_lang);
                startActivity(intent);
            }
        });

        selectSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Select_Function.this, Setting.class);
                intent.putExtra("lang", s);
                startActivity(intent);
            }
        });
    }
    }
