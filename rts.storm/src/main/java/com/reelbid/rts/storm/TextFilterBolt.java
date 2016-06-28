package com.zdatainc.rts.storm;

import org.apache.log4j.Logger;
import java.util.Map;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.*;
import org.apache.http.nio.protocol.*;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import java.io.*;
import java.nio.*;
import org.apache.http.impl.nio.*;
import org.apache.http.concurrent.*;

public class TextFilterBolt extends BaseBasicBolt
{
	private CloseableHttpAsyncClient httpclient; 
	private static final long serialVersionUID = 42L;
	private static Logger LOGGER = Logger.getLogger(TextFilterBolt.class);
	private static String url = "http://ec2-52-41-74-224.us-west-2.compute.amazonaws.com/test";

	//HttpClient httpclient = new DefaultHttpClient();
	//  HttpClient client = new HttpClient();
	//  HttpMethod method = new GetMethod("http://www.apache.org/");

	public final void prepare(Map stormConf, BasicOutputCollector collector){
		try {
			// start the http client
			httpclient = HttpAsyncClients.createDefault();
			httpclient.start();
		} catch (Throwable exception) {

		}
	}
	public void declareOutputFields(OutputFieldsDeclarer declarer)
	{
		declarer.declare(new Fields("tweet_id", "tweet_text"));
	}

	public void execute(Tuple input, BasicOutputCollector collector)
	{
		LOGGER.info("removing ugly characters");
		Long id = input.getLong(input.fieldIndex("tweet_id"));
		String text = input.getString(input.fieldIndex("tweet_text"));
		text = text.replaceAll("[^a-zA-Z\\s]", "").trim().toLowerCase();
		collector.emit(new Values(id, text));

		/////////// SEND HTTP POST ///////////////////
		String url="http://google.com";
		sendAsyncGetPostRequest(url);
	}

	public void sendAsyncGetPostRequest(String url) {
		HttpGet request = new HttpGet(url);
		HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);

/*		AsyncCharConsumer<HttpResponse> consumer = new AsyncCharConsumer<HttpResponse>() {

			HttpResponse response;

			@Override
				protected void onResponseReceived(final HttpResponse response) {
					this.response = response;
				}

			@Override
				protected void onCharReceived(final CharBuffer buf) throws IOException {
					// Do something useful
				}

			@Override
				protected void releaseResources() {
				}

			@Override
				protected HttpResponse buildResult(final HttpContext context) {
					return this.response;
				}
		};

		httpclient.execute(producer, consumer, new FutureCallback<HttpResponse>() {

				@Override
				public void completed(HttpResponse response) {
				// do something useful with the response
				//logger.debug(response.toString());
				}

				@Override
				public void failed(Exception ex) {
				//logger.warn("!!! Async http request failed!", ex);
				}

				@Override
				public void cancelled() {
				//logger.warn("Async http request canceled!");
				}
				});
*/
	}

	public Map<String, Object> getComponentConfiguration() { return null; }
}



