
package com.example.kostkilosowe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int[] kostki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Znajdywanie elementów z xml po id
        EditText editTextLiczbaKostek = findViewById(R.id.editTextLiczbaKostek);
        Button buttonLosuj = findViewById(R.id.buttonLosuj);
        Button buttonRosnaco = findViewById(R.id.buttonRosnaco);
        Button buttonMalejaco = findViewById(R.id.buttonMalejaco);

        buttonLosuj.setOnClickListener(v -> {

            // WALIDACJA
            String tekst = editTextLiczbaKostek.getText().toString().trim();

            if (tekst.isEmpty()) {
                Toast.makeText(this, "Podaj liczbę kostek!", Toast.LENGTH_SHORT).show();
                return;
            }


            int liczbaKostek = Integer.parseInt(tekst);
            if (liczbaKostek > 100) {
                Toast.makeText(this, "Nie można losować więcej niż 100 kostek!", Toast.LENGTH_SHORT).show();
                return;
            } else if (liczbaKostek < 1) {
                Toast.makeText(this, "Nie możesz losować mniej niż 1 kostki!", Toast.LENGTH_SHORT).show();
                return;
            }

            kostki = new int[liczbaKostek];
            Random random = new Random();

            for (int i = 0; i < kostki.length; i++) {
                kostki[i] = random.nextInt(6) + 1;
            }
            pokazKostki();
        });

        buttonRosnaco.setOnClickListener(v -> {
            if (kostki == null) {
                Toast.makeText(this, "Musisz najpierw wylosować kostki!", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < kostki.length - 1; i++) {
                for (int j = 0; j < kostki.length - 1 - i; j++) {

                    if (kostki[j] > kostki[j + 1]) {
                        int temp = kostki[j];
                        kostki[j] = kostki[j + 1];
                        kostki[j + 1] = temp;

                    }
                }
            }
            pokazKostki();
        });

        buttonMalejaco.setOnClickListener(v -> {
            if (kostki == null) {
                Toast.makeText(this, "Musisz najpierw wylosować kostki!", Toast.LENGTH_SHORT).show();
                return;
            }
            
            
            for (int i = 0; i < kostki.length - 1; i++) {
                
                int indeksMax = i;
                for (int j = i + 1; j < kostki.length; j++) {
                    if (kostki[j] > kostki[indeksMax]) {
                        indeksMax = j;
                    }
                }

                int temp = kostki[i];
                kostki[i] = kostki[indeksMax];
                kostki[indeksMax] = temp;
            }
            pokazKostki();
        });
    }
    
    private void pokazKostki() {
        GridLayout gridKostki = findViewById(R.id.gridKostki);
        TextView textViewWyniki = findViewById(R.id.textViewWyniki);


        gridKostki.removeAllViews();
        gridKostki.setColumnCount(5);

        String wyniki = "[";
        for (int i = 0; i < kostki.length; i++) {

            TextView tvKostka = new TextView(this);
            tvKostka.setText(String.valueOf(kostki[i]));
            tvKostka.setTextSize(20);


            gridKostki.addView(tvKostka);
            wyniki += kostki[i];
            if (i < kostki.length - 1) {
                wyniki += ", ";
            }
        }
        wyniki += "]";
        textViewWyniki.setText(wyniki);
    }
}
