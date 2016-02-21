package edu.uci.stacks.easybudget.data.transaction;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class AutoCompleteTransactionAdapater extends BaseAdapter implements Filterable {

    private final MoneyTransactionData moneyTransactionData;

    public AutoCompleteTransactionAdapater(MoneyTransactionData moneyTransactionData) {
        this.moneyTransactionData = moneyTransactionData;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }
}
