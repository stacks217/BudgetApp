package edu.uci.stacks.easybudget.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.category.Category;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.util.DisplayUtil;

public class EditCategoryActivity extends BudgetActivity {
    @Inject
    CategoryData categoryData;

    private int categoryId = -1;
    private EditText editCategoryAmount;
    private EditText editCategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        Category category = null;
        if (getIntent().hasExtra("category_id")) {
            categoryId = getIntent().getIntExtra("category_id", -1);
            category = categoryData.getCategory(categoryId);
        }
        setContentView(R.layout.activity_edit_category);
        editCategoryName = (EditText) findViewById(R.id.input_name);
        editCategoryAmount = (EditText) findViewById(R.id.input_amount);
        if (category != null) {
            editCategoryName.setText(category.getName());
            editCategoryAmount.setText(category.getInputDisplayAmount());
        }

        setupToolbar();
    }

    public void finished(View view) {
        String name = editCategoryName.getText().toString();
        String amountText = editCategoryAmount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            amountText = "0.0";
        }
        int amount = DisplayUtil.toCentsFromDollars(amountText);
        if (TextUtils.isEmpty(name)) {
            categoryData.removeCategoryById(categoryId);
        } else {
            if (categoryId != -1) {
                categoryData.updateCategory(new Category(categoryId, name, amount));
            } else {
                categoryData.addCategory(new Category(categoryId, name, amount));
            }
        }
        finish();
    }
}
