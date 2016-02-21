package edu.uci.stacks.easybudget.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import edu.uci.stacks.easybudget.BudgetApplication;
import edu.uci.stacks.easybudget.R;
import edu.uci.stacks.easybudget.activity.fragment.DatePickerFragment;
import edu.uci.stacks.easybudget.data.category.Category;
import edu.uci.stacks.easybudget.data.category.CategoryData;
import edu.uci.stacks.easybudget.data.category.CategorySpinnerAdapater;
import edu.uci.stacks.easybudget.data.transaction.AutoCompleteTransactionAdapater;
import edu.uci.stacks.easybudget.data.transaction.MoneyOutTransaction;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransaction;
import edu.uci.stacks.easybudget.data.transaction.MoneyTransactionData;

public class EnterPurchaseActivity extends BudgetActivity
        implements DatePickerDialog.OnDateSetListener  {

    public static final String MONEY_TRANSACTION_ID = "money_transaction_id";
    static final int REQUEST_TAKE_PHOTO = 1;

    @Inject
    CategoryData categoryData;

    @Inject
    MoneyTransactionData moneyTransactionData;

    public int moneyTransactionId = -1;
    private EditText editAmount;
    private Spinner categorySpinner;
    private CategorySpinnerAdapater adapter;
    private int year;
    private int month;
    private int day;
    String mCurrentPhotoPath;
    private Button datePicker;
    private AutoCompleteTextView editName;
    private Category[] categories;
    private ImageView receiptImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BudgetApplication.getComponent().inject(this);
        MoneyTransaction moneyTransaction = null;
        setContentView(R.layout.activity_enter_purchase);
        setupToolbar();

        editName = (AutoCompleteTextView) findViewById(R.id.input_name);
        editName.setAdapter(new AutoCompleteTransactionAdapater(moneyTransactionData));
        editAmount = (EditText) findViewById(R.id.input_amount);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        receiptImageView = (ImageView) findViewById(R.id.receipt_thumbnail);
        categories = categoryData.getCategories();
        adapter = new CategorySpinnerAdapater(categories);
        categorySpinner.setAdapter(adapter);


        if (getIntent().hasExtra(MONEY_TRANSACTION_ID)) {
            moneyTransactionId = getIntent().getIntExtra(MONEY_TRANSACTION_ID, -1);
            moneyTransaction = moneyTransactionData.getMoneyTransaction(moneyTransactionId);
        }

        if (moneyTransaction != null) {
            int spinnerPosition = 0;
            for (int i = 0; i < categories.length; i++) {
                if (moneyTransaction.getCategoryId() == categories[i].getId()) {
                    spinnerPosition = i;
                    break;
                }
            }
            categorySpinner.setSelection(spinnerPosition);
            editName.setText(moneyTransaction.getName());
            editAmount.setText((float) moneyTransaction.getAmount() / 100.0f + "");
            if (!TextUtils.isEmpty(moneyTransaction.getReceiptFilePath())) {
                mCurrentPhotoPath = moneyTransaction.getReceiptFilePath();
                receiptImageView.setImageBitmap(decodeSampledBitmapFromFile(mCurrentPhotoPath, 1000, 700));
            }
        }

        if (savedInstanceState != null) {
            year = savedInstanceState.getInt("year");
            month = savedInstanceState.getInt("month");
            day = savedInstanceState.getInt("day");
            mCurrentPhotoPath = savedInstanceState.getString("photoPath");
            if (mCurrentPhotoPath != null) {
                receiptImageView.setImageBitmap(decodeSampledBitmapFromFile(mCurrentPhotoPath, 1000, 700));
            }
        } else {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        datePicker = (Button) findViewById(R.id.date_picker);
        setDatePickerButtonText();
    }

    private void setDatePickerButtonText() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        String dateButtonText = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()).toString();
        datePicker.setText(dateButtonText);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void finished(View view) {
        // save purchase
        String name = editName.getText().toString();
        String amountText = editAmount.getText().toString();
        if (TextUtils.isEmpty(amountText)) {
            amountText = "0.0";
        }
        int amount = (int)(Double.parseDouble(amountText)*100);
        int categoryId = ((Category)categorySpinner.getSelectedItem()).getId();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        if (moneyTransactionId != -1) {
            moneyTransactionData.updateMoneyTransaction(new MoneyOutTransaction(moneyTransactionId, name, categoryId, date, amount, mCurrentPhotoPath, new Date()));
        } else {
            moneyTransactionData.addMoneyTransaction(new MoneyOutTransaction(name, categoryId, date, amount, mCurrentPhotoPath));
        }
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        setDatePickerButtonText();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("year", year);
        savedInstanceState.putInt("month", month);
        savedInstanceState.putInt("day", day);
        savedInstanceState.putString("photoPath", mCurrentPhotoPath);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void addReceipt(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            receiptImageView.setImageBitmap(decodeSampledBitmapFromFile(mCurrentPhotoPath, 1000, 700));
        }
    }

    public void viewImage(View v) {
        Intent viewImage = new Intent(this, ViewReceiptActivity.class);
        viewImage.putExtra("data", mCurrentPhotoPath);
        startActivity(viewImage);
    }

    // http://blog-emildesign.rhcloud.com/?p=590
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
}
