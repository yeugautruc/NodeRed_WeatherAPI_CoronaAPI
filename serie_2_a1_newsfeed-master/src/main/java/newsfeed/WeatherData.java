package newsfeed;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherData extends Observable {
	
	public JsonObject getData() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet requestWeather = new HttpGet("http://10.8.0.4:1880/weather");
		
		HttpResponse responseWeather = client.execute(requestWeather);
		String response3 = EntityUtils.toString(responseWeather.getEntity());
		JsonObject jsonWeatherObjekt = JsonParser.parseString(response3).getAsJsonObject();
		
		notifyObserver("weather");
		
		return jsonWeatherObjekt;
	}

}
