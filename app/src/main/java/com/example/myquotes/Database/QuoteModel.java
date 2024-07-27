package com.example.myquotes.Database;

public class QuoteModel {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QuoteModel() {
    }

    public String getQuote() {
        return quote;
    }

    public QuoteModel(int id, String quote) {
        this.id = id;
        this.quote = quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    String quote;
}
