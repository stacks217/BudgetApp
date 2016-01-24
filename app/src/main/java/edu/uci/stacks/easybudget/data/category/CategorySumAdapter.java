package edu.uci.stacks.easybudget.data.category;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.EditCategoryActivity;
import edu.uci.stacks.easybudget.data.BudgetDataContract;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class CategorySumAdapter extends RecyclerView.Adapter<CategorySumAdapter.ViewHolder>{

    private final CategoryData categoryData;
    private Cursor cursor;
    private final Date date;


    public void update() {
        cursor.close();
        cursor = categoryData.getCategoriesCursorWithSumByMonth(date);
        notifyDataSetChanged();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView name;
        public TextView amount;
        public int id;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            this.name = (TextView) v.findViewById(R.id.category_name);
            this.amount = (TextView) v.findViewById(R.id.category_amount);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), EditCategoryActivity.class);
            if (id != -1) {
                i.putExtra("category_id", id);
            }
            v.getContext().startActivity(i);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategorySumAdapter(CategoryData categoryData, Date date) {
        this.categoryData = categoryData;
        this.date = date;
        cursor = categoryData.getCategoriesCursorWithSumByMonth(date);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategorySumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.name.setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_NAME)));
        int cents = cursor.getInt(cursor.getColumnIndex("sum"));
        holder.amount.setText(DisplayUtil.formatToCurrencyFromCents(cents));
        holder.id = cursor.getInt(cursor.getColumnIndex(BudgetDataContract.Category._ID));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
