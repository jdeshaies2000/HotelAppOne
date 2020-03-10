package com.bb.hotelappone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bb.hotelappone.model.Name;
import com.bb.hotelappone.adapter.NameAdapter;
import com.bb.hotelappone.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private NameAdapter nameAdapter;

    private EditText guestNameEditText;
    private ListView guestListView;
    private Button addGuestButton;

    private int guestCount = 0;
    private String guestKeyPrefix = "GUEST_";

    private List guestList = new ArrayList<String>();
    private List nameList = new ArrayList<Name>();

    // SharedPreferences
    private final String GUEST_COUNT_KEY = "guest.count.key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.bb.hotelappone", Context.MODE_PRIVATE);

        guestNameEditText = findViewById(R.id.editText);
        guestListView = findViewById(R.id.guest_listview);
        addGuestButton = findViewById(R.id.add_guest_button);

        guestCount = sharedPreferences.getInt(GUEST_COUNT_KEY, 0);

        addGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String guestName = guestNameEditText.getText().toString().trim();
                guestCount++;

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(guestKeyPrefix + guestCount, guestName);
                editor.putInt(GUEST_COUNT_KEY, guestCount);
                editor.apply();

                readGuests();
                guestNameEditText.setText("");
            }
        });

        readGuests();
    }

    private void readGuests() {
        guestList.clear();
        nameList.clear();

        guestCount = sharedPreferences.getInt(GUEST_COUNT_KEY, 0);

        for (int i = 0; i < guestCount; i++) {
            String name = sharedPreferences.getString(guestKeyPrefix+(i + 1), "unknown");
            guestList.add(name);
            nameList.add(new Name("Mr.", name));
            Log.d("TAG_X", name);
        }

        updateGuestList();
    }

    private void updateGuestList() {
        nameAdapter = new NameAdapter(nameList);
        guestListView.setAdapter(nameAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG_X", "onResume() lifecycle");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG_X", "onStop() lifecycle");
    }

}
