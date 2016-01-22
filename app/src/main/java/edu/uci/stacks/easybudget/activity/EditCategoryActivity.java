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
        setContentView(R.layout.activity_edit_category);
        setupToolbar();
        editCategoryName = (EditText) findViewById(R.id.input_name);
        editCategoryAmount = (EditText) findViewById(R.id.input_amount);
        if (getIntent().hasExtra("category_id")) {
            categoryId = getIntent().getIntExtra("category_id", -1);
            Category category = categoryData.getCategory(categoryId);
            editCategoryName.setText(category.getName());
            editCategoryAmount.setText(category.getInputDisplayAmount());
        }

    }

    public void finished(View view) {
        String name = editCategoryName.getText().toString();
        int amount = (int)(Double.parseDouble(editCategoryAmount.getText().toString())*100);
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
