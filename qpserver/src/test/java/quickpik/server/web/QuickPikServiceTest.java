package quickpik.server.web;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sun.jersey.api.json.JSONWithPadding;

public class QuickPikServiceTest {

	@Test
	public void testGetPhotos() throws ClassNotFoundException, MalformedURLException, JSONException {
		QuickPikService service = new QuickPikService() ;
		JSONWithPadding result = service.getPhotos("car", "mycallback") ;
		assertNotNull(result) ;
		JSONObject json = (JSONObject) result.getJsonSource() ;
		assertNotNull(json) ;
		JSONArray itemsArray = json.getJSONArray("items") ;
		assertNotNull(itemsArray) ;
	}
}
