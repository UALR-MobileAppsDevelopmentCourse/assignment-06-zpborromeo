package com.ualr.recyclerviewassignment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.AdapterListBasic;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.databinding.ActivityListMultiSelectionBinding;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT_POS = 2;

    private FloatingActionButton mFAB;
    private List<Inbox> mainDataSource;
    private ActivityListMultiSelectionBinding mainBinding;
    private AdapterListBasic mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityListMultiSelectionBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();
    }

    private void initComponent() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        mainDataSource = DataGenerator.getInboxData(this);

    }

    // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly
    private void initRecyclerView(View view) {

        mainAdapter = new AdapterListBasic(this, mainDataSource);
        mainBinding.MainRecyclerView.setAdapter(mainAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.MainRecyclerView.setLayoutManager(layoutManager);

        // TODO 05: Invoke the removeItem method
        mainAdapter.setOnItemClickListener(new AdapterListBasic.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mainAdapter.removeItem(position);
            }
        });

        // TODO 04. Define the layout of each item in the list
        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 10. Invoke the method created to a new item to the top of the list so it's
                //  triggered when the user taps the Floating Action Button
                mainAdapter.addItem(DEFAULT_POS, mainDataSource.get(Tools.getRandomNumberInRange(0, mainDataSource.size()-1)));
            }
        });
    }
}