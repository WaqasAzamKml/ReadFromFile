package com.readfromfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button btnRead, btnScan;
    TextView tvText;
//    EditText etScanString;
    ListView lvShortCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRead = (Button) findViewById(R.id.btnReadText);
        btnScan = (Button) findViewById(R.id.btnScanText);
        tvText = (TextView) findViewById(R.id.tvText);
//        etScanString = (EditText) findViewById(R.id.etScanString);
        lvShortCodes = (ListView) findViewById(R.id.lvShortCodes);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readText();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findShortCodes();

            }
        });
    }

    public void readText(){

        BufferedReader reader = null;
        StringBuilder text = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("wp_tips.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                text.append(mLine);
                text.append('\n');
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        tvText.setText(text);

    }

    public void findShortCodes(){
        String myString = tvText.getText().toString();
//                Pattern myPattern = Pattern.compile("'(.*?)'"); // regular expression for any text contained inside single quotes ''
        Pattern myPattern = Pattern.compile("\\{(.*?)\\}"); // regular expression for any text contained inside curly brackets{}
        // brackets are escaped using backslashes \ but backslash itself is a special character in java's string
        // so it is also escaped by backslash, making it a double backslash \\

        Matcher matcher = myPattern.matcher(myString);

        //This if condition finds only first occurrence of the provided pattern's match.
//                if(matcher.find()){
//                    Toast.makeText(MainActivity.this, matcher.group(), Toast.LENGTH_SHORT).show();
//                }

        //This loops through the input string and finds all occurrences of the provided pattern.
        while(matcher.find()){
            Toast.makeText(MainActivity.this, String.valueOf(matcher.start()) + " value " + matcher.group() + String.valueOf(matcher.end()), Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, , Toast.LENGTH_SHORT).show();
        }
    }
}
