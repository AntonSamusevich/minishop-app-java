package com.example.minishop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

public class CheckedAdapter extends BaseAdapter {

    //контекст приложения
    private Context context;
    //список продуктов, отображаемых в списке
    private List<Product> selectedProducts;
    //инфлейтер для создания представлений элементов списка из макета
    private LayoutInflater inflater;

    //конструктор адаптера
    public CheckedAdapter(Context context, List<Product> selectedProducts) {
        this.context = context;
        this.selectedProducts = selectedProducts;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //возвращает количество выбранных продуктов
        return selectedProducts.size();
    }

    @Override
    public Object getItem(int position) {
        //возвращает продукт по указанной позиции
        return selectedProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        //возвращает идентификатор элемента списка
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product, parent, false);
            holder = new ViewHolder();
            holder.textViewProductId = convertView.findViewById(R.id.id);
            holder.textViewProductName = convertView.findViewById(R.id.name);
            holder.textViewProductPrice = convertView.findViewById(R.id.price);
            holder.checkBoxProductSelected = convertView.findViewById(R.id.checkBox); //получаем CheckBox
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //получаем текущий продукт
        Product product = selectedProducts.get(position);

        //устанавливаем данные продукта в соответствующие TextView
        holder.textViewProductId.setText(String.valueOf(product.getId()));
        holder.textViewProductName.setText(product.getName());
        holder.textViewProductPrice.setText(String.valueOf(product.getPrice()));

        //если контекст является SecondActivity, скрываем CheckBox,
        if (context instanceof SecondActivity) {
            holder.checkBoxProductSelected.setVisibility(View.GONE);
        } else {
            holder.checkBoxProductSelected.setChecked(product.isSelected());
            holder.checkBoxProductSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox checkBox = (CheckBox) v;
                    product.setSelected(checkBox.isChecked());
                }
            });
        }

        return convertView;
    }

    //внутренний класс ViewHolder для удерживания представлений элементов списка
    private static class ViewHolder {
        TextView textViewProductId;
        TextView textViewProductName;
        TextView textViewProductPrice;
        CheckBox checkBoxProductSelected;
    }

    //метод для вычисления общей стоимости выбранных продуктов
    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Product product : selectedProducts) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(product.getPrice()));
        }
        return totalPrice;
    }
}