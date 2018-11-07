package com.happy.trans;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Medicine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static String searchMedicine(String drug_code){


        String drug_info = null;
        Log.d("drug_code",drug_code);

        try {
            String searchUrl = "http://localapi.health.kr:8090/result_drug.localapi?drug_cd=" +
                    URLEncoder.encode(drug_code,"UTF-8") + "&callback=";
            drug_info = new getData().execute(searchUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return drug_info;
    }

    private static class getData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... search) {
            String contents = "null";
            boolean check = FALSE;

            try {
                ConnectServer connectServer = new ConnectServer();
                contents = connectServer.requestGet(search[0]);
                check = TRUE;

                Log.d("RRRRRR", contents.toString());
            } catch (IOException e) {
                Log.d("RR", e.toString());
            }
            if (!check) return "존재하지 않는 결과입니다.";

            StringBuffer jsonData = new StringBuffer();

            try {
                JSONArray jarray = new JSONArray(contents);
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = null;
                    jObject = jarray.getJSONObject(i);

                    String ingredient = jObject.getString("sunb");
                    while (ingredient.indexOf("<") != -1)
                        ingredient = ingredient.indexOf("<") <= 1 ? ingredient.substring(ingredient.indexOf(">") + 1, ingredient.length()) :
                                ingredient.substring(0, ingredient.indexOf("<") - 1) + ingredient.substring(ingredient.indexOf(">") + 1, ingredient.length());
                    ingredient = ingredient.replaceAll("@", "\n");
                    String extraeffect = jObject.getString("medititle");
                    if (extraeffect.indexOf("[") != -1)
                        extraeffect = extraeffect.substring(extraeffect.indexOf("]") + 1, extraeffect.length());
                    extraeffect = extraeffect.replaceAll("br", "\n");
                    String effect = jObject.getString("effect");
                    if (effect.indexOf("[") != -1)
                        effect = effect.substring(effect.indexOf("]") + 1, effect.length());
                    effect = effect.replaceAll("br", "\n");
                    String dosage = jObject.getString("dosage");
                    if (dosage.indexOf("[") != -1)
                        dosage = dosage.substring(dosage.indexOf("]") + 1, dosage.length());
                    while (dosage.indexOf("<") != -1)
                        dosage = dosage.indexOf("<") <= 1 ? dosage.substring(dosage.indexOf(">") + 1, dosage.length()) :
                                dosage.substring(0, dosage.indexOf("<") - 1) + dosage.substring(dosage.indexOf(">") + 1, dosage.length());
                    dosage = dosage.replaceAll("br", "\n");
                    String caution = jObject.getString("caution");
                    String guide = jObject.getString("mediguide");
                    while (guide.indexOf("<") != -1)
                        guide = guide.indexOf("<") <= 1 ? guide.substring(guide.indexOf(">") + 1, guide.length()) :
                                guide.substring(0, guide.indexOf("<") - 1) + guide.substring(guide.indexOf(">") + 1, guide.length());
                    guide = guide.replaceAll("br", "\n");

                    jsonData.append("성분 : " + ingredient + "\n효능,효과 : " + effect + "\n" + extraeffect +
                            "\n\n용법용량 : " + dosage + "\n\n복약정보\n" + guide );
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return jsonData.toString();
        }
    }
}
