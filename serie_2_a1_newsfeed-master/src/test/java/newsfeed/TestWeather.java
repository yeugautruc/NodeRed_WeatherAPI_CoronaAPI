package newsfeed;

import static org.junit.Assert.assertThat;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class TestWeather {

@Test	
	public void testUrl() throws ClientProtocolException, IOException {
		//raspi request
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet requestWeatherRaspi = new HttpGet("http://10.8.0.4:1880/weather");
		
		HttpResponse responseWeatherRaspi = client.execute(requestWeatherRaspi);
		String responseRaspi = EntityUtils.toString(responseWeatherRaspi.getEntity());
		JsonObject jsonWeatherObjektRaspi = JsonParser.parseString(responseRaspi).getAsJsonObject();
		
		
		//openweather request
		HttpGet requestWeatherApi = new HttpGet("https://api.openweathermap.org/data/2.5/find?q=Wolfenb%C3%BCttel&APPID=9b03b33de290e32ae576a33486918d3c");
		
		HttpResponse responseWeatherApi = client.execute(requestWeatherApi);
		String responseApi = EntityUtils.toString(responseWeatherApi.getEntity());
		JsonObject jsonWeatherObjektApi = JsonParser.parseString(responseApi).getAsJsonObject();
		jsonWeatherObjektApi=jsonWeatherObjektApi.get("list").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsJsonObject();
		
		assertThat(jsonWeatherObjektApi.get("temp"),equalTo(jsonWeatherObjektRaspi.get("temp")));
		assertThat(jsonWeatherObjektApi.get("pressure"),equalTo(jsonWeatherObjektRaspi.get("pressure")));
		assertThat(jsonWeatherObjektApi.get("humidity"),equalTo(jsonWeatherObjektRaspi.get("humidity")));
	}
	
	@Test
	public void testObserver () {
	
		WeatherData w = new WeatherData();
		
		NewsFeed f = new NewsFeed ();
		w.addObserver(f);
		assertThat(w.getObserver(),contains(f));
		}
	}
