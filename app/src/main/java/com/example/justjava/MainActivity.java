package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 7;
    int total;
    String name = "Brentanovitch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        chocolate.setChecked(false);
        CheckBox whipped = findViewById(R.id.whippedcream_checkbox);
        whipped.setChecked(false);
        TextView quantityView = findViewById(R.id.quantity_display_view);
        quantityView.setText(Integer.toString(quantity));
        TextView nameview = findViewById(R.id.name_text_input);
        nameview.setText(name);
    }

    /**
     * this method displays the summary on the screen and submits the order
     * when the order button is pressed.
     *
     * @param view
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_text_input);
        name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedcream_checkbox);
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        total = calculatePrice(quantity);
//        displayOrder(createOrderSummary(quantity, hasWhippedCream, hasChocolate));
        String message = createOrderSummary(quantity, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:brentm68@gmail.com"));
        intent.putExtra(Intent.EXTRA_STREAM, message);
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * this method increments the quantity on the screen.
     *
     * @param view
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "you cannot order more than 100", Toast.LENGTH_SHORT).show();
        } else {
            quantity += 1;
            displayQuntity(quantity);
        }
    }

    /**
     * this method decrements the quantity displayed on screen
     *
     * @param view
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "you cannot order less than 1", Toast.LENGTH_SHORT).show();
        } else {
            quantity -= 1;
            displayQuntity(quantity);
        }
    }

    /**
     * This method clears all variables and updates the main activity screen.
     */
    public void clearCount(View view) {
        quantity = 0;
        displayQuntity(quantity);
        total = 0;
        displayOrder("");
        CheckBox whippedCreamCheckBox = findViewById(R.id.whippedcream_checkbox);
        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        whippedCreamCheckBox.setChecked(false);
        chocolateCheckbox.setChecked(false);
        EditText nameText = findViewById(R.id.name_text_input);
        nameText.setText("");
    }

    /**
     * This method updates the order summary view on the screen.
     */
    private void displayOrder(String summary) {
        String order = summary;
        TextView orderSummary = findViewById(R.id.summarytext_text_view);
        orderSummary.setText(order);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuntity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_display_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method calculates the total.
     */
    private int calculatePrice(int quantity) {
        total = quantity * price;
        return total;
    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(int quant, boolean addWhippedCream, boolean addChocolate) {
        String addWhip;
        String addChoco;
        if (addWhippedCream) {
            total += 1;
            addWhip = "Yes";
        } else {
            addWhip = "No";
        }
        if (addChocolate) {
            addChoco = "Yes";
            total += 1;
        } else {
            addChoco = "No";
        }
        String message = getString(R.string.order_summary_name, name);
        message += "\n" + getString(R.string.add_whipped_cream) + " " + addWhip;
        message += "\n" + getString(R.string.add_cohocolate) + " " + addChoco;
        message += "\n" + getString(R.string.quantity) + quant;
        message += "\n" + getString(R.string.order_total, NumberFormat.getCurrencyInstance().format(total));
        message += "\n" + getString(R.string.thank_you);
        return message;
    }
}