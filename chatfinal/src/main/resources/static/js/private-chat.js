document.addEventListener("DOMContentLoaded", function () {
  const currentUser = document.getElementById("currentUser")?.value;
  const otherUser = document.getElementById("otherUser")?.value;
  const chatBox = document.getElementById("chat-box");
  const msgInput = document.getElementById("msg-input");

  let stompClient = null;

  function connectWebSocket() {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {
      console.log("WebSocket connected");

      // Subscribe to /user/queue/messages (Spring routes it based on Principal)
      stompClient.subscribe("/user/queue/messages", function (message) {
        const msg = JSON.parse(message.body);

        if (
          (msg.senderUsername === currentUser && msg.receiverUsername === otherUser) ||
          (msg.senderUsername === otherUser && msg.receiverUsername === currentUser)
        ) {
          displayMessage(msg);
        }
      });
          stompClient.subscribe(`/user/${currentUser}/queue/typing`, function (typingMsg) {
          console.log("Received typing indicator:", typingMsg.body); // <== DEBUG LINE

            const typingBox = document.getElementById("typing-indicator");
             if (typingBox) { 
              typingBox.textContent = typingMsg.body;
               typingBox.style.display = "block";

              clearTimeout(typingBox._timeout);
              typingBox._timeout = setTimeout(() => {
              typingBox.style.display = "none";
              }, 2000);
    }
    });

    });
 }

  function displayMessage(msg) {
    const div = document.createElement("div");
    div.classList.add("message", msg.senderUsername === currentUser ? "sent" : "received");
    div.textContent = `${msg.senderUsername}: ${msg.message}`;
    chatBox.appendChild(div);
    chatBox.scrollTop = chatBox.scrollHeight;
  }

  async function loadMessages() {
    if (!currentUser || !otherUser) return;

    try {
      const res = await fetch(`/api/private/messages?sender=${currentUser}&receiver=${otherUser}`);
      const messages = await res.json();
      chatBox.innerHTML = "";
      messages.forEach(displayMessage);
    } catch (error) {
      console.error("Failed to load messages", error);
    }
  }

  window.sendMessage = function () {
    const message = msgInput.value.trim();
    if (!message || !stompClient || !currentUser || !otherUser) return;

    const msgObj = {
      senderUsername: currentUser,
      receiverUsername: otherUser,
      message: message
    };

    stompClient.send("/app/private-message", {}, JSON.stringify(msgObj));
    msgInput.value = "";
  };

  async function loadChatHistory() {
    const list = document.getElementById("chat-history-list");
    if (!currentUser || !list) return;

    try {
      const response = await fetch(`/api/chat/history`);
      const users = await response.json();

      list.innerHTML = "";
      users.forEach(user => {
        const item = document.createElement("div");
        item.className = "chat-user-item";
        item.textContent = user;
        item.onclick = () => window.location.href = `/private-chat/${user}`;
        list.appendChild(item);
      });
    } catch (err) {
      console.error("Failed to load chat history", err);
    }
  }

  // Send message on Enter key
  msgInput.addEventListener("keydown", function (e) {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  });
  msgInput.addEventListener("input", () => {
  if (stompClient && stompClient.connected && otherUser) {
    stompClient.send("/app/typing", {}, otherUser);
  }
});

  // Initialize
  connectWebSocket();
  loadMessages();
  loadChatHistory();
});