package com.example.bullsandcows;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.awt.font.TextAttribute;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {
    Button ok, regulations, link;
    TextView textBulls, textCows, amountBulls, amountCows, mainText;
    EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ok = findViewById(R.id.ok);
        regulations = findViewById(R.id.regulations);
        link = findViewById(R.id.link);
        textBulls = findViewById(R.id.textBulls);
        textCows = findViewById(R.id.textCows);
        amountBulls = findViewById(R.id.amountBulls);
        amountCows = findViewById(R.id.amountCows);
        mainText = findViewById(R.id.mainText);
        editText = findViewById(R.id.editText);


        String secretNumber = makeNumber();
        mainText.setText("Угадайте число из 4 уникальных цифр");
        //textView6.setTextColor(#FF000000);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String line = editText.getText().toString();

                line = editText.getText().toString();
                if (isNumeric(line) && line.length() == 4) {
                    String result = searchForMatches(secretNumber, line);

                    amountBulls.setText(result.charAt(0) + "");
                    amountCows.setText(result.charAt(2) + "");
                    mainText.setText("Прошлая комбинация: " + line);
                    if (line.equals("0000")) {
                        mainText.setText("Загаданное число: " + secretNumber);
                    }
                    editText.setText("");

                    if (result.charAt(0) == '4') {
                        mainText.setText("Вы победили. Загаданое число: " + secretNumber);
                        ok.setEnabled(false);
                    }
                } else {
                    mainText.setText("Введите значение из 4 цифр");
                }


            }
        });


        regulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createButtonAlertDialog("Правила","Компьютер задумывает четыре различные цифры из 0,1,2,...9. \n" +
                        "Игрок делает ходы, чтобы узнать эти цифры и их порядок.\n" +
                        "\n" +
                        "Каждый ход состоит из четырёх цифр. \n" +
                        "\n" +
                        "В ответ компьютер показывает число отгаданных цифр, стоящих на своих местах (число быков)\n" +
                        " и число отгаданных цифр, стоящих не на своих местах (число коров).");

            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createButtonAlertDialog("Ссылка на GitHub","https://github.com/1Gregorio1/BullsAndCows");

            }
        });


    }

    private void createButtonAlertDialog(String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();
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
