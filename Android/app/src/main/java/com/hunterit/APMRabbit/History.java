package com.hunterit.APMRabbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.Time;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class History extends Activity {

    final Context context = this;
    private DataSource datasource;
    private ListView listView;
    private Button addPoint, search;
    private EditText inputField;
    List<WayPoints> values;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);

        //Declaring the Visual Buttons/Lists
        listView = (ListView) findViewById(R.id.list);
        addPoint = (Button) findViewById(R.id.addWaypoint);
        search = (Button) findViewById(R.id.searchButton);
        inputField = (EditText) findViewById(R.id.searchText);

        registerForContextMenu(listView);

        refreshList(null);

        addPoint.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");

                String inputResult = inputField.getText().toString().trim();
                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();

                String message =   "Name: " + inputResult + "\n"
                                 + "Location: " + "43.2425, 34.2425" + "\n"
                                 + "Timestamp: " + today.format("%k:%M:%S") + "\n";

                builder.setMessage(message);

                // Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Add input to Database
                        datasource.create("43.2425, 34.2425", inputField.getText().toString().trim());
                        refreshList(null);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
            }
        );

        search.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {

              String inputResult = inputField.getText().toString().trim();
                 System.out.println(inputResult);
               if (inputResult.matches("")) {
                   Toast.makeText(getApplicationContext(), "Please fill in the box before selecting",
                           Toast.LENGTH_SHORT).show();
                 refreshList(null);
                 return;
              }

              else {
                   refreshList(inputResult);
               }

            }
         }
        );


        }

    public void refreshList(String search) {

        //Create a connection to the database
        datasource = new DataSource(this);
        datasource.open();

        values = datasource.getAllWaypoints();
        ArrayList<String> val_names = new ArrayList<String>();

        //Save all the names for the List
        for (int i=0; i < values.size(); i++) {

            if (search != null) {
                if (values.get(i).getName().equals(search))
                    val_names.add(values.get(i).getName());
            } else {
                val_names.add(values.get(i).getName());
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, val_names);

        // Set the ArrayAdapter as the ListView's adapter.
        listView.setAdapter(adapter);

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int position = info.position;

        System.out.println("Value of Position: " + position);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        System.out.println(item.getTitle());
        switch(item.getItemId())
        {
            case R.id.send:
                System.out.println("SEND SELECTED");
                break;
            case R.id.view:
                System.out.println("VIEW SELECTED");

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("View");

                String message =   "Name: " + "Awesome" + "\n"
                        + "Location: " + "43.2425, 34.2425" + "\n"
                        + "Timestamp: " + "April 2nd, 2014" + "\n";

                builder.setMessage(message);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Add input to Database
                    }
                });

                builder.show();
                break;
            case R.id.rename:
                System.out.println("RENAME SELECTED");

                AlertDialog.Builder renameBox = new AlertDialog.Builder(context);
                renameBox.setTitle("Rename");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                renameBox.setView(input);

                String mess =   "What will you like to rename too?";

                renameBox.setMessage(mess);

                // Set up the buttons
                renameBox.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Add input to Database
                        Editable value = input.getText();
                    }
                });
                renameBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                renameBox.show();
                break;
            case R.id.delete:
                System.out.println("DELETE SELECTED");
                break;
        }

        return super.onContextItemSelected(item);
    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}

