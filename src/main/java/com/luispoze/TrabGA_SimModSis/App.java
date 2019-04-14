package com.luispoze.TrabGA_SimModSis;

import java.io.IOException;
import javax.swing.JFrame;
import org.jgrapht.io.ExportException;
import java.util.Scanner;

public class App{
	
	private Core core = new Core();
	
	public void init()
	{
		Menu menu = new Menu(core);
	}

	public static void main(String[] args) throws ExportException, IOException{
		App app = new App();
		app.init();
	}
	
}
