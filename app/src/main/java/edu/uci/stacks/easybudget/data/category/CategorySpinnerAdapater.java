package edu.uci.stacks.easybudget.data.category;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.BudgetDataContract;

public class CategorySpinnerAdapater extends CursorAdapter {

    public CategorySpinnerAdapater(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_spinner_view, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view).setText(cursor.getString(cursor.getColumnIndex(BudgetDataContract.Category.COLUMN_NAME_NAME)));
    }

    public int getPosition(int categoryId) {
        Cursor c = getCursor();
        c.moveToFirst();
        while(!c.isAfterLast()) {
            if (c.getInt(c.getColumnIndex(BudgetDataContract.Category._ID)) == categoryId) {
                return c.getPosition();
            }
            c.moveToNext();
        }
        return -1;
    }

    public int getCategoryId(int selectedItemPosition) {
        Cursor c = getCursor();
        c.moveToPosition(selectedItemPosition);
        return c.getInt(c.getColumnIndex(BudgetDataContract.Category._ID));
    }
}
