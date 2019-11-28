package com.example.androidverifier;

import android.content.Context;
import android.widget.Toast;

public class MessageBuilder {

    /**
     *
     * @param context
     * @param text
     * return Toast
     */
    public static void makeToastLengthLong(Context context, String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
