package com.happy.trans;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Adapter extends BaseAdapter
{
    LayoutInflater inflater = null;
    private ArrayList<List_Element> m_oData = null;
    private int nListCnt = 0;
    String lang;

    public List_Adapter(ArrayList<List_Element> _oData, String language)
    {
        m_oData = _oData;
        nListCnt = m_oData.size();
        lang=language;
    }

    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return nListCnt;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String choose;

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.list_element, parent, false);
        }

        if (lang.equalsIgnoreCase("English")) {
            choose="choice";
        }
        else if (lang.equalsIgnoreCase("Viet")) {
            choose="Sự lựa chọn";
        }
        else if (lang.equalsIgnoreCase("Thai")) {
            choose="เลือก";
        }
        else {
            choose="选择";
        }

        TextView element_name = (TextView) convertView.findViewById(R.id.element_name);
        ImageView element_image = (ImageView)convertView.findViewById(R.id.element_image);
        Button oBtn = (Button) convertView.findViewById(R.id.choose_medicine);

        element_name.setText(m_oData.get(position).medicine_name);
        element_image.setImageBitmap(m_oData.get(position).medicine_image);
        oBtn.setOnClickListener(m_oData.get(position).onClickListener);
        oBtn.setText(choose);

        convertView.setTag(""+position);

        return convertView;
    }
}