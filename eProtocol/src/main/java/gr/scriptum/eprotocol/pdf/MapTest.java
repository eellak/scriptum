package gr.scriptum.eprotocol.pdf;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class MapTest {

  
  public static void main(String argv[]){
	  
	  TreeMap<Date,String> map = new TreeMap<Date,String>();
	  
	  map.put(new Date(), "today");
	  map.put(new Date(1239434533445L), "1");
	  map.put(new Date(1233499532345L), "2");
	  map.put(new Date(1234499532345L), "3");
	  map.put(new Date(1334499532345L), "4");
	  map.put(new Date(2000000000000L), "5");
	  
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Date,String> pairs = (Map.Entry<Date,String>)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	    }
  }
  
  
}
