package com.happy.trans;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class List extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ArrayList<String> saved_items= getListFromLocal("emphasis_words");
        final ArrayList<String> items;

        if(saved_items.isEmpty()) items = new ArrayList<String>();
        else items=saved_items;

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
        final EditText edittext=(EditText)findViewById(R.id.editText);

        adapter.notifyDataSetChanged();

        Button addButton = (Button) findViewById(R.id.add);
        Button modifyButton = (Button) findViewById(R.id.modify);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button saveButton=(Button)findViewById(R.id.send);

        Intent intent=new Intent(this.getIntent());
        final String lang=intent.getStringExtra("lang");
        final String empty;
        final String overlap;


        if(lang.equalsIgnoreCase("English")) {
          addButton.setText("add");
          modifyButton.setText("modify");
          deleteButton.setText("delete");
          saveButton.setText("save");
          empty="Please enter an item to emphasize";
          overlap="There is already the same item.";
        }

        else if(lang.equalsIgnoreCase("Thai")) {
            addButton.setText("การเพิ่ม");
            modifyButton.setText("การแก้ไข");
            deleteButton.setText("การกำจัด");
            saveButton.setText("การเก็บ");
            empty="กรุณาระบุรายการที่จะเน้น";
            overlap="มีรายการเดียวกัน";
        }

        else if(lang.equalsIgnoreCase("Viet")) {
            addButton.setText("Cộng thêm");
            modifyButton.setText("Biên tập");
            deleteButton.setText("Sự loại bỏ");
            saveButton.setText("Lưu giữ");
            empty="Vui lòng nhập mục tiêu nhấn mạnh.";
            overlap="Đã có một hạng mục như vậy";
        }

        else {
            addButton.setText("추가");
            modifyButton.setText("수정");
            deleteButton.setText("삭제");
            saveButton.setText("저장");
            empty="강조할 항목을 입력해주세요";
            overlap="이미 같은 항목이 있습니다";
        }


        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String add;
                add=edittext.getText().toString();

                if(add.equalsIgnoreCase("")){
                    Toast.makeText(List.this, empty, Toast.LENGTH_SHORT).show();
                    edittext.requestFocus();
                    return;
                }

                for(int j=items.size()-1; j>=0; j--)
                if(items.get(j).equals(add)) {
                    Toast.makeText(List.this, overlap, Toast.LENGTH_SHORT).show();
                    edittext.requestFocus();
                    return;
                }

                items.add(add);

                adapter.notifyDataSetChanged();
            }
        });


        modifyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked;
                String edit;
                count = adapter.getCount();

                if (count > 0) {
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        edit=edittext.getText().toString();
                        items.set(checked, edit);
                        adapter.notifyDataSetChanged();
                    }
                }


            }
        });


        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked;
                count = adapter.getCount();

                if (count > 0) {

                    checked = listview.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        items.remove(checked);

                        listview.clearChoices();

                        adapter.notifyDataSetChanged();
                    }

                }

            }

        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> sending_items = items;
       saveListInLocal(sending_items, "emphasis_words");
            }
                });

    }

    public void saveListInLocal(ArrayList<String> list, String key) {

        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getListFromLocal(String key)
    {
        SharedPreferences prefs = getSharedPreferences("AppName", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);

    }

}