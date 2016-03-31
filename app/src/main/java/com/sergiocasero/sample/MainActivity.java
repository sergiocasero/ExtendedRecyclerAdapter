package com.sergiocasero.sample;

import com.sergiocasero.extendedrecycleradapter.ExtendedRecyclerAdapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler)
    RecyclerView cars;

    private Car audi;

    @OnClick(R.id.add)
    public void onAddClick() {
        myRecyclerAdapter.add(audi);
    }

    @OnClick(R.id.clear)
    public void onClearClick() {
        myRecyclerAdapter.clear();
    }

    @OnClick(R.id.add_all)
    public void onAddAllClick() {
        myRecyclerAdapter.addAll(list);
    }

    @OnClick(R.id.update)
    public void onUpdateClick() {
        audi.setName("skoda");
        myRecyclerAdapter.update(audi);
    }

    @OnClick(R.id.remove)
    public void onRemoveClick() {
        myRecyclerAdapter.remove(audi);
    }


    private MyRecyclerAdapter myRecyclerAdapter;

    private List<Car> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeRecycler();
        registerListeners();
        initializeMockData();
    }

    private void initializeRecycler() {
        myRecyclerAdapter = new MyRecyclerAdapter();
        cars.setAdapter(myRecyclerAdapter);
        cars.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void registerListeners() {
        myRecyclerAdapter.setOnItemClickListener(new ExtendedRecyclerAdapter.OnItemClickListener<Car>() {
            @Override
            public void onClick(Car item, int position) {
                Toast.makeText(getApplicationContext(), "click!!", Toast.LENGTH_LONG).show();
            }
        });

        myRecyclerAdapter.setOnLongItemClickListener(new ExtendedRecyclerAdapter.OnLongItemClickListener<Car>() {
            @Override
            public void onLongClick(Car item, int position) {
                Toast.makeText(getApplicationContext(), "Long click!!", Toast.LENGTH_LONG).show();
            }
        });

        myRecyclerAdapter.setOnNotFoundListener(new ExtendedRecyclerAdapter.OnNotFoundListener() {
            @Override
            public void onNotFound(Object item) {
                Toast.makeText(getApplicationContext(), "Not found!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeMockData() {
        list = new ArrayList<>();
        list.add(new Car(0, "lamborgini"));
        list.add(new Car(1, "subaru"));
        list.add(new Car(2, "seat"));
        list.add(new Car(3, "mercedes"));
        list.add(new Car(4, "buggatti"));

        audi = new Car(5, "Audi");
    }

}
