<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<title>Dj's ChatRoom</title>
</head>
<script type="text/javascript">    
            var socketURI = "ws://192.168.50.146:8082/chatWithMe/echo";
            websocket = new WebSocket(socketURI);
            
            websocket.onmessage = function processMessage(message){
            	console.log(message);
            	var jsonData = JSON.parse(message.data);
            	console.log(jsonData)
            	if(jsonData.data != null){
            		messageTextArea.value = messageTextArea.value + jsonData.data +"\n";
            	}
            }
            function sendMessage(){
            	console.log("Outgoing message--> "+messageTextArea.value);
            	websocket.send(messageText.value);
            	messageText.value="";
            	messageText.placeholder="";
            }
            
</script>
<body>
	<h1 style="text-align: center;">Dj's chat Room</h1>
	<br>
	<div style="text-align: center;">
		<textarea id="messageTextArea" readonly="readonly" rows="10" cols="80"></textarea>
		<br> 
		<input id="messageText" placeholder="Enter your Name" type="text">
		<input onclick="sendMessage()" value="Send" type="button">

	</div>
	<div id="output"></div>
</body>
</html>