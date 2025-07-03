document.addEventListener("DOMContentLoaded", function () {
  const currentUser = document.getElementById("currentUser")?.value;
  const otherUser = document.getElementById("otherUser")?.value;

  const chatBox = document.getElementById("chat-box");
  const msgInput = document.getElementById("msg-input");

  async function loadMessages() {
    if (!currentUser || !otherUser) {
      console.error("Missing user info.");
      return;
    }

    try {
      const res = await fetch(`/api/private/messages?sender=${currentUser}&receiver=${otherUser}`);
      const messages = await res.json();
      chatBox.innerHTML = "";

      messages.forEach(m => {
        const div = document.createElement("div");
        div.classList.add("message");
        div.classList.add(m.senderUsername === currentUser ? "sent" : "received");
        div.textContent = `${m.senderUsername}: ${m.message}`;
        chatBox.appendChild(div);
      });

      chatBox.scrollTop = chatBox.scrollHeight; // auto scroll to bottom
    } catch (error) {
      console.error("Failed to load messages", error);
    }
  }

  window.sendMessage = async function () {
    const message = msgInput.value.trim();
    if (!message || !currentUser || !otherUser) {
      alert("Missing required elements or user info.");
      return;
    }

    try {
      await fetch('/api/private/send', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          senderUsername: currentUser,
          receiverUsername: otherUser,
          message: message
        })
      });
      msgInput.value = "";
      await loadMessages();
    } catch (error) {
      console.error("Failed to send message", error);
    }
  };

  // Load previous messages and chat list
  loadMessages();

async function loadChatHistory() {
  const currentUser = document.getElementById("currentUser")?.value;
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

  loadChatHistory();
});
