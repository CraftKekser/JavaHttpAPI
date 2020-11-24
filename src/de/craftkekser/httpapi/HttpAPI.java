package de.craftkekser.httpapi;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpAPI {

	private HttpServer server;
	private ThreadPoolExecutor threadPoolExecutor;

	volatile private HashMap<String, List<Route>> routes;

	public HttpAPI(int port) throws IOException {
		this.server = HttpServer.create(new InetSocketAddress(port), 0);
		this.threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
		this.routes = new HashMap<>();
	}

	public void listen() {
		this.server.setExecutor(this.threadPoolExecutor);
		this.server.start();

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				forceShutdown();
			}
		}));
	}
	
	public void shutdown() {
		this.server.stop(3);
	}
	
	public void forceShutdown() {
		this.server.stop(0);
		this.threadPoolExecutor.shutdownNow();
	}

	public void addRoute(String method, String path, HttpAPIHandler handler) {
		Route r = new Route(method, path, handler);

		if(this.routes.containsKey(path)) {
			this.routes.get(path).add(r);
		}else {
			List<Route> newList = new ArrayList<>();
			newList.add(r);
			this.routes.put(path, newList);

			this.server.createContext(path, new HttpHandler() {

				@Override
				public void handle(HttpExchange e) throws IOException {
					List<Route> localRoutes = routes.get(e.getHttpContext().getPath());
					boolean routeFound = false;
					for(Route r : localRoutes) {
						if(e.getRequestMethod().equalsIgnoreCase(r.getMethod())) {
							HttpAPIResponse response = r.getHandler().handle(new HttpAPIRequest(e));
							byte[] responseContent = response.getData().toString().getBytes(StandardCharsets.UTF_8);
							e.getResponseHeaders().add("Content-Type", "text/json");
							e.sendResponseHeaders(response.getResponseCode(), responseContent.length);
							e.getResponseBody().write(responseContent);
							e.getResponseBody().close();
							routeFound = true;
						}
					}
					if(!routeFound) {
						e.sendResponseHeaders(404, 0);
						e.getResponseBody().close();
					}
				}
			});
		}

	}

	public class Route {

		private String method;
		private String path;
		private HttpAPIHandler handler;

		public Route(String method, String path, HttpAPIHandler handler) {
			this.setMethod(method);
			this.setPath(path);
			this.setHandler(handler);
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public HttpAPIHandler getHandler() {
			return handler;
		}

		public void setHandler(HttpAPIHandler handler) {
			this.handler = handler;
		}

	}
}
