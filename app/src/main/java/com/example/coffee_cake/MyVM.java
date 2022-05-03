package com.example.coffee_cake;

import android.text.BoringLayout;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


public class MyVM extends ViewModel {
    private MutableLiveData<ArrayList<Boolean>> tables = new MutableLiveData<>();

    public void setTables(int index ,boolean values)
    {

    }

    public ArrayList<Boolean> getTables()
    {
        return tables.getValue();
    }

    public void setTables(ArrayList<Boolean> values)
    {
        this.tables.setValue(values);
    }
}
