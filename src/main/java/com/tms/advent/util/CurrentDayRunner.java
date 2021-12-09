package com.tms.advent.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CurrentDayRunner {
	public static void main(String[] args) {
		try {
			int dayToRun = 0;
			if (args.length == 1)
				dayToRun = Integer.parseInt(args[0]);
			else {
				List<String> classes = getClassesInPackage();
				for (String c : classes) {
					if (c.startsWith("Day") && !c.endsWith("X"))
						dayToRun = Math.max(dayToRun, Integer.parseInt(c.substring(3)));
				}
			}
			AllDayRunner.runDay(dayToRun);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
    public static List<String> getClassesInPackage() throws IOException {
    	List<String> result = new ArrayList<>();
        Enumeration<URL> thisEnum = CurrentDayRunner.class.getClassLoader().getResources(AllDayRunner.PKG.replace(".", "/"));
        while (thisEnum.hasMoreElements()) {
            URL url = (URL)thisEnum.nextElement();
            String urlName = url.toString();
            if (url.getProtocol().toLowerCase().equals("file")) {
                File file = new File(urlName.substring(urlName.indexOf("/")));
                String[] children = file.list();
                for (int i = 0; i < children.length; i++)
                    if (children[i].endsWith(".class") && children[i].indexOf('$') < 0)
                        result.add(children[i].substring(0, children[i].lastIndexOf(".class")));
            }
        }
        return result;
    }
}