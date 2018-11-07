package com.happy.trans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SelectTranslation extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_translation);

        TextView select_translation=(TextView)findViewById(R.id.select_translation);
        Button name_search=(Button)findViewById(R.id.button_search_name);
        Button barcode_search=(Button)findViewById(R.id.button_search_barcode);
        TextView tv_name_search=(TextView)findViewById(R.id.search_name);
        TextView tv_barcode_search=(TextView)findViewById(R.id.search_barcode);

        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        final String lang = prefs.getString("lang", "English");

        if(lang.equalsIgnoreCase("English")) {
            tv_name_search.setText("Search for name");
            tv_barcode_search.setText("Search for barcode");
            select_translation.setText("Select Search Method");
        }

        else if(lang.equalsIgnoreCase("Thai")) {
            tv_name_search.setText("ค้นหาจากชื่อ");
            tv_barcode_search.setText("ค้นหาโดยรหัสอักขระ");
            select_translation.setText("เลือกวิธีการค้นหา");
        }

        else if(lang.equalsIgnoreCase("Viet")) {
            tv_name_search.setText("tìm kiếm bằng tên");
            tv_barcode_search.setText("tra cứu bằng Mã vạch");
            select_translation.setText("Cách tìm kiếm");
        }

        else {
            tv_name_search.setText("姓名检索");
            tv_barcode_search.setText("用条形码搜索");
            select_translation.setText("选择搜索方法");
        }


        name_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectTranslation.this, Medicine_List.class);
                startActivity(intent);
            }
        });

        barcode_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectTranslation.this, Barcode.class);
                startActivity(intent);
            }
        });

    }

}