package com.example.twx.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;


public class MyActivity extends Activity {
    private List<Station> stations;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Thread threadXML = new Thread(new Runnable() {
            @Override
            public void run() {
                VLilleWebService service = new VLilleWebService();
                stations = service.getStations();
            }
        });
        threadXML.start();
        try {
            threadXML.join();
            this.sortStation();
            this.setStationOnView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sortStation() {
        LocationFinder sort = new LocationFinder();
        stations = sort.sortStation(this, stations);
    }

    public void setStationOnView() {
        List<String> name = new LinkedList<String>();
        for (int i=0; i<this.stations.size();i++) {
            name.add(this.stations.get(i).getName());
        }
        this.listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MyActivity2.class);
                Bundle b = new Bundle();
                Station station = stations.get(position);
                b.putString("name", station.getName());
                b.putString("lat", station.getLatitude());
                b.putString("lng", station.getLongitude());
                b.putString("id", station.getId());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
