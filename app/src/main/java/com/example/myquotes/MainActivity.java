package com.example.myquotes;

import static com.example.myquotes.Database.Database.DB_TABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.myquotes.Database.Database;
import com.example.myquotes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<String> quotes;
    int currentNumber = 0;

    Database database;
    SQLiteDatabase sqLiteDatabase;
    boolean isSave = false;
    int temId = 0;
    String temQuote = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        database = new Database(MainActivity.this);
        addQuotes();

        temId = getIntent().getIntExtra("id", 0);
        temQuote = getIntent().getStringExtra("quote");
        if (temId != 0 && !temQuote.isEmpty()) {
            currentNumber = temId;
            binding.quoteText.setText(quotes.get(temId));
            binding.saveBtn.setImageDrawable(getDrawable(R.drawable.white_heart));
            binding.refreshLayout.setVisibility(View.GONE);
            binding.listLayout.setVisibility(View.GONE);
            binding.saveLayout.setVisibility(View.GONE);
        }
        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
                binding.quoteText.setText(quotes.get(generateRandom()));
                binding.quoteText.startAnimation(animation);
                binding.saveBtn.setImageDrawable(getDrawable(R.drawable.heart));
                isSave = false;
            }
        });

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "My Quotes ~ \n" + quotes.get(currentNumber));
                startActivity(Intent.createChooser(intent, "Select App to share Quote"));
            }
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSave) {
                    saveQuote();
                    binding.saveBtn.setImageDrawable(getDrawable(R.drawable.white_heart));
                    isSave = true;
                } else {
                    deleteSaveQuote();
                    binding.saveBtn.setImageDrawable(getDrawable(R.drawable.heart));
                    isSave = false;
                }
            }
        });

        binding.listSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListSaved.class));
            }
        });

    }

    private void addQuotes() {
        quotes = new ArrayList<>();
        quotes.add("The greatest glory in living lies not in never falling, but in rising every time we fall. - Nelson Mandela");
        quotes.add("The way to get started is to quit talking and begin doing. - Walt Disney");
        quotes.add("Your time is limited, don't waste it living someone else's life. - Steve Jobs");
        quotes.add("If life were predictable it would cease to be life, and be without flavor. - Eleanor Roosevelt");
        quotes.add("If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough. - Oprah Winfrey");
        quotes.add("Life is what happens when you're busy making other plans. - John Lennon");
        quotes.add("Spread love everywhere you go. Let no one ever come to you without leaving happier. - Mother Teresa");
        quotes.add("When you reach the end of your rope, tie a knot in it and hang on. - Franklin D. Roosevelt");
        quotes.add("Always remember that you are absolutely unique. Just like everyone else. - Margaret Mead");
        quotes.add("Don't judge each day by the harvest you reap but by the seeds that you plant. - Robert Louis Stevenson");
        quotes.add("The future belongs to those who believe in the beauty of their dreams. - Eleanor Roosevelt");
        quotes.add("Tell me and I forget. Teach me and I remember. Involve me and I learn. - Benjamin Franklin");
        quotes.add("The best and most beautiful things in the world cannot be seen or even touched - they must be felt with the heart. - Helen Keller");
        quotes.add("It is during our darkest moments that we must focus to see the light. - Aristotle");
        quotes.add("Whoever is happy will make others happy too. - Anne Frank");
        quotes.add("Do not go where the path may lead, go instead where there is no path and leave a trail. - Ralph Waldo Emerson");
        quotes.add("You will face many defeats in life, but never let yourself be defeated. - Maya Angelou");
        quotes.add("In the end, it's not the years in your life that count. It's the life in your years. - Abraham Lincoln");
        quotes.add("Never let the fear of striking out keep you from playing the game. - Babe Ruth");
        quotes.add("Life is either a daring adventure or nothing at all. - Helen Keller");
        quotes.add("Many of life's failures are people who did not realize how close they were to success when they gave up. - Thomas A. Edison");
        quotes.add("You have within you right now, everything you need to deal with whatever the world can throw at you. - Brian Tracy");
        quotes.add("Believe you can and you're halfway there. - Theodore Roosevelt");
        quotes.add("The only impossible journey is the one you never begin. - Tony Robbins");
        quotes.add("The purpose of our lives is to be happy. - Dalai Lama");
        quotes.add("Life is really simple, but we insist on making it complicated. - Confucius");
        quotes.add("May you live all the days of your life. - Jonathan Swift");
        quotes.add("Life itself is the most wonderful fairy tale. - Hans Christian Andersen");
        quotes.add("Do not let making a living prevent you from making a life. - John Wooden");
        quotes.add("Life is ours to be spent, not to be saved. - D. H. Lawrence");
        quotes.add("Keep smiling, because life is a beautiful thing and there's so much to smile about. - Marilyn Monroe");
        quotes.add("Life is a long lesson in humility. - James M. Barrie");
        quotes.add("In three words I can sum up everything I've learned about life: it goes on. - Robert Frost");
        quotes.add("Love the life you live. Live the life you love. - Bob Marley");
        quotes.add("Life is made of ever so many partings welded together. - Charles Dickens");
        quotes.add("Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work. - Steve Jobs");
        quotes.add("My mission in life is not merely to survive, but to thrive; and to do so with some passion, some compassion, some humor, and some style. - Maya Angelou");
        quotes.add("Success is not final, failure is not fatal: It is the courage to continue that counts. - Winston Churchill");
        quotes.add("Success usually comes to those who are too busy to be looking for it. - Henry David Thoreau");
        quotes.add("The way to get started is to quit talking and begin doing. - Walt Disney");
        quotes.add("Don't be distracted by criticism. Remember -- the only taste of success some people get is to take a bite out of you. - Zig Ziglar");
        quotes.add("Success seems to be connected with action. Successful people keep moving. They make mistakes but they don't quit. - Conrad Hilton");
        quotes.add("If you really look closely, most overnight successes took a long time. - Steve Jobs");
        quotes.add("The real test is not whether you avoid this failure, because you won't. It's whether you let it harden or shame you into inaction, or whether you learn from it; whether you choose to persevere. - Barack Obama");
        quotes.add("The only limit to our realization of tomorrow is our doubts of today. - Franklin D. Roosevelt");
        quotes.add("The only place where success comes before work is in the dictionary. - Vidal Sassoon");
        quotes.add("Do what you can with all you have, wherever you are. - Theodore Roosevelt");
        quotes.add("You are never too old to set another goal or to dream a new dream. - C.S. Lewis");
        quotes.add("Reading is to the mind, as exercise is to the body. - Brian Tracy");
        quotes.add("The purpose of life is not to be happy. It is to be useful, to be honorable, to be compassionate, to have it make some difference that you have lived and lived well. - Ralph Waldo Emerson");
        quotes.add("You have brains in your head. You have feet in your shoes. You can steer yourself any direction you choose. - Dr. Seuss");
        quotes.add("The only way to do great work is to love what you do. - Steve Jobs");
        quotes.add("The purpose of our lives is to be happy. - Dalai Lama");
        quotes.add("Life is what happens when you're busy making other plans. - John Lennon");
        quotes.add("Get busy living or get busy dying. - Stephen King");
        quotes.add("You only live once, but if you do it right, once is enough. - Mae West");
        quotes.add("Many of life’s failures are people who did not realize how close they were to success when they gave up. - Thomas A. Edison");
        quotes.add("If you want to live a happy life, tie it to a goal, not to people or things. - Albert Einstein");
        quotes.add("Never let the fear of striking out keep you from playing the game. - Babe Ruth");
        quotes.add("Money and success don’t change people; they merely amplify what is already there. - Will Smith");
        quotes.add("Your time is limited, don’t waste it living someone else’s life. - Steve Jobs");
        quotes.add("Not how long, but how well you have lived is the main thing. - Seneca");
        quotes.add("If life were predictable it would cease to be life, and be without flavor. - Eleanor Roosevelt");
        quotes.add("The whole secret of a successful life is to find out what is one’s destiny to do, and then do it. - Henry Ford");
        quotes.add("In order to write about life first you must live it. - Ernest Hemingway");
        quotes.add("The big lesson in life, baby, is never be scared of anyone or anything. - Frank Sinatra");
        quotes.add("Sing like no one’s listening, love like you’ve never been hurt, dance like nobody’s watching, and live like it’s heaven on earth. - Mark Twain");
        quotes.add("Curiosity about life in all of its aspects, I think, is still the secret of great creative people. - Leo Burnett");
        quotes.add("Life is not a problem to be solved, but a reality to be experienced. - Soren Kierkegaard");
        quotes.add("The unexamined life is not worth living. - Socrates");
        quotes.add("Turn your wounds into wisdom. - Oprah Winfrey");
        quotes.add("The way I see it, if you want the rainbow, you gotta put up with the rain. - Dolly Parton");
        quotes.add("Do all the good you can, for all the people you can, in all the ways you can, as long as you can. - Hillary Clinton");
        quotes.add("Don’t settle for what life gives you; make life better and build something. - Ashton Kutcher");
        quotes.add("Everything negative – pressure, challenges – is all an opportunity for me to rise. - Kobe Bryant");
        quotes.add("I like criticism. It makes you strong. - LeBron James");
        quotes.add("You never really learn much from hearing yourself speak. - George Clooney");
        quotes.add("Life imposes things on you that you can’t control, but you still have the choice of how you’re going to live through this. - Celine Dion");
        quotes.add("Life is never easy. There is work to be done and obligations to be met – obligations to truth, to justice, and to liberty. - John F. Kennedy");
        quotes.add("Live for each second without hesitation. - Elton John");
        quotes.add("Life is like riding a bicycle. To keep your balance, you must keep moving. - Albert Einstein");
        quotes.add("Life is really simple, but men insist on making it complicated. - Confucius");
        quotes.add("Life is a succession of lessons which must be lived to be understood. - Ralph Waldo Emerson");
        quotes.add("My mama always said, life is like a box of chocolates. You never know what you’re gonna get. - Forrest Gump");
        quotes.add("Watch your thoughts; they become words. Watch your words; they become actions. Watch your actions; they become habits. Watch your habits; they become character. Watch your character; it becomes your destiny. - Lao-Tze");
        quotes.add("When we do the best we can, we never know what miracle is wrought in our life or the life of another. - Helen Keller");
        quotes.add("The healthiest response to life is joy. - Deepak Chopra");
        quotes.add("Life is short, and it is up to you to make it sweet. - Sarah Louise Delany");
        quotes.add("Do not dwell in the past, do not dream of the future, concentrate the mind on the present moment. - Buddha");
        quotes.add("Life can only be understood backwards; but it must be lived forwards. - Soren Kierkegaard");
        quotes.add("Life is like a coin. You can spend it any way you wish, but you only spend it once. - Lillian Dickson");
        quotes.add("The best thing to hold onto in life is each other. - Audrey Hepburn");
        quotes.add("When you cease to dream you cease to live. - Malcolm Forbes");
        quotes.add("Good friends, good books, and a sleepy conscience: this is the ideal life. - Mark Twain");
        quotes.add("Life would be tragic if it weren’t funny. - Stephen Hawking");
        quotes.add("Live in the sunshine, swim the sea, drink the wild air. - Ralph Waldo Emerson");
        quotes.add("The greatest pleasure of life is love. - Euripides");
        quotes.add("Life is what we make it, always has been, always will be. - Grandma Moses");
        quotes.add("Life’s tragedy is that we get old too soon and wise too late. - Benjamin Franklin");
        quotes.add("Life is about making an impact, not making an income. - Kevin Kruse");

        binding.quoteText.setText(quotes.get(generateRandom()));
    }

    private int generateRandom() {
        Random random = new Random();
        int min = 1;
        int max = 100;
        currentNumber = random.nextInt((max - min)) + min;
//        Log.d("Chekc", String.valueOf(currentNumber));
        return currentNumber;
    }

    private void saveQuote() {
        ContentValues cv = new ContentValues();
        cv.put("id", currentNumber);
        cv.put("quote", quotes.get(currentNumber));

        sqLiteDatabase = database.getWritableDatabase();
        Long checkInsert = sqLiteDatabase.insert(DB_TABLE, null, cv);
        if (checkInsert != null) {
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            Log.d("VDFD", "save");
        }
    }

    private void deleteSaveQuote() {
        sqLiteDatabase = database.getReadableDatabase();
        long delete = sqLiteDatabase.delete(DB_TABLE, "id=" + currentNumber, null);
        if (delete != -1) {
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            Log.d("VDFD", "delete");
        }
    }
}