package org.kpu.ticketbox.util;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.kpu.ticketbox.payment.*;

public class BackupWriter {
	public void backupFile(String filename, HashMap<Integer,Receipt> map) {
		File src = new File(filename);
		Set<Integer> key = map.keySet();
		Iterator<Integer> it = key.iterator();
		try {
			FileWriter fw = new FileWriter(src);
			while(it.hasNext()) {
				Integer id = it.next();
				Receipt receipt = map.get(id);
				fw.write(receipt.toString());
			}
		}catch(IOException e) {
			
		}
	}
}
