package com.example.coffee_cake;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ViewModel_for_food extends ViewModel {
    private MutableLiveData<ArrayList<OrderDrinks>> queue = new MutableLiveData<>();

    public ArrayList<OrderDrinks> getQueues()
    {
        return queue.getValue();
    }

    public void setQueues(ArrayList<OrderDrinks> values)
    {
        this.queue.setValue(values);
    }

    public MutableLiveData<ArrayList<OrderDrinks>> getData(){
        return queue;
    }
}
