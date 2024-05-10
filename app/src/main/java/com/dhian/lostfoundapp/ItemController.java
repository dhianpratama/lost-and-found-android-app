package com.dhian.lostfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;



public class ItemController {
    private DataBaseHelper dataBaseHelper;
    private String TABLE_NAME = "items";

    public ItemController(Context contexto) {
        dataBaseHelper = new DataBaseHelper(contexto);
    }

    public long newItem(Item task) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForInsert = new ContentValues();
        valuesForInsert.put("name", task.getName());
        valuesForInsert.put("phone", task.getPhone());
        valuesForInsert.put("description", task.getDescription());
        valuesForInsert.put("post_type", task.getPost_type());
        valuesForInsert.put("date", task.getDate());
        valuesForInsert.put("location", task.getLocation());
        return dataBase.insert(TABLE_NAME, null, valuesForInsert);
    }

    public int deleteItem(String id) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        return dataBase.delete(TABLE_NAME, "name = ?", args);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> tasks = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dataBaseHelper.getReadableDatabase();
        String[] fields = {"name", "phone", "description", "id", "date", "location", "post_type"};
        Cursor cursor = baseDeDatos.query(
                TABLE_NAME,
                fields,
                null,
                null,
                null,
                null,
                "date"
        );

        if (cursor == null) {
            return tasks;

        }
        if (!cursor.moveToFirst()) return tasks;


        do {
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            String description = cursor.getString(2);
            long id = cursor.getLong(3);
            String date = cursor.getString(4);
            String location = cursor.getString(5);
            String post_type = cursor.getString(6);
            Item taskGetFromDB = new Item(id, name, post_type, phone, description, date, location);
            tasks.add(taskGetFromDB);
        } while (cursor.moveToNext());

        cursor.close();
        return tasks;
    }
}
