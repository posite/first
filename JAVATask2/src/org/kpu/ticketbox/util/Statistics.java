package org.kpu.ticketbox.util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


import org.kpu.ticketbox.payment.*;

public class Statistics {
	public static double sum(HashMap<Integer,Receipt> map) {
		double total=0;
		Set<Integer> key = map.keySet();
		Iterator<Integer> it = key.iterator();
		while(it.hasNext()) {
			Integer id = it.next();
			total +=map.get(id).getTotalAmount();
		}
		return total;
	}
}
