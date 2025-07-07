async function loadOnlineUsers() {
  try {
    const response = await fetch("/api/online-users");
    const users = await response.json();

    const list = document.getElementById("online-users");
    if (!list) return;

    list.innerHTML = ""; // Clear previous

    users.forEach(username => {
      const li = document.createElement("li");
      li.textContent = username;
      list.appendChild(li);
    });

  } catch (error) {
    console.error("Failed to fetch online users", error);
  }
}

// Refresh every 5 seconds
setInterval(loadOnlineUsers, 5000);
loadOnlineUsers(); // Load once initially
