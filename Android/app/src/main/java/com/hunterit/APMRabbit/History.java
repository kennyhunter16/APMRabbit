package com.hunterit.APMRabbit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        //Create a connection to the database
        datasource = new DataSource(this);
        datasource.open();

        //Load all the Values of the Waypoints
        values = datasource.getAllWaypoints();

        registerForContextMenu(listView);

        //Update List
        refreshList(null);

        addPoint.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");

                final String inputResult = inputField.getText().toString().trim();

                SimpleDateFormat fmt = new SimpleDateFormat("MMMM d, yyyy hh:mm a");
                Date date = new Date();
                final String timestamp = fmt.format(date);

                //Get the Rover Location
                Rover roverInfo = new Rover();
                SharedPreferences location = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String roverLocation = location.getString("Location","");

                if (roverLocation == null)  roverLocation = "No Location Found";


                if (inputResult.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill in the box before adding location",
                            Toast.LENGTH_SHORT).show();
                }


                else {

                    String message = "Name: " + inputResult + "\n"
                            + "Location: " + roverLocation + "\n"
                            + "Timestamp: " + timestamp + "\n";

                    builder.setMessage(message);

                    final String thelocation = roverLocation;

                    // Set up the buttons
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Add input to Database
                            datasource.create(thelocation, inputResult, timestamp);
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
            }
        );

        search.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {

              String inputResult = inputField.getText().toString().trim();
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
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo contextMenuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        final int number = contextMenuInfo.position;

        switch(item.getItemId()){
            case R.id.send:
                System.out.println("SEND SELECTED");
                break;
            case R.id.view:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("View");

                String name = values.get(number).getName();
                String location = values.get(number).getLocation();
                String timestamp = values.get(number).getTimestamp();

                String message =   "Name: " + name + "\n"
                        + "Location: " + location + "\n"
                        + "Timestamp: " + timestamp + "\n";

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
                AlertDialog.Builder renameBox = new AlertDialog.Builder(context);
                renameBox.setTitle("Rename Waypoint");

                // Set an EditText view to get user input
                final EditText input = new EditText(this);
                renameBox.setView(input);

                String mess = "What will you like to rename too?";
                renameBox.setMessage(mess);


                // Set up the buttons
                renameBox.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Add input to Database
                        String new_name = input.getText().toString().trim();
                        long id = values.get(number).getID();
                        datasource.rename(id, new_name);
                        refreshList(null);

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
                long id = values.get(number).getID();
                datasource.delete(id);
                refreshList(null);
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

