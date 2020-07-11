package newsfeed;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.gson.JsonObject;


@WebServlet(urlPatterns = { "raumalarm" })

public class RaumAlarm extends HttpServlet {
	
	private RaumAlarmData data ;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Refresh", "3");
		JsonObject jsonRaumAlarmObjekt = data.getData();

		String html_Respond = "<html> " + "	<body>" +"<h1> Weather in Experiment Room </h1>"
				+ "<p> the Temperature in Experiment Room :" + jsonRaumAlarmObjekt.get("temp") + "</p>"
				+ "<p> the Pressure in Experiment Room :" + jsonRaumAlarmObjekt.get("pressure") + "</p>" 
				+ "<p> the Humidity in Experiment Room :" + jsonRaumAlarmObjekt.get("humidity") + "</p> "
						+ "<a href='newsfeed'> back </a>" + "</body>" + "</html> ";

		response.getWriter().print(html_Respond);

	}
	
	@Override
	public void init() throws ServletException {
		data = new RaumAlarmData ();
		getServletContext().setAttribute("alarmObservable", data);
		super.init();
	}
}
