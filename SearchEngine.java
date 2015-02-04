package BingSearch;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
@SuppressWarnings("unused")
public class SearchEngine {

	 private static HttpsURLConnection conn;
	 private static Scanner inp;	
	 
	 public static void main(String[] args) throws Exception {
		 
			String url = "https://www.bing.com/";
			String searchurl = "https://www.bing.com/search?q=";
			SearchEngine http = new SearchEngine();
		 
			// 1. Send a "GET" request, so that you can extract the form's data.
			URL obj = new URL(url);
			conn = (HttpsURLConnection) obj.openConnection();
		 
			// default is GET
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		 
			BufferedReader in = 
		            new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
		 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		 
			String page = response.toString();			
			//System.out.println("Extracting form's data from page = " + page + "\n\n");
			Document doc = Jsoup.parse(page);
			String text = doc.body().text();
			Element inputElement = doc.getElementById("sb_form_q");
			//System.out.println("Id search:" +inputElement.id());
			String key = inputElement.attr("name");
			String value = inputElement.attr("value");
			//System.out.println("printing key " +key);
	 
			
			if (key.equals("q")) {
				inp = new Scanner(System.in);
				System.out.println("\n*********************************");
				System.out.println("Type the keyword to be searched:");
				System.out.println("*********************************");
				value = inp.nextLine();
			}
			//System.out.println("printing value " +value);
			
			String postParams = key + "=" + URLEncoder.encode(value, "UTF-8");
			
			//System.out.println("printing value " +postParams);
	
			//2. Post the parameters;
			http.sendPost(url, postParams);					
		 
			//3. Success then search for the keyword.
			String value_url = searchurl + value;
			//System.out.println("Search url is:" +value_url);
			String result = http.GetPageContent(value_url);
			
			PrintWriter writer = new PrintWriter("bing_search.txt", "UTF-8");		
			//System.out.println("SEARCH RESPONSE :::\n\n");
			writer.println(result);
			writer.close();
			
			// Check for the links by the hyperlink
		    
			Document doc1 = Jsoup.connect(value_url).get();
			Elements links = doc1.getElementsByTag("a"); 
			for (Element link : links) {
				String linkHref = link.attr("href");
					if(linkHref.length() > 4 && linkHref.substring(0,4).equals("http")) {
						String linkText = link.text();
						//System.out.println("First URL name:" +linkText);
						System.out.println("\n*****************************************************");
						System.out.println("First URL is = " + linkHref);
						System.out.println("*****************************************************");
						break;
					}
			   }
			  
			
		  } //end of main
	
	 private void sendPost(String url, String postParams) throws Exception {
		 	URL obj = new URL(url);
			conn = (HttpsURLConnection) obj.openConnection();
		 
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Host", "www.bing.com");
			conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Referer", "https://www.bing.com");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
			conn.setDoOutput(true);
			conn.setDoInput(true);
		 
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
		 
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
		   //System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);
		 
			BufferedReader in = 
		             new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			PrintWriter writer = new PrintWriter("bing_response.txt", "UTF-8");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				writer.println(inputLine);
			}
			writer.close();
			in.close();
			
		  }

	 private String GetPageContent(String url) throws Exception {
		 
			URL obj = new URL(url);
			conn = (HttpsURLConnection) obj.openConnection();
		 
			// default is GET
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = 
		            new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		 
			return response.toString();
		 
		  }
}


