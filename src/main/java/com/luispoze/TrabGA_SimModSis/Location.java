package com.luispoze.TrabGA_SimModSis;

public class Location extends Vertex{
	private String name;
	private int marcs;
	public Location(String name, int marcs) {
		this.name = name;
		this.marcs = marcs;
	}
	
	public Location()
	{
		this.name = "";
		this.marcs = 0;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Location: " + this.name +", with "+ String.valueOf(this.marcs) + " marcs.";
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getMarcs() {
		return marcs;
	}
	
	@Override
	public void setMarcs(int marcs) {
		this.marcs = marcs;
	}
	
}
