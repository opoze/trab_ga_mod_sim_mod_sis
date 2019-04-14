package com.luispoze.TrabGA_SimModSis;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Save {
	public List<L> locations = new ArrayList<L>();
	public List<T> transitions= new ArrayList<T>();
	public List<A> arcs = new ArrayList<A>();
}
