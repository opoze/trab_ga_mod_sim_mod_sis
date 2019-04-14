package com.luispoze.TrabGA_SimModSis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.traverse.DepthFirstIterator;

public class Core {
	private Graph<Vertex, DefaultWeightedEdge> g;
	private Location l;
	private Transition t;
	private List<List<String>> ciclos = new ArrayList<List<String>>();
	private boolean canProcess;
	
	public Core()
	{
		canProcess = true;
        g = GraphTypeBuilder
                .<Vertex, DefaultWeightedEdge> directed()
                .allowingMultipleEdges(true)
                .allowingSelfLoops(false)
                .edgeClass(DefaultWeightedEdge.class)
                .weighted(true).buildGraph();
	}
	
	public boolean canProcess()
	{
		return canProcess;
	}
	
	public boolean addLocation(String name, int marcs) {
		l = new Location(name, marcs);
		Vertex v = findVertex(name);
		if(v != null) {
			return false;
		}
		canProcess = true;
		g.addVertex(l);
		System.out.println("Added location " + name + " with " + String.valueOf(marcs) + " marcs");	
		return true;
	}
	
	public boolean addTransition(String name){
		t = new Transition(name);
		Vertex v = findVertex(name);
		if(v != null) {
			return false;
		}
		canProcess = true;
		g.addVertex(t);
		System.out.println("Added trantition " + name);
		return true;
	}
	
	private Vertex findVertex(String name) {
		Vertex v = null;
		try {
			v = g.vertexSet()
	            .stream()
	            .filter(vertex -> vertex.getName().equals(name))
	            .findFirst()
	            .get();
		}
		catch(Exception e) {
			return v;
		}
		return v;
	}
	
	public boolean addArc(String from, String to, int weight) {	
		Vertex f = findVertex(from);
		Vertex t = findVertex(to);
		
		if(f == null) {
			return false;
		}
		if(t == null) {
			return false;
		}
		canProcess = true;
		DefaultWeightedEdge e = g.addEdge(f,  t);
		g.setEdgeWeight(e, weight);
		
		System.out.println("Added arc from " + from + " to " + to + " with " + String.valueOf(weight) + " weight");
		return true;
	}
    
    private void mapActualState()
    {
    	List<String> out = new ArrayList<String>();
    	double weight = 0;
    	String hab = "S";
    	Vertex aux; 
    	Iterator<Vertex> iterator = new DepthFirstIterator<>(g);
    	canProcess = false;
        while (iterator.hasNext()) {
            Vertex v = iterator.next();
            if(v.getClass() == Transition.class) {
            	Set<DefaultWeightedEdge> set = g.incomingEdgesOf(v);
            	hab = "S";
            	for (DefaultWeightedEdge  Edge : set) {
            		weight = g.getEdgeWeight(Edge);
            		aux = g.getEdgeSource(Edge);
            		if(aux.getMarcs() - weight < 0) {
            			hab = "N";
            		}
            	}
            	if(hab.equals("S")) {
            		canProcess = true;
            	}
            	out.add(hab);
            }
            if(v.getClass() == Location.class) {
            	out.add(String.valueOf(v.getMarcs()));
            }
        }
        ciclos.add(out);
    }
    
    public void print()
    {
    	
    	// Print Header
		Iterator<Vertex> iterator = new DepthFirstIterator<>(g);
		String header1 = "+-------+";
		String header2 = "| Ciclo |"; 
        while (iterator.hasNext()) {
            Vertex v = iterator.next();
            header1 += "-------+";
			header2 += String.format("%7s|", v.getName());
        }
        System.out.println(header1);
        System.out.println(header2);
        System.out.println(header1);
        
        String out = "";
        int i = 1;
        for (List<String>  lista : ciclos) {
        	out = String.format("|%7d|", i);
        	for(String txt : lista) {
        		out += String.format("%7s|", txt);
        	}
        	System.out.println(out);
        	i++;
        }
        System.out.println(header1);

    }
    
    public void process()
    {	
    	if(!canProcess) {
    		return;
    	}
    	boolean hab = false;
    	Vertex vertex = t = null;
    	Set<DefaultWeightedEdge> incommingEdges, outgoingEdges;
    	Iterator<Vertex> iterator = new DepthFirstIterator<>(g);    	
        while (iterator.hasNext()) {
            vertex = iterator.next();
            if(vertex.getClass() == Transition.class) {
            	hab = true;
            	// Edges de entrada
            	incommingEdges = g.incomingEdgesOf(vertex);
            	// Veritica se a transicao esta habilitada
            	for (DefaultWeightedEdge  Edge : incommingEdges) {
            		if(g.getEdgeSource(Edge).getMarcs() - g.getEdgeWeight(Edge) < 0) {
            			hab = false;
            		}
            	}
            	// Se a transicao estiver habilitata
            	if(hab) {
            		// remover marcas dos vertices de origem
            		for (DefaultWeightedEdge  Edge : incommingEdges) {
            			g.getEdgeSource(Edge).setMarcs(g.getEdgeSource(Edge).getMarcs() - (int)g.getEdgeWeight(Edge));	
            		}
            		// adiciona marcas nos vertices de destino
            		outgoingEdges = g.outgoingEdgesOf(vertex);
            		for (DefaultWeightedEdge  Edge : outgoingEdges) {
            			System.out.println("De" + g.getEdgeTarget(Edge).getMarcs() + " para: " + (g.getEdgeTarget(Edge).getMarcs() + (int)g.getEdgeWeight(Edge)));
            			
            			
            			
            			g.getEdgeTarget(Edge).setMarcs(g.getEdgeTarget(Edge).getMarcs() + (int)g.getEdgeWeight(Edge));
            		}
            	}
            }
        }
        mapActualState();
    }

	public Graph<Vertex, DefaultWeightedEdge> getGraph()
	{
		return this.g;
	}
}
