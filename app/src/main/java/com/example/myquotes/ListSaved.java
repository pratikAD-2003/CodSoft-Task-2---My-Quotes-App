package com.example.myquotes;

import static com.example.myquotes.Database.Database.DB_TABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.myquotes.Database.Database;
import com.example.myquotes.Database.QuoteModel;
import com.example.myquotes.databinding.ActivityListSavedBinding;

import java.util.ArrayList;

public class ListSaved extends AppCompatActivity {
    ActivityListSavedBinding binding;
    QuoteAdapter adapter;
    ArrayList<QuoteModel> list;
    SQLiteDatabase sqLiteDatabase;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListSavedBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        list = new ArrayList<>();
        database = new Database(this);

        binding.quoteRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.quoteRecyclerview.setHasFixedSize(true);
        adapter = new QuoteAdapter(this, list, new QuoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(QuoteModel item) {
                ListSaved.this.recreate();
            }
        });
        binding.quoteRecyclerview.setAdapter(adapter);

        loadData();

    }

    private void loadData() {
        sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DB_TABLE + "", null);
        while (cursor.moveToNext()) {
            int i = 0;
            int id = cursor.getInt(0);
            String quote = cursor.getString(1);
            list.add(new QuoteModel(id, quote));
        }
        adapter.notifyDataSetChanged();

        if (!list.isEmpty()) {
            binding.emptyList.setVisibility(View.INVISIBLE);
        } else {
            binding.emptyList.setVisibility(View.VISIBLE);
        }
    }
}