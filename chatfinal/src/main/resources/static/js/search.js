let debounceTimeout;

function searchUsers() {
  clearTimeout(debounceTimeout);

  debounceTimeout = setTimeout(() => {
    const query = document.getElementById("userSearchInput").value.trim();
    const resultsContainer = document.getElementById("searchResults");

    // Always start by hiding the results container
    resultsContainer.style.display = "none";
    resultsContainer.innerHTML = "";

    if (query === "") {
      return; // do not search or show anything
    }

    fetch(`/api/search-users?query=${encodeURIComponent(query)}`)
      .then(response => {
        if (!response.ok) throw new Error("Failed to fetch users");
        return response.json();
      })
      .then(users => {
        if (users.length === 0) {
          resultsContainer.innerHTML = "<div class='search-result'>No users found</div>";
          resultsContainer.style.display = "block";
          return;
        }

        users.forEach(user => {
          const div = document.createElement("div");
          div.className = "search-result";
          div.textContent = user.username;
          div.onclick = () => {
            window.location.href = `/private-chat/${user.username}`;
          };
          resultsContainer.appendChild(div);
        });

        resultsContainer.style.display = "block"; // show only when results are present
      })
      .catch(error => {
        console.error("Error:", error);
        resultsContainer.style.display = "none"; // hide on error
      });
  }, 300); // debounce delay
}
