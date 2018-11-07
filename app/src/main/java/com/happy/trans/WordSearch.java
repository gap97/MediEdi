package com.happy.trans;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WordSearch extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_search);

        Intent intent=new Intent(this.getIntent());
        final String emphasis1 = intent.getStringExtra("translate_result");
        String lang=intent.getStringExtra("lang");

        TextView result_Translate=(TextView)findViewById(R.id.result_translate1);
        result_Translate.setText(emphasis1);

        Button search_button=(Button)findViewById(R.id.search1);

        if(lang.equalsIgnoreCase("English")) {
            search_button.setText("search");
        }

        else if(lang.equalsIgnoreCase("Thai")) {
            search_button.setText("การสืบค้น");
        }

        else if(lang.equalsIgnoreCase("Viet")) {
            search_button.setText("Tìm kiếm");
        }

        else {
            search_button.setText("검색");
        }

        search_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                TextView result_Emphasis=(TextView)findViewById(R.id.result_emphasis1);
                EditText edit_text=(EditText)findViewById(R.id.edit_text1);
                String emphasis=emphasis1;
                String emphasis_word=edit_text.getText().toString();

                emphasis =Emphasis(emphasis, emphasis_word);

                result_Emphasis.setText(Html.fromHtml(emphasis, 1));
            }

        });
    }

    public String Emphasis(String resource, String word_emphasis) {
        String start="<b>";
        String end="</b>";
        String result=resource.replaceAll(word_emphasis, start+word_emphasis+end);
        return result;
    }

}
