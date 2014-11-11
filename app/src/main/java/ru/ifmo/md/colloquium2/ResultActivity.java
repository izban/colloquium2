package ru.ifmo.md.colloquium2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);

        ElectionHelper helper = new ElectionHelper(this, ElectionHelper.DATABASE_NAME, null, 1);
        ArrayList<Candidate> arrayList = helper.getAllCandidates();
        Collections.sort(arrayList, new Comparator<Candidate>() {
            @Override
            public int compare(Candidate candidate, Candidate candidate2) {
                if (candidate.count > candidate2.count) return -1;
                if (candidate.count == candidate2.count) return 0;
                return 1;
            }
        });
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).toString();
            adapter.add(arrayList.get(i).name + " " + arrayList.get(i).count);
        }
        helper.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result_activitty, menu);
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
