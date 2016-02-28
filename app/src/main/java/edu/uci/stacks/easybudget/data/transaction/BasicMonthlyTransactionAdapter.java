package edu.uci.stacks.easybudget.data.transaction;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class BasicMonthlyTransactionAdapter extends RecyclerView.Adapter<BasicMonthlyTransactionAdapter.ViewHolder> {


    private final MoneyTransactionData moneyTransactionData;
    private final Date date;
    private Cursor cursor;


    public void update() {
        cursor.close();
        cursor = moneyTransactionData.getAllTransactionsForDate(date);
        notifyDataSetChanged();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public TextView amount;
        public TextView date;
        public ViewHolder(View root) {
            super(root);
            this.name = (TextView) root.findViewById(R.id.transaction_list_item_name);
            this.amount = (TextView) root.findViewById(R.id.transaction_list_item_amount);
            this.date = (TextView) root.findViewById(R.id.transaction_list_item_date);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BasicMonthlyTransactionAdapter(MoneyTransactionData moneyTransactionData, Date date) {
        this.moneyTransactionData = moneyTransactionData;
        this.date = date;
        cursor = moneyTransactionData.getAllTransactionsForDate(date);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BasicMonthlyTransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basic_transaction_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        cursor.moveToPosition(position);

        holder.name.setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME)));
        holder.amount.setText(DisplayUtil.formatToCurrencyFromCents(cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT))));
        holder.date.setText(DisplayUtil.formatToMonthDay(cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_DATE))));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
