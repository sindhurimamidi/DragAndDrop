import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class macLastFileAccess extends LastFileAccess {
		macLastFileAccess(String[ ] args) {
			super(args);
			this.current_os = "mac";
		}
		
		public HashMap<String, String> findFileAccessTimes( ) throws IOException, InterruptedException{
			//System.out.println("In MAC findFileAccessTimes ..");
			
			for(int i = 0; i < this.file_arr.length; i++) {
				StringBuffer out = new StringBuffer( );
				String local_str = this.file_arr[i];
				Process proc;
				BufferedReader reader;
				String line = "";
	            //System.out.println("\nFinding access time for file: " + local_str);
	            String command = "stat -x " + local_str;
	            proc = Runtime.getRuntime( ).exec(command);
	            proc.waitFor( );
				reader = new BufferedReader(new InputStreamReader(proc.getInputStream( )));
				//System.out.println("reader.readLine is " + reader.readLine());
				while ((line = reader.readLine( )) != null) {
					if(line.matches("^Access:.*")) {
						//System.out.println("Found Access" + line);
						out.append(line + "\n");
					} 
				}
				//System.out.println(local_str + " " + out.toString());
				String access_time = out.toString();
				this.access_times_map.put(local_str, access_time);
				Iterator<Entry<String, String>> set_it = this.access_times_map.entrySet().iterator();
				//System.out.println("Size of access_times_map is " + this.access_times_map.size());
			    /*while (set_it.hasNext()) {
			        Map.Entry<String, String> map_entry = (Map.Entry<String, String>)set_it.next();
			        System.out.println(map_entry.getKey() + " = " + map_entry.getValue());
			    }*/
	        }
			//System.out.println("MAC Size of access_times_map is " + this.access_times_map.size());
			return this.access_times_map;
		}
}	
