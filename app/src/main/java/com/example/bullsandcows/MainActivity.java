package com.example.bullsandcows;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView textBulls, textCows, textView4, textView5, textView6;
    EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textBulls = findViewById(R.id.textBulls);
        textCows = findViewById(R.id.textCows);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        editText = findViewById(R.id.editText);


        String secretNumber = makeNumber();
        textView6.setText("Угадайте число из 4 уникальных цифр");
        //textView6.setTextColor(#FF000000);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = editText.getText().toString();

                line = editText.getText().toString();
                if (isNumeric(line) && line.length() == 4) {
                    String result = searchForMatches(secretNumber, line);

                    textView4.setText(result.charAt(0) + "");
                    textView5.setText(result.charAt(2) + "");
                    textView6.setText("Прошлая комбинация: " + line);
                    if (line.equals("0000")) {
                        textView6.setText("Загаданное число: " + secretNumber);
                    }
                    editText.setText("");

                    if (result.charAt(0) == '4') {
                        textView6.setText("Вы победили. Загаданое число: " + secretNumber);
                        button.setEnabled(false);
                    }
                } else {
                    textView6.setText("Введите значение из 4 цифр");
                }


            }
        });


    }


    public static String makeNumber() {
        String str = (int) (Math.random() * 9 + 1) + "";
        for (int i = 1; i < 4; i++) {
            while (true) {
                String numeral = (int) (Math.random() * 9) + "";
                if (str.indexOf(numeral) == -1) {
                    str += numeral;
                    break;
                }
            }
        }
        return str;
    }

    static String searchForMatches(String hidden, String answer) {
        int bulls = 0, cows = 0;
        for (int i = 0; i < 4; i++) {
            if (hidden.indexOf(answer.charAt(i)) != -1) {
                if (answer.charAt(i) == hidden.charAt(i)) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }
        return bulls + "-" + cows;
    }

    static boolean isNumeric(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
