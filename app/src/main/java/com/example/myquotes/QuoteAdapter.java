package com.example.myquotes;

import static com.example.myquotes.Database.Database.DB_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquotes.Database.Database;
import com.example.myquotes.Database.QuoteModel;
import com.example.myquotes.databinding.QuoteItemBinding;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyHolder> {
    Context context;
    ArrayList<QuoteModel> list = new ArrayList<>();
    QuoteItemBinding binding;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(QuoteModel item);
    }

    public QuoteAdapter(Context context, ArrayList<QuoteModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = QuoteItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        binding.itemText.setText(list.get(position).getQuote());

        binding.saveItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.isSave) {
                    holder.saveQuote(list.get(position).getId(), list.get(position).getQuote());
                    binding.saveItemBtn.setImageDrawable(context.getDrawable(R.drawable.white_heart));
                    holder.isSave = true;
                } else {
                    holder.deleteSaveQuote(list.get(position).getId(), listener);
                    binding.saveItemBtn.setImageDrawable(context.getDrawable(R.drawable.heart));
                    holder.isSave = false;
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("quote", list.get(position).getQuote());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        QuoteItemBinding binding;
        SQLiteDatabase sqLiteDatabase;
        Database database;
        boolean isSave = true;

        public MyHolder(@NonNull QuoteItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        private void saveQuote(int id, String quotes) {
            ContentValues cv = new ContentValues();
            cv.put("id", id);
            cv.put("quote", quotes);

            sqLiteDatabase = database.getWritableDatabase();
            Long checkInsert = sqLiteDatabase.insert(DB_TABLE, null, cv);
            if (checkInsert != null) {
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show();
                Log.d("VDFD", "save");
            }
        }

        private void deleteSaveQuote(int id, OnItemClickListener listener) {
            database = new Database(context);
            sqLiteDatabase = database.getReadableDatabase();
            long delete = sqLiteDatabase.delete(DB_TABLE, "id=" + id, null);
            if (delete != -1) {
                Toast.makeText(context, "Removed!", Toast.LENGTH_SHORT).show();
                Log.d("VDFD", "delete");
                listener.onItemClick(new QuoteModel());
                notifyDataSetChanged();
            }
        }
    }
}
