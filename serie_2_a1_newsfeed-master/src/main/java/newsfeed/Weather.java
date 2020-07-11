package newsfeed;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.google.gson.JsonObject;

@WebServlet(urlPatterns = { "weather" })

public class Weather extends HttpServlet {
	
	private WeatherData data ;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JsonObject jsonWeatherObject = data.getData();

		String icon = jsonWeatherObject.get("icon").toString().replace('"', ' ').trim();
		double temperature = (int) ((Double.parseDouble(jsonWeatherObject.get("temp").toString()) - 273.15) * 100);
		temperature /= 100;

		String html_Respond = "<html> " + "	<body>"
				+"<h1> Weather in Wolfenb�ttel </h1>"
				+ "<p> the Temperature is :" + temperature + "</p>"
				+ "<p> the Pressure is :" + jsonWeatherObject.get("pressure") + "</p>" +
				"<p> the Humidity is :"+ jsonWeatherObject.get("humidity") + "</p>" 
				+ "<img src='http://openweathermap.org/img/wn/" + icon
				+ "@2x.png'/>" + "<p> the Description is :" + jsonWeatherObject.get("description") + "</p>"
				+"<a href='newsfeed'> back </a>"
				+ "</body>"
				+ "</html> ";

		response.getWriter().print(html_Respond );
	}
	
	@Override
	public void init() throws ServletException {
		data = new WeatherData ();
		
		// getServlet ist unser static array
		getServletContext().setAttribute("weatherObservable", data);
		super.init();
	}
}