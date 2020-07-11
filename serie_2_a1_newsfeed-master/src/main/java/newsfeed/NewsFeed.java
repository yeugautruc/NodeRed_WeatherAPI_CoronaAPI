package newsfeed;


	import org.apache.http.HttpResponse;
	import org.apache.http.client.ClientProtocolException;
	import org.apache.http.client.methods.HttpGet;
	import org.apache.http.impl.client.CloseableHttpClient;
	import org.apache.http.impl.client.HttpClientBuilder;
	import org.apache.http.util.EntityUtils;

	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import java.beans.PropertyChangeSupport;
	import java.io.IOException;
	import java.net.http.HttpClient;
	import java.sql.Date;
	import java.util.*;
	import java.text.*;

	import com.google.gson.JsonArray;
	import com.google.gson.JsonElement;
	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;

	import newsfeed.Observer;

	@WebServlet(urlPatterns = { "newsfeed" })

	public class NewsFeed  extends HttpServlet implements Observer {
		
		private int countWeatherClicks ;
		private int countRaumAlarmClicks ;
		private String alarm ;
		

		public NewsFeed() {
			countWeatherClicks =0 ;
			countRaumAlarmClicks = 0;
			alarm = "";
		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			response.setHeader("Refresh", "3");
			CloseableHttpClient client = HttpClientBuilder.create().build();			
			HttpGet requestRaumAlarm = new HttpGet("http://localhost:8080/newsfeed/raumalarm");
			try {
				client.execute(requestRaumAlarm);
			}catch (Exception e) {
				// TODO: handle exception
			}
			String responseString = "<!DOCTYPE html> " + 
					 "    <html> " +  
					 "    <head> " +  
					 "    <metacharset=\"utf-8\"> " +  
					 "    <title>NewsFeed</title> " +  
					 "    </head> " +  
					 "    <body> " +  
					 "    <div id=\"newsfeed\"> " +  
					 "<a href='weather'> Weather:  </a>"+
					 "<p> Clicks :"+countWeatherClicks + "</p><br>"+
					 "<a href='raumalarm'> RaumAlarm: </a>"+
					 "<p> Clicks :"+countRaumAlarmClicks + alarm + " </p>"+
					 "    </div> " +  
  		 			 "    </body> " +  
					 "    </html>"; 
			response.getWriter().print(responseString );
		}

		@Override
		public void update(String s) {
			String [] arr = s.split(":");
			
			if(arr[0].equals("weather")) {
				countWeatherClicks++;
			}else {
				countRaumAlarmClicks++;
			}
			
			if (arr.length > 1) {
				alarm = arr[1];
			}else {
				alarm = "";
			}
		}
		
		@Override
		public void init() throws ServletException {
			
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet requestWeather = new HttpGet("http://localhost:8080/newsfeed/weather");
			try {
				client.execute(requestWeather);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			HttpGet requestRaumAlarm = new HttpGet("http://localhost:8080/newsfeed/raumalarm");
			try {
				client.execute(requestRaumAlarm);
			} catch (IOException e) {
				e.printStackTrace();
			}

			super.init();
			
			// ruf ein object auf das in Helloweather erzeugt wurde
			WeatherData w = (WeatherData)getServletContext().getAttribute("weatherObservable");
				w.addObserver(this);
				
				// ruf ein object auf das in Alarm erzeugt wurde
			RaumAlarmData b = (RaumAlarmData)getServletContext().getAttribute("alarmObservable");
				b.addObserver(this);
		

			
		}

	}
