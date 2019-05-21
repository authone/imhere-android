package ro.certsign.imhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static String KEY_PREF_USERID = "pref_key_edit_text_id";
    public static String KEY_PREF_USERNAME = "pref_key_edit_text_name";
    public static String KEY_PREF_ROOM = "pref_key_edit_text_room";
    public static String KEY_PREF_ISMOKE = "pref_key_check_box_ismoke";
    public static String KEY_PREF_SERVER = "pref_key_edit_text_serverurl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}


