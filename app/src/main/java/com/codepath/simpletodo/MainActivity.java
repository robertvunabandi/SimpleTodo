package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // you can declare fields here
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    public final static int EDIT_REQUEST_CODE = 20;
    public  final static String ITEM_TEXT = "itemText";
    public  final static String ITEM_POSITION = "itemPosition";
    // called by android when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // the superclass' logic will be executed first
        super.onCreate(savedInstanceState);
        // Inflating the layout file from res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        // obtain a reference to the ListView created with the layout
        lvItems = (ListView) findViewById(R.id.lvItems);
        // initialize the items list
        readItems();
        // initialize the adapter using the items list
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // wire the adapter to the view
        lvItems.setAdapter(itemsAdapter);

        // setup the listener on creation
        setupListViewListener();

        //setupListEditListener();
    }
    //you can add other methods here
    public void onAddItem(View v) {
        // obtain a reference to the EditText created with the layout
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        // grab the EditText's content as a String
        String itemText = etNewItem.getText().toString();
        // add the item to the list via the adapter
        itemsAdapter.add(itemText);
        // clear the EditText by setting it to an empty String
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(), "\""+itemText+"\" added to your list :)", Toast.LENGTH_SHORT).show();
    }
    private void setupListViewListener() {
        // set the ListView's itemLongClickListener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // remove the item in the list at the index given by position
                items.remove(position);
                // notify the adapter that the underlying dataset changed
                itemsAdapter.notifyDataSetChanged();
                // store the updated list
                writeItems();
                Toast.makeText(getApplicationContext(), "Removed item " + position, Toast.LENGTH_SHORT).show();
                // Log.i("MainActivity", "Removed item " + position);
                // return true to tell the framework that the long click was consumed
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(ITEM_TEXT, items.get(position));
                i.putExtra(ITEM_POSITION, position);
                startActivity(i);
            }
        });
        /*lvItems.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(MainActivity.this, EditActivity.class);
            i.putExtra(ITEM_TEXT, items.get(position));
            i.putExtra(ITEM_POSITION, position);
            startActivity(i);
        });*/
    }

    public void launchComposeView(String before, String position) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditActivity.class);
        i.putExtra("content", before);
        i.putExtra("position", position);
        startActivity(i); // brings up the second activity
    }

    // returns the file in which the data is stored
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }
    // read the items from the file system
    private void readItems() {
        try {
            // create the array using the content in the file
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            // print the error to the console
            e.printStackTrace();
            // just load an empty list
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e){
            Log.e("MainActivity", "Error writing file", e);
        }
    }
}
