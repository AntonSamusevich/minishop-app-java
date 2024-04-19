package com.example.minishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    //контекст приложения
    private Context context;
    //список продуктов, отображаемых в списке
    private List<Product> productList;
    //инфлейтер для создания представлений элементов списка из макета
    private LayoutInflater inflater;
    //текстовое поле для отображения количества выбранных элементов
    private TextView itemCount;

    // Конструктор адаптера продуктов
    public ProductAdapter(Context context, List<Product> productList, TextView itemCount) {
        this.context = context;
        this.productList = productList;
        this.inflater = LayoutInflater.from(context);
        this.itemCount = itemCount;
    }

    @Override
    public int getCount() {
        //возвращает количество элементов в списке продуктов
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        //возвращает элемент списка по указанной позиции
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //возвращает идентификатор элемента списка по указанной позиции
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //создание нового представления, если оно еще не было создано
            convertView = inflater.inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.textViewProductId = convertView.findViewById(R.id.id);
            holder.textViewProductName = convertView.findViewById(R.id.name);
            holder.textViewProductPrice = convertView.findViewById(R.id.price);
            holder.checkBoxProductSelected = convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            //использование существующего представления
            holder = (ViewHolder) convertView.getTag();
        }

        //получение продукта на текущей позиции
        final Product product = productList.get(position);
        //установка данных продукта в представления
        holder.textViewProductId.setText(String.valueOf(product.getId()));
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText(String.valueOf(product.getPrice()));
        holder.checkBoxProductSelected.setChecked(product.isSelected());

        //установка обработчика события для чекбокса
        holder.checkBoxProductSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                //обновление состояния выбранного продукта
                product.setSelected(checkBox.isChecked());
                //обновление отображения количества выбранных элементов
                updateItemCountText();
            }
        });

        return convertView;
    }

    //метод для обновления отображения количества выбранных элементов
    public void updateItemCountText() {
        int selectedCount = 0;
        for (Product product : productList) {
            if (product.isSelected()) {
                selectedCount++;
            }
        }
        //установка текста в текстовом поле для отображения количества выбранных элементов
        if (itemCount != null) {
            itemCount.setText("Selected items: " + selectedCount);
        }
    }

    //метод для получения списка выбранных продуктов
    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.isSelected()) {
                selectedProducts.add(product);
            }
        }
        return selectedProducts;
    }

    //внутренний класс ViewHolder для кэширования представлений элемента списка
    private static class ViewHolder {
        TextView textViewProductId;
        TextView textViewProductName;
        TextView textViewProductPrice;
        CheckBox checkBoxProductSelected;
    }
}
