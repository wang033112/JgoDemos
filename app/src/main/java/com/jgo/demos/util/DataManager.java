package com.jgo.demos.util;

import android.content.Context;

import com.jgo.demos.R;
import com.jgo.demos.listview.data.PersonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/06/29.
 *
 */

public class DataManager {

    private static DataManager instance;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * Get the list of persons
     *
     * @return person list
     */
    public List<PersonData> getPersonList(Context context) {
        List<PersonData> personList = new ArrayList<>();

        PersonData person_1 = new PersonData("Houston Rockets Center", R.mipmap.photo_1);
        person_1.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_1);

        PersonData person_2 = new PersonData("Houston Rockets Center", R.mipmap.photo_2);
        person_2.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_2);

        PersonData person_3 = new PersonData("Houston Rockets Center", R.mipmap.photo_3);
        person_3.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_3);

        PersonData person_4 = new PersonData("Houston Rockets Center", R.mipmap.photo_1);
        person_4.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_4);

        PersonData person_5 = new PersonData("Houston Rockets Center", R.mipmap.photo_2);
        person_5.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_5);

        PersonData person_6 = new PersonData("Houston Rockets Center", R.mipmap.photo_3);
        person_6.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_6);

        PersonData person_7 = new PersonData("Houston Rockets Center", R.mipmap.photo_1);
        person_7.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_7);

        PersonData person_8 = new PersonData("Houston Rockets Center", R.mipmap.photo_2);
        person_8.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_8);

        PersonData person_9 = new PersonData("Houston Rockets Center", R.mipmap.photo_3);
        person_9.setContent(context.getString(R.string.person_list_content_y_m));
        personList.add(person_9);

        return personList;
    }
}
