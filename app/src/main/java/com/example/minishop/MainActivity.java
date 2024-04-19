package com.example.minishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Product> productList; //список продуктов
    private ProductAdapter adapter; //адаптер для списка продуктов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView countItem = findViewById(R.id.countItem); //текст для отображения количества
        Button showButton = findViewById(R.id.showButton); //кнопка просмотра продуктов

        productList = new ArrayList<>();
        productList.add(new Product(1, "Молоко", 2.08));
        productList.add(new Product(2, "Хлеб", 0.86));
        productList.add(new Product(3, "Яйца", 3.57));
        productList.add(new Product(4, "Сахар", 2.69));
        productList.add(new Product(5, "Масло", 3.4));
        productList.add(new Product(6, "Мука", 2.99));
        productList.add(new Product(7, "Творог", 1.79));
        productList.add(new Product(8, "Соль", 0.75));
        productList.add(new Product(9, "Кофе", 8.49));
        productList.add(new Product(10, "Чай", 3.19));

        loadCheckedProductsState(); //загрузка состояния выбранных продуктов

        ListView listViewProducts = findViewById(R.id.listView); //список для отображения списка
        adapter = new ProductAdapter(this, productList, countItem); //создание адаптера для списка продуктов
        listViewProducts.setAdapter(adapter); //установка адаптера

        adapter.updateItemCountText(); //обновление количества продуктов

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> selectedProducts = adapter.getSelectedProducts(); //получение выбранных продуктов из адаптера
                if (selectedProducts.isEmpty()) {
                    // если ни один элемент не выбран
                    Toast.makeText(MainActivity.this, "Choose at least one product", Toast.LENGTH_SHORT).show();
                } else {
                    //переход на новую страницу
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putParcelableArrayListExtra("selectedProducts", new ArrayList<>(selectedProducts));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // сохраняем состояние выбранных продуктов
        saveCheckedProductsState();
    }

    //метод для сохранения состояния выбранных продуктов
    private void saveCheckedProductsState() {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckedProducts", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Product product : productList) {
            //сохраняем состояние каждого продукта в SharedPreferences
            editor.putBoolean("product_" + product.getId(), product.isSelected());
        }
        editor.apply();
    }

    //метод для загрузки состояния выбранных продуктов
    private void loadCheckedProductsState() {
        SharedPreferences sharedPreferences = getSharedPreferences("CheckedProducts", MODE_PRIVATE);
        for (Product product : productList) {
            //загружаем состояние каждого продукта из SharedPreferences
            boolean isSelected = sharedPreferences.getBoolean("product_" + product.getId(), false);
            product.setSelected(isSelected);
        }
    }
}
