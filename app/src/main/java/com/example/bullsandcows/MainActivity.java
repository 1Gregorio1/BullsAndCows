package com.example.bullsandcows;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    Button ok, regulations, link, reset;
    TextView textBulls, textCows, amountBulls, amountCows, mainText;
    EditText editText;
    String secretNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ok = findViewById(R.id.ok);
        regulations = findViewById(R.id.regulations);
        link = findViewById(R.id.link);
        reset = findViewById(R.id.reset);

        textBulls = findViewById(R.id.textBulls);
        textCows = findViewById(R.id.textCows);
        amountBulls = findViewById(R.id.amountBulls);
        amountCows = findViewById(R.id.amountCows);
        mainText = findViewById(R.id.mainText);

        editText = findViewById(R.id.editText);



        secretNumber = makeNumber();
        reset.setEnabled(false);
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
                        conditionButton(ok, false);
                        editText.setEnabled(false);
                        editText.setAlpha(0);
                        conditionButton(reset, true);
                        conditionText(textBulls, false);
                        conditionText(textCows, false);
                        conditionText(amountBulls, false);
                        conditionText(amountCows, false);
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
                        "Может начинаться с 0.\n" +
                        "Каждый ход состоит из четырёх цифр. \n" +
                        "\n" +
                        "В ответ компьютер показывает число отгаданных цифр, стоящих на своих местах (число быков)\n" +
                        " и число отгаданных цифр, стоящих не на своих местах (число коров).");

            }
        });

        /*
            Вызываем метод с алертом для кнопки "GITHAB"
         */

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialogButtonClicked();
//                createButtonAlertDialog("Ссылка на GitHub", "https://github.com/1Gregorio1/BullsAndCows");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/1Gregorio1/BullsAndCows"));
//                startActivity(browserIntent);
//                createButtonAlertDialog("Ссылка на GitHub", "https://github.com/1Gregorio1/BullsAndCows");
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conditionButton(ok, true);
                editText.setEnabled(true);
                editText.setAlpha(1);
                conditionButton(reset, false);
                conditionText(textBulls, true);
                conditionText(textCows, true);
                conditionText(amountBulls, true);
                conditionText(amountCows, true);
                amountBulls.setText("");
                amountCows.setText("");
                mainText.setText("Все по новой!");
                secretNumber = makeNumber();
            }
        });
    }

    /*
        Скрывать или показывать кнопку
    */
    void conditionButton(Button button, boolean condition){
        button.setEnabled(condition == true ? true : false);
        button.setAlpha(condition == true ? 1 : 0);
    }

    /*
        Скрывать или показывать текст
    */
    void conditionText(TextView textView, boolean condition){
        textView.setAlpha(condition == true ? 1 : 0);
    }

    /*
        Вывод всплывающего окна с диалогом.
     */
    private void createButtonAlertDialog(String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();
    }

    /*
        Создаем четырехзначное значение из уникальных символов
     */
    public static String makeNumber() {
        String str = (int) (Math.random() * 9 ) + "";
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

    /*
        hidden - загаданное значени
        answer - введенное значение
        Проверка наличия введенных символов в загаданном значении. При совпадении позиции символов увеличиваем количество быков, при разных позициях коров.
     */
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

    /*
        Проверка введенного значения на число
     */
    static boolean isNumeric(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
        Создаем алерт с предложением перейти в репозиторий.
        Вызываем метод в link.setOnClickListener
     */
    public void showAlertDialogButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ссылка на GitHub");
        builder.setMessage("https://github.com/1Gregorio1/BullsAndCows");
        builder.setPositiveButton("Перейти",
                //Необходимо доработать(оптимизировать)
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/1Gregorio1/BullsAndCows"));
                        startActivity(browserIntent);
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
