package com.example.consolidate;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spBorrower = findViewById(R.id.borrower);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBorrower.setAdapter(adapter);
        spBorrower.setOnItemSelectedListener(this);

        Spinner spOwed = findViewById(R.id.owed);

        spOwed.setAdapter(adapter);
        spOwed.setOnItemSelectedListener(this);

        //IMPORTANT CODE
        int size = 3;
        int[][] arr = new int[size][size];

        final Recordbook debt = new Recordbook(size, arr );
        final List<Transaction> transactionList = new ArrayList<Transaction>();

        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner firstPerson = findViewById(R.id.borrower);
                Spinner secondPerson = findViewById(R.id.owed);
                EditText amountText = findViewById(R.id.amount);

                int borrower = Integer.parseInt(firstPerson.getSelectedItem().toString());
                int owed = Integer.parseInt(secondPerson.getSelectedItem().toString());
                int amount = Integer.parseInt(amountText.getText().toString());
                Log.d("myTag", "" +borrower + " owes " + owed + " $" + amount);

                debt.addTransaction(borrower, owed, amount);

                Transaction temp = new Transaction(""+ borrower, ""+ owed, amount);
                transactionList.add(temp);


            }
        });

        Button btnDone = (Button) findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debt.consolidateDebts();
                Log.d("myTag", debt.toString());
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
