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


    public int deleteTask(Item task) {

        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        String[] args = {String.valueOf(task.getId())};
        return dataBase.delete(TABLE_NAME, "id = ?", args);
    }

    public long newTask(Item task) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForInsert = new ContentValues();
        valuesForInsert.put("name", task.getName());
        valuesForInsert.put("description", task.getDescription());
        valuesForInsert.put("date", task.getDate());
        return dataBase.insert(TABLE_NAME, null, valuesForInsert);
    }

    public int saveChanges(Item taskEdited) {
        SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
        ContentValues valuesForUpdate = new ContentValues();
        valuesForUpdate.put("name", taskEdited.getName());
        valuesForUpdate.put("description", taskEdited.getDescription());
        valuesForUpdate.put("date", taskEdited.getDate());
        String fieldForUpdate = "id = ?";
        String[] argsForUpdate = {String.valueOf(taskEdited.getId())};
        return dataBase.update(TABLE_NAME, valuesForUpdate, fieldForUpdate, argsForUpdate);
    }

    public ArrayList<Item> getTasks() {
        ArrayList<Item> tasks = new ArrayList<>();
        SQLiteDatabase baseDeDatos = dataBaseHelper.getReadableDatabase();
        String[] fields = {"title", "description", "id", "dueDate"};
        Cursor cursor = baseDeDatos.query(
                TABLE_NAME,
                fields,
                null,
                null,
                null,
                null,
                "dueDate"
        );

        if (cursor == null) {
            return tasks;

        }
        if (!cursor.moveToFirst()) return tasks;


        do {
            String title = cursor.getString(0);
            String description = cursor.getString(1);
            long id = cursor.getLong(2);
            String dueDate = cursor.getString(3);
            Item taskGetFromDB = new Item(title, title, title, title, description, dueDate);
            tasks.add(taskGetFromDB);
        } while (cursor.moveToNext());

        cursor.close();
        return tasks;
    }
}
