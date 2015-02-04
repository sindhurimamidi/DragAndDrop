import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.lang.*;


public class winLastFileAccess extends LastFileAccess {
		winLastFileAccess(String[ ] args) {
			super(args);
			this.current_os = "windows";
		}
		
		public HashMap<String, String> findFileAccessTimes( ) throws IOException, InterruptedException{
			System.out.println("In WINDOWS findFileAccessTimes ..");
			try {
				String command0 = "cd";
				Process proc0 = Runtime.getRuntime().exec("cmd /c cd");
				proc0.waitFor( );
				BufferedReader reader0 = new BufferedReader(new InputStreamReader(proc0.getInputStream( )));
				System.out.println("reader0.readLine is " + reader0.readLine());
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			
			for(int i = 0; i < this.file_arr.length; i++) {
				StringBuffer out = new StringBuffer( );
				String local_str = this.file_arr[i];
				Process proc;
				BufferedReader reader;
				String line = "";
	            //System.out.println("\nFinding access time for file: " + local_str);
	            String command = "cmd /c dir /T:A | findstr " + local_str;
	            proc = Runtime.getRuntime( ).exec(command);
	            proc.waitFor( );
				reader = new BufferedReader(new InputStreamReader(proc.getInputStream( )));
				//System.out.println("reader.readLine is " + reader.readLine());
				line = reader.readLine( );
				String[ ] line_arr = line.split("\\s\\s\\s\\s");
				out.append(line_arr[0]);
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
			//System.out.println("WINDOWS Size of access_times_map is " + this.access_times_map.size());
			return this.access_times_map;
		}
}	
