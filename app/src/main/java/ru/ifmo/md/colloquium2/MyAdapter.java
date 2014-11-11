package ru.ifmo.md.colloquium2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by izban on 11.11.14.
 */
public class MyAdapter extends BaseAdapter {
    ArrayList<Candidate> candidateArrayList;

    MyAdapter() {
        candidateArrayList = new ArrayList<Candidate>();
    }

    @Override
    public int getCount() {
        return candidateArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return candidateArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Log.i("", "what");
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main, viewGroup, false);
        }
        TextView text = (TextView)view.findViewById(R.id.textView);
        text.setText((CharSequence)(candidateArrayList.get(i).name + ": " + candidateArrayList.get(i).count));
        return view;
    }

    public void add(Candidate candidate) {
        candidateArrayList.add(candidate);
    }

    public void clear() {
        candidateArrayList.clear();
    }
}
