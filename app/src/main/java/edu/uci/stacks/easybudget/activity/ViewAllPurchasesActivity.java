package edu.uci.stacks.easybudget.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionAdapter;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;

public class ViewAllPurchasesActivity extends BudgetActivity {

    @Inject
    MoneyTransactionData transactionData;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MoneyTransactionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        setContentView(R.layout.activity_view_all_purchases);
        setupToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.money_transaction_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MoneyTransactionAdapter(transactionData);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onResume() {
        super.onResume();
        mAdapter.update();
    }
}
