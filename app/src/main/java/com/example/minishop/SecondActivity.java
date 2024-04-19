package com.example.minishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //получение списка выбранных продуктов из предыдущей активности
        List<Product> selectedProducts = getIntent().getParcelableArrayListExtra("selectedProducts");

        //нахождение ListView в макете активности
        ListView selectedProductsListView = findViewById(R.id.listViewSecond);

        //создание адаптера для списка выбранных продуктов и установка его в ListView
        CheckedAdapter adapter = new CheckedAdapter(this, selectedProducts);
        selectedProductsListView.setAdapter(adapter);

        //получение суммы выбранных продуктов из адаптера
        BigDecimal totalPrice = adapter.calculateTotalPrice();

        //установка суммы в заголовок активности
        TextView headerTextView = findViewById(R.id.header);
        headerTextView.setText("Sum of products: " + totalPrice + " руб.");

        //нахождение кнопки "назад" в макете и установка обработчика клика
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //создание намерения для возврата к предыдущей активности (MainActivity)
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
