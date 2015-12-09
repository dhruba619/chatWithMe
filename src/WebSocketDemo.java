import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class WebSocketDemo {
	static Set<Session> users = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void handleOnOpen(Session userSession) {
		System.out.println("Opening session" + userSession.getId());
		users.add(userSession);
	}

	@OnMessage
	public void echoTextMessage(Session userSession, String message,
			boolean last) throws IOException {
		System.out.println("Inside on message--->> "+message);
		
		String userName = (String) userSession.getUserProperties().get(
				"userName");
		if (userName == null) {
			userSession.getUserProperties().put("userName", message);
			String welcome = "Hi you are connected as: "+message;
			userSession.getBasicRemote().sendText("{\"data\":\""+welcome+"\"}");
		} else {
			Iterator<Session> iterator = users.iterator();
			while (iterator.hasNext()) {
				iterator.next().getBasicRemote()
						.sendText("{\"data\":\""+userName+" : "+message+"\"}");
			}
		}
	}

	@OnClose
	public void handleClose(Session userSession) {
		System.out.println("Closing session" + userSession.getId());
		users.add(userSession);
	}
	
	@OnError
	public void onError(Session session, Throwable thr) {
		System.out.println("An error occured : Reason-->"+thr.getMessage());
		handleClose(session);
	}

}