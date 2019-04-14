package com.luispoze.TrabGA_SimModSis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import com.thoughtworks.xstream.XStream;

public class Xml {
	
	private Graph<Vertex, DefaultWeightedEdge> g;
	private Save s;
	private	XStream xstream;

	public Xml()
	{
		xstream = new XStream();
		XStream xstream = new XStream();
		s = new Save();
		    
	}

	public void load(Core c)
	{
		xstream.alias("data", Save.class);
		xstream.alias("location", L.class);
		xstream.alias("transition", T.class);
		xstream.alias("arc", A.class);
	    try{
	        FileInputStream xmlFile = new FileInputStream(new File("saved.xml"));     
	        s = (Save) xstream.fromXML(xmlFile);
	        
        	for (L  l : s.locations) {
        		c.addLocation(l.name, l.marcs);
        	}
        	
        	for (T  t : s.transitions) {
        		c.addTransition(t.name);
        	}
        	
        	for (A  a : s.arcs) {
        		c.addArc(a.from, a.to, a.weight);
        	}
        	
        	System.out.println("Loadef from file: saved.xml");
	    }catch(Exception e){
	        System.err.println("Error in XML Read: " + e.getMessage());
	    }
	}	
	
	public void save(Graph<Vertex, DefaultWeightedEdge> g)
	{
		Iterator<Vertex> iterator = new DepthFirstIterator<>(g);
    	Set<DefaultWeightedEdge> incommingEdges, outgoingEdges;

    	String from, to;
    	int weight;
    	
        while (iterator.hasNext()) {
            Vertex v = iterator.next();
            if(v.getClass() == Transition.class) {
            	incommingEdges = g.incomingEdgesOf(v);
        		outgoingEdges = g.outgoingEdgesOf(v);
        		
            	for (DefaultWeightedEdge  Edge : incommingEdges) {
            		weight = (int)g.getEdgeWeight(Edge);
            		from =  g.getEdgeSource(Edge).getName();
            		s.arcs.add(new A(from, v.getName(), weight));
            	}
        		for (DefaultWeightedEdge  Edge : outgoingEdges) {
        			weight = (int)g.getEdgeWeight(Edge);
            		to =  g.getEdgeTarget(Edge).getName();
            		s.arcs.add(new A(v.getName(), to, weight));
        		}
        		s.transitions.add(new T(v.getName()));
            }
            if(v.getClass() == Location.class) {
            	s.locations.add(new L(v.getName(), v.getMarcs()));
            	System.out.println(v);
            }
        }
        
        xstream = new XStream();
		XStream xstream = new XStream();
		xstream.alias("data", Save.class);
		xstream.alias("location", L.class);
		xstream.alias("transition", T.class);
		xstream.alias("arc", A.class);
        String xml = xstream.toXML(s);
		
		FileOutputStream fos = null;
		try {
		    fos = new FileOutputStream("saved.xml");
		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
		    byte[] bytes = xml.getBytes("UTF-8");
		    fos.write(bytes);

		} catch(Exception e) {
		    e.printStackTrace();
		} finally {
		    if(fos!=null) {
		        try{ 
		            fos.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		System.out.println("Saved to file: saved.xml");
	}
	
}
