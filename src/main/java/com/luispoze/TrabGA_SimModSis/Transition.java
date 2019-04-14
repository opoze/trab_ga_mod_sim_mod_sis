package com.luispoze.TrabGA_SimModSis;

public class Transition extends Vertex {
	
	public Transition(String name) {
		this.name = name;
	}
	
	public Transition()
	{
		this.name = "";
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Transition: " + this.name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setMarcs(int marcs) {
		this.marcs = marcs;
	}
	
}
