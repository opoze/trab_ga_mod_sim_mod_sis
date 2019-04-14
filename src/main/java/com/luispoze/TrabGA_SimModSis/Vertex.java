package com.luispoze.TrabGA_SimModSis;

public abstract class Vertex {
	protected String name;
	protected int marcs;
	
	public String getName() {
		return "Vertex";
	}
	
	public int getMarcs() {
		return 0;
	}
	
	public void setMarcs(int marcs) {
		this.marcs = marcs;
	}
}
