package com.asrai.expandableradiogroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Element element1 = new Element(R.drawable.ic_filter_1_black_24dp ,"My first element", "It was many and many a year ago, in a kingdom by the sea, that a maiden there lived whom you may know by the name of ANNABEL LEE");
        Element element2 = new Element(R.drawable.ic_filter_2_black_24dp,"My second element", "But the Raven, sitting lonely on the placid bust, spoke only that one word, as if his soul in that one word he did outpour. Nothing further then he uttered- not a feather then he fluttered-Till I scarcely more than muttered, Other friends have flown before-On the morrow he will leave me, as my hopes have flown before. Then the bird said, Nevermore." );
        Element element3 = new Element(R.drawable.ic_filter_3_black_24dp,"My third element", "Is all that we see or seem, But a dream within a dream?");
        Element element4 = new Element(R.drawable.ic_filter_4_black_24dp,"My element without desc");
        List<Element> elements = Arrays.asList(element1, element2, element3, element4);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ExpandableRadioButtonAdapter adapter = new ExpandableRadioButtonAdapter(this, elements);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
