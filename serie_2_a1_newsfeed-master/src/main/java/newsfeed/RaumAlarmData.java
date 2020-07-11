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

public class RaumAlarmData extends Observable {

	public JsonObject getData () throws ClientProtocolException, IOException {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet requestWeather = new HttpGet("http://10.8.0.4:1880/raumalarm");

		HttpResponse responseWeather = client.execute(requestWeather);

		String response3 = EntityUtils.toString(responseWeather.getEntity());

		JsonObject jsonRaumAlarmObjekt = JsonParser.parseString(response3).getAsJsonObject();
		
		if (Double.parseDouble(jsonRaumAlarmObjekt.get("temp").toString()) > 20) {
			notifyObserver(" alarm : Groesser 20 C");
		}else if (jsonRaumAlarmObjekt.get("temp").toString().isEmpty()){
			notifyObserver("alarm : Sensoren liefern Keine Werte ");
		}
		
		else {
			notifyObserver("alarm ");
		}
		return jsonRaumAlarmObjekt;

	}

}
