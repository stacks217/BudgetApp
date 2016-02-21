package edu.uci.stacks.easybudget.data.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import edu.uci.stacks.easybudget.R;

public class CategorySpinnerAdapater extends BaseAdapter {

    private Category[] data;

    public CategorySpinnerAdapater(Category[] data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    public Category getCategoryItem(int i) {
        return (Category) getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return getCategoryItem(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.category_item_spinner_view, null);
        }
        ((TextView) convertView).setText(getCategoryItem(position).getName());
        return convertView;
    }
}
