package com.happy.trans;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translation extends AppCompatActivity {

    EditText etSource;
    TextView tvResult;
    TextView tvOriginal;
    Button btSearch;
    Button btEmphasis;
    String translate_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation);

        etSource = (EditText) findViewById(R.id.medicine);
        tvResult = (TextView) findViewById(R.id.result_translation3);
        tvOriginal= (TextView)findViewById(R.id.original);
        btEmphasis = (Button)findViewById(R.id.button_emphasis);
        btSearch=(Button)findViewById(R.id.search2);

        Intent intent=new Intent(this.getIntent());
        final String s=intent.getStringExtra("lang");

        btEmphasis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Intent intent=new Intent(Translation.this, SelectSearch.class);
                intent.putExtra("translate_result",translate_result);
                intent.putExtra("lang", s);
                startActivity(intent);
            }
        });

                final String target_lang;

                if(s.equalsIgnoreCase("English")) {
                    target_lang="en";
                btSearch.setText("search");
                btEmphasis.setText("emphasis");
                etSource.setHint("Please enter the name of the medication to search for");
                }

                else if(s.equalsIgnoreCase("Thai")) {
                    target_lang="th";
                    btSearch.setText("การสืบค้น");
                    btEmphasis.setText("การเน้นย้ำ");
                    etSource.setHint("กรุณากรอกชื่อผลิตภัณฑ์เพื่อค้นหาค่ะ");
                }

                else if(s.equalsIgnoreCase("Viet")) {
                    target_lang="vi";
                    btSearch.setText("Tìm kiếm");
                    btEmphasis.setText("Sự nhấn mạnh");
                    etSource.setHint("Hãy nhập tên thuốc loại thuốc để tìm kiếm.");
                }

                else {
                    target_lang="ko";
                    btSearch.setText("검색");
                    btEmphasis.setText("강조");
                    etSource.setHint("검색할 의약품명을 입력하세요.");
                }


        btSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (etSource.getText().toString().length() == 0) {
                    Toast.makeText(Translation.this, "약이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    etSource.requestFocus();
                    return;
                }

                NaverTranslateTask asyncTask = new NaverTranslateTask();
                String medicine_name = etSource.getText().toString();
//                String medicine_name="타이레놀";
                String sText = Medicine.searchMedicine(medicine_name);
                tvOriginal.setText(sText);
                asyncTask.targetLang = target_lang;
                asyncTask.execute(sText);
            }
        });

    }

public class NaverTranslateTask extends AsyncTask<String, Void, String> {

    public String resultText;
    //Naver
    String clientId = "7suMEiClPpJC5VZU38ri";//애플리케이션클라이언트아이디값";
    String clientSecret = "OT6M8kt7Rt";
    String sourceLang = "ko";
    String targetLang;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //AsyncTask 메인처리
    @Override
    protected String doInBackground(String... strings) {
        String sourceText = strings[0];

        try {
            String text = URLEncoder.encode(sourceText, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source="+sourceLang+"&target="+targetLang+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString());
            return response.toString();

        } catch (Exception e) {
            Log.d("error", e.getMessage());
            return null;
        }
    }

    //번역된 결과를 받아서 처리
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Gson gson = new GsonBuilder().create();
        JsonParser parser = new JsonParser();
        JsonElement rootObj = parser.parse(s.toString())
                .getAsJsonObject().get("message")
                .getAsJsonObject().get("result");
        TranslatedItem items = gson.fromJson(rootObj.toString(), TranslatedItem.class);
        translate_result=items.getTranslatedText();
        tvResult.setText(translate_result);
    }


    private class TranslatedItem {
        String translatedText;

        public String getTranslatedText() {
            return translatedText;
        }
    }
}

}