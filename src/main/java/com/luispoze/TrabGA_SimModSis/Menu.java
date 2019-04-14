package com.luispoze.TrabGA_SimModSis;

import java.util.Scanner;

public class Menu {
	
	Scanner sc;
	
	public Menu(Core core)
	{
	    sc = new Scanner(System.in);
	    String option = "-1";
	    do
	    {
	    	option = menu(sc);
	    	switch(option) {
	    		case "L": {
	    			location(sc, core);
	    			break;
	    		}
	    		case "T": {
	    			transition(sc, core);
	    			break;
	    		}
	    		case "A": {
	    			arc(sc, core);
	    			break;
	    		}
	    		case "S": {
	    			saveXml(core);
	    			break;
	    		}
	    		case "X": {
	    			fromXml(core);
	    			break;
	    		}
	    		case "P": {
	    			process(sc, core);
	    			break;
	    		}
	    		
	    	}
	    }
	    while(!option.equals("0"));
	}
	
	private void fromXml(Core core)
	{
		Xml xml = new Xml();
		xml.load(core);	
	}
	
	private void saveXml(Core core)
	{
		Xml xml = new Xml();
		xml.save(core.getGraph());
	}
	
	private void clear()
	{
		
//		System.out.print("\033[H\033[2J");  
//	    System.out.flush(); 
//		try {
//			Runtime.getRuntime().exec("cls");
//		}
//		catch(Exception e) {
//			System.out.println("Error");
//		}
	}
	
	private String menu(Scanner sc)
	{
		clear();
		System.out.println("=====Menu RDP=====");
		System.out.println("L - Localização");
		System.out.println("T - Transição");
		System.out.println("A - Arco");
		System.out.println("S - Salvar XML");
		System.out.println("X - Ler XML");
		System.out.println("P - Processar RDP");
		System.out.println("0 - Fim");
		System.out.println("=====Menu=====");
		System.out.print("Escolha: ");
		return sc.nextLine();
	}
	
	private void location(Scanner sc, Core core) 
	{
		clear();
		System.out.println("Nova Localização");
		System.out.print("Nome: ");
		String name = sc.next();
		System.out.print("Marcas: ");
		int marcs = sc.nextInt();
		core.addLocation(name, marcs);
	}
	
	private void transition(Scanner sc, Core core) 
	{
		clear();
		System.out.println("Nova Transição");
		System.out.print("Nome: ");
		String name = sc.next();
		core.addTransition(name);
	}
	
	private void arc(Scanner sc, Core core) 
	{
		clear();
		System.out.println("Novo Arco");
		System.out.print("De: ");
		String from = sc.next();
		System.out.print("Para: ");
		String to = sc.next();
		System.out.print("Peso: ");
		int weight = sc.nextInt();
		core.addArc(from, to, weight);
	}
	
	private void process(Scanner sc, Core core)
	{
		clear();
	    System.out.print("Continue? ");
	    while(sc.next() != "n" && core.canProcess()) {
	    	core.process();
	    	core.print();
	    }
	}


}
