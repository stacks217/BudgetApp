package edu.uci.stacks.easybudget.data.transaction;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetDataContract;

public class MoneyTransactionAdapter extends RecyclerView.Adapter<MoneyTransactionAdapter.ViewHolder> {


    private final MoneyTransactionData moneyTransactionData;
    private Cursor cursor;


    public void update() {
        cursor.close();
        cursor = moneyTransactionData.getMoneyTransactionsCursor();
        notifyDataSetChanged();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView dataText;
        public int id;
        public ViewHolder(TextView v) {
            super(v);
            dataText = v;
        }

        @Override
        public void onClick(View v) {

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MoneyTransactionAdapter(MoneyTransactionData moneyTransactionData) {
        this.moneyTransactionData = moneyTransactionData;
        cursor = moneyTransactionData.getMoneyTransactionsCursor();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MoneyTransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_spinner_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder((TextView)v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        cursor.moveToPosition(position);
        String dataString = "";
        dataString += cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction._ID));
        dataString += "," + cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME));
        dataString += "," + cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CATEGORY_ID));
        dataString += "," + cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_IN));
        dataString += "," + cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT));
        holder.dataText.setText(dataString);
        holder.id = cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction._ID));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
