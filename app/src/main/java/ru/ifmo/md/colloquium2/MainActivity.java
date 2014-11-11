package ru.ifmo.md.colloquium2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    //MyAdapter adapter;
    ElectionHelper helper;
    Intent intent;
    boolean ended = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intent = new Intent(this, ResultActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new ElectionHelper(this, ElectionHelper.DATABASE_NAME, null, 1);

        lv = (ListView)findViewById(R.id.listView);
        //adapter = new MyAdapter();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!ended) {
                    return;
                }
                String s = ((ArrayAdapter<String>)parent.getAdapter()).getItem(position);
                Candidate cur = helper.getCandidate(position);
                cur.count++;
                helper.updateCandidate(cur);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View view) {
        TextView text = (TextView)findViewById(R.id.editText);
        if (text.getText().toString().equals("")) {
            return;
        }
        adapter.add(text.getText().toString());
        //adapter.add(new Candidate(adapter.getCount(), text.getText().toString(), 0));
        helper.addCandidate(new Candidate(adapter.getCount() - 1, text.getText().toString(), 0));
        text.setText("");
    }

    public void onButton2Click(View view) {
        ((Button)findViewById(R.id.button)).setEnabled(false);
        ended = true;
    }

    public void onButton3Click(View view) {
        adapter.clear();
        ended = false;
        ((Button)findViewById(R.id.button)).setEnabled(true);
        startActivity(intent);
    }
}
