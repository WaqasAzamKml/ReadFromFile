package com.readfromfile;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MIRSAB on 3/7/2017.
 * This Java class reads text from the input file (file should be available inside the project, maybe we'll add support for external and remote files in the future
 * PatternReplacer also scans for patterns (or short-codes) (anything inside curly brackets)
 * This class is also able to replace the short-codes' explanation inside the text file read earlier and then it returns the replaced String.
 */

public class PatternReplacer {
    BufferedReader reader = null;
    StringBuilder text = null;
    Context context;
    String replacedString;
    HashMap<String, String> patternExplanations;

    public PatternReplacer(Context context) {
        this.context = context;
    }

    //Asks for the file path (currently file path must be from inside the project's assets folder.
    // Returns true if successful otherwise returns false.
    public boolean addFile(String filePath){
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filePath)));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add short-codes as keys and their respective explanations as values.
    public void addExplanation(HashMap<String, String> patternExplanations){
        this.patternExplanations = patternExplanations;
    }

    //Reads text from the file specified in addFile() and returns it in the String format,
    // also stores it inside text (StringBuilder object) for later usages,
    // also closes the reader if it is still open (providing data & memory leakages.
    public String readText(){
        String line;
        try {
            while((line = reader.readLine()) != null){
                text.append(line);
                text.append("\n");
            }
            return text.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Finds for any text contained inside curly brackets from the text read before from the file (inside readText() method)
    // If no matches are found, it returns null.
    // Otherwise, it replaces the text (short-codes) with their respective explanations provided in the addExplanation() method.
    // Then finally, it returns the string with replaced text.
    public String findAndReplace(){
        replacedString = text.toString();
        Pattern myPattern = Pattern.compile("\\{(.*?)\\}"); // regular expression for any text contained inside curly brackets{}
        // brackets are escaped using backslashes \ but backslash itself is a special character in java's string
        // so it is also escaped by backslash, making it a double backslash \\
        Matcher matcher = myPattern.matcher(replacedString);
        if(!matcher.find())
            return null;
        while(matcher.find()){
            replacedString = replacedString.replace(matcher.group(),patternExplanations.get(matcher.group()));
        }
        return replacedString;
    }
}
