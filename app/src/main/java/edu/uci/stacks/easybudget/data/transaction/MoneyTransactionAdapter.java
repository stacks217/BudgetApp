package edu.uci.stacks.easybudget.data.transaction;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.EnterPurchaseActivity;
import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class MoneyTransactionAdapter extends RecyclerView.Adapter<MoneyTransactionAdapter.ViewHolder> {


    private final MoneyTransactionData moneyTransactionData;
    private Cursor cursor;


    public void update() {
        cursor.close();
        cursor = moneyTransactionData.getMoneyTransactionsOutForDisplayCursor();
        notifyDataSetChanged();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView name;
        public TextView category;
        public TextView amount;
        public TextView date;
        public TextView enterDate;
        public int id;
        public ViewHolder(View root) {
            super(root);
            root.setOnClickListener(this);
            this.name = (TextView) root.findViewById(R.id.transaction_list_item_name);
            this.category = (TextView) root.findViewById(R.id.transaction_list_item_category);
            this.amount = (TextView) root.findViewById(R.id.transaction_list_item_amount);
            this.date = (TextView) root.findViewById(R.id.transaction_list_item_date);
            this.enterDate = (TextView) root.findViewById(R.id.transaction_list_item_create_date);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), EnterPurchaseActivity.class);
            i.putExtra(EnterPurchaseActivity.MONEY_TRANSACTION_ID, id);
            v.getContext().startActivity(i);
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
                .inflate(R.layout.transaction_list_item, parent, false);
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
        String dataString = "";

        holder.name.setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_NAME)));
        holder.category.setText(cursor.getString(cursor.getColumnIndex("categoryName")));
        holder.amount.setText(DisplayUtil.formatToCurrencyFromCents(cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_AMOUNT))));
        holder.date.setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_DATE)));
        holder.enterDate.setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction.COLUMN_NAME_CREATE_DATE)));
        holder.id = cursor.getInt(cursor.getColumnIndex(BudgetDataContract.MoneyTransaction._ID));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
