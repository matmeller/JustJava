/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 1;

    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        CheckBox CheckBoxCream = (CheckBox) findViewById(R.id.check_box_cream);
        boolean hasWhippedCream = CheckBoxCream.isChecked();

        CheckBox CheckBoxChoco = (CheckBox) findViewById(R.id.check_box_chocolate);
        boolean hasChoco = CheckBoxChoco.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChoco);
        String priceMessage = "Total: $" + price;
        String Name = "Name: " + nameField.getText().toString();
        String quant = "Quantity: " + quantity;
        String cream = "Add whipped cream? " + hasWhippedCream;
        String choco = "\nAdd chocolate? " + hasChoco;
        priceMessage = priceMessage + "\nThank you!";
        String wholeMessage = Name + "\n" + cream + choco + "\n" + quant + "\n" + priceMessage;
        //displayMessage(wholeMessage);
        String subject = "JustJava order for " + Name;

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, wholeMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChoco) {
        int basePrice = 5;

        if (hasChoco) {
            basePrice += 2;
        }

        if (hasWhippedCream) {
            basePrice++;
        }
        int price = quantity * basePrice;
        return price;
    }

    public void increment(View view) {
        if (quantity <= 100) {
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {

        if (quantity > 1) {
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}
