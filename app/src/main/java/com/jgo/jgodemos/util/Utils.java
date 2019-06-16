package com.jgo.jgodemos.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jgo.jgodemos.data.SampleGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ke-oh on 2019/06/16.
 *
 */

public class Utils {

    /**
     * Jasonファイルを解析する。
     *
     * @param context  コンテキスト
     */
    public static String parseJsonToString(@NonNull Context context, String path) {
        StringBuilder dataSb = new StringBuilder();

        if (TextUtils.isEmpty(path)) {
            return dataSb.toString();
        }

        InputStream inputstream = null;

        String line;
        try {
            inputstream = context.getResources().getAssets().open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

            while ((line=reader.readLine())!=null){
                dataSb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataSb.toString();

    }

    /**
     * GSONでString -> Objectに解析する。
     *
     * @param jsonData　
     * @return SampleGroup List
     */
    public static ArrayList<SampleGroup> parseStringToGSON(String jsonData) {

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(jsonData).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<SampleGroup> userBeanList = new ArrayList<>();

        for (JsonElement sample : jsonArray) {
            SampleGroup userBean = gson.fromJson(sample, SampleGroup.class);
            userBeanList.add(userBean);
        }
        return userBeanList;
    }
}
