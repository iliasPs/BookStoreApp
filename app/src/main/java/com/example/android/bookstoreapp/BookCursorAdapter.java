package com.example.android.bookstoreapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.bookstoreapp.data.BookContract.BookEntry;

public class BookCursorAdapter extends CursorAdapter {


    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

// Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView supplierName = (TextView) view.findViewById(R.id.summary);
        TextView inStockTextVIew = (TextView) view.findViewById(R.id.stock);


        // Find the columns of book attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_QUANTITY);
        int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_BOOK_SUPPLIER);

        // Read the book attributes from the Cursor for the current book
        String bookName = cursor.getString(nameColumnIndex);
        final int inStock = cursor.getInt(quantityColumnIndex);
        String supName = cursor.getString(supplierColumnIndex);


        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        supplierName.setText(supName);
        if (inStock == 0) {
            inStockTextVIew.setText(R.string.stock_availability_no);
        } else {
            inStockTextVIew.setText(R.string.stock_availability_yes);
        }

        final long id = cursor.getInt(cursor.getColumnIndexOrThrow(BookEntry._ID));

        view.findViewById(R.id.sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) context;
                ma.saleDone(id, inStock);
            }
        });
    }

}

