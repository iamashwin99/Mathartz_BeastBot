package com.beastbot.list;

import java.util.List;

public class FormulaData {

	int id;  
	String fname, st,mt,et;  
	double x,y;
	int lcount,round,qty;  
	Boolean tradeswitch;
	public FormulaData(int id, String fname, double x, double y, String st,String mt,String et, int lcount,int round, int qty, Boolean tradeswitch) {  
	    this.id = id;
	    this.fname = fname;
	    this.x = x;  
	    this.y = y;   
	    this.st = st;  
	    this.mt = mt;  
	    this.et = et;  
	    this.lcount = lcount;  
	    this.round = round;
	    this.qty = qty;
	    this.tradeswitch=tradeswitch;
	    
	}
	
	public int getId() {
        return id;
    }
	public String getfname() {
        return fname;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getLcount() {
        return lcount;
    }
    public double getRound() {
        return round;
    }
    public String getST() {
        return st;
    }
    public String getMT() {
        return mt;
    }
    public String getET() {
        return et;
    }
    public int getQty() {
        return qty;
    }
    public Boolean getTradeswitch() {
        return tradeswitch;
    }

}
