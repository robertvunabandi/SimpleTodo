package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    public final static String ITEM_TEXT = "itemText";
    public final static String ITEM_POSITION = "itemPosition";
    ArrayAdapter<String> itemsAdapter;
    public String position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String content = getIntent().getStringExtra(ITEM_TEXT);

        position = getIntent().getStringExtra(ITEM_POSITION);

        EditText etNewItem = (EditText) findViewById(R.id.editText);
        etNewItem.setText(content);
    }

    public void onSubmit(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        
        // closes the activity and returns to first screen
        this.finish();
    }
}
