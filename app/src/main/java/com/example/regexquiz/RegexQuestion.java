package com.example.regexquiz;

// a single regex question:
public class RegexQuestion {
    private int ID = 0;
    private String REGEXTEXT;
    private int SOLVED = 0;

    public RegexQuestion() {
        ID = 0;
        REGEXTEXT = "";
    }

    public RegexQuestion(String regexText) {
        REGEXTEXT = regexText;
    }

    public int getID() {
        return ID;
    }

    public String getREGEXTEXT() {
        return REGEXTEXT;
    }

    public void setID(int id) {
        ID = id;
    }

    public void setREGEXTEXT(String regexText) {
        REGEXTEXT = regexText;
    }

    public int getSOLVED() {
        return SOLVED;
    }

    public void setSOLVED(int SOLVED) {
        this.SOLVED = SOLVED;
    }

    public boolean isSolved() { return getSOLVED() == 1; }
}
