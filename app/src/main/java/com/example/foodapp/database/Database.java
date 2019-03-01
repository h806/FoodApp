package com.example.foodapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "eatDB";
    private static final int    DB_VER  = 1;


    public Database(Context context) {
        super(context, DB_NAME,null,DB_VER);
    }

    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"productName", "productId", "Quantity", "price", "discount"};
        String sqlTable = "orderDetail";

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result = new ArrayList<>();

        if (cursor.moveToFirst()){
            do {
                result.add(new Order(cursor.getString(cursor.getColumnIndex("productId")),
                        cursor.getString(cursor.getColumnIndex("productName")),
                        cursor.getString(cursor.getColumnIndex("Quantity")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("discount"))));

            }while (cursor.moveToNext());

        }
        return result;
    }

    public void AddToCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("INSERT INTO orderDetail(productId, productName,Quantity, price, discount)  " +
                " VALUES('%s', '$s', '$s', '$s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount());
        db.execSQL(query);
    }

    public void CleanCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query  = String.format("DELETE FROM orderDetail");
        db.execSQL(query);
    }
}
