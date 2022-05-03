package com.example.coffee_cake;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyVM extends ViewModel {
    private MutableLiveData<Boolean[]> tables = new MutableLiveData<>();

    public void setTables(int index ,boolean values)
    {
        this.tables.getValue()[index] = values;
    }

    public Boolean[] getTables()
    {
        return this.tables.getValue();
    }
}
