package com.autonavi.zhtian.jplearn;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zhTian on 2/1/15.
 */






/*
test 2015-10-9
*/

public class JPFragment extends Fragment {
    private enum TEXTTYPE {
        Hiragana, katakana, bothOf
    }

    private Button mButton;
    private Button mButton_1;
    private Button mButton_2;
    private Button mButton_3;
    private TextView mTextView;
    private static final String ENCODING = "UTF-8";
    private static String s = null;
    private TEXTTYPE mTEXTTYPE = TEXTTYPE.Hiragana;
    private int length_of_string = 0;

    private ArrayList<String> mArrayList_1;
    private ArrayList<String> mArrayList_2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.jp_fragment, parent, false);

        mArrayList_1 = new ArrayList<String>();
        mArrayList_2 = new ArrayList<String>();

        s = ReadTxtFile();
        s = replaceBlank(s);
        length_of_string = s.length();
        for(int index = 0; index < length_of_string; index ++){
            char ch = s.charAt(index);
            String str = "" + ch;
            if(index%2==0){ //平假名
                mArrayList_1.add(str);
            }
            else{//片假名
                mArrayList_2.add(str);
            }
        }

        mTextView = (TextView)v.findViewById(R.id.jp_fragment_textView);
        mTextView.setText("" + s.substring(0,1));

        mButton_1 = (Button)v.findViewById(R.id.jp_fragment_button_1);
        mButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   mTEXTTYPE = TEXTTYPE.Hiragana;
            }
        });

        mButton_2 = (Button)v.findViewById(R.id.jp_fragment_button_2);
        mButton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTEXTTYPE = TEXTTYPE.katakana;
            }
        });

        mButton_3 = (Button)v.findViewById(R.id.jp_fragment_button_3);
        mButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTEXTTYPE = TEXTTYPE.bothOf;
            }
        });

        mButton = (Button)v.findViewById(R.id.jp_fragment_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change text view content

                int index = (int)(Math.random()*length_of_string/2.0);

                String delta;
                switch (mTEXTTYPE){
                    case Hiragana:{
                        delta = mArrayList_1.get(index);
                    }
                    break;
                    case katakana:{
                        delta = mArrayList_2.get(index);
                    }
                    break;
                    default:{
                        String a = mArrayList_1.get(index);
                        String b = mArrayList_2.get(index);
                        delta = a + "  " + b;
                    }
                    break;
                }

                mTextView.setText("" + delta);
            }
        });



        return v;
    }

    //读取文本文件中的内容
    public String ReadTxtFile()
    {
        String result = "";
        try {
            InputStream in = getResources().openRawResource(R.raw.jp_words);
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
