package sandboxapii;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class TradeMeApi extends DefaultApi10a {
	private static final String AUTHORIZE_URL = "https://secure.tmsandbox.co.nz/Oauth/Authorize?oauth_token=%s"; //https://secure.trademe.co.nz/Oauth/Authorize?oauth_token=<token>

	protected TradeMeApi() {
    }

	private static class InstanceHolder {
		private static final TradeMeApi INSTANCE = new TradeMeApi();
	}

	public static TradeMeApi instance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return "https://secure.tmsandbox.co.nz/Oauth/AccessToken";
	}

	@Override
	public String getRequestTokenEndpoint() {
		return "https://secure.tmsandbox.co.nz/Oauth/RequestToken?scope=MyTradeMeRead,MyTradeMeWrite";
	}

	@Override
	public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
		System.out.println("getAuthorizationUrl");
		return String.format(AUTHORIZE_URL, requestToken.getToken());
	}

	@Override
	protected String getAuthorizationBaseUrl() {
		System.out.println("getAuthorizationBaseUrl");

		return AUTHORIZE_URL;
	}
	

	
}