package com.Cs681.Game.Service;



import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;


@Service
	public class SocketService {
//	    private final SocketIOServer server;
//
//	    public SocketService() {
//	        Configuration config = new Configuration();
//	        config.setHostname("localhost");
//	        config.setPort(8080);
//
//	        server = new SocketIOServer(config);
//
//	        server.addConnectListener(client -> {
//	            System.out.println("Client connected: " + client.getSessionId());
//	        });
//
//	        server.addEventListener("move", Integer.class, (client, data, ackSender) -> {
//	            System.out.println("Received move event: " + data);
//	            // Handle incoming move event here
//	        });
//
//	        server.start();
//	    }
//
//	    public void sendEvent(String eventName, Object data) {
//	        server.getBroadcastOperations().sendEvent(eventName, data);
//	    }
//
//	    @PreDestroy
//	    public void stopServer() {
//	        server.stop();
//	    }
	}


