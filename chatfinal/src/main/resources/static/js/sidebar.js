  const sidebar = document.getElementById("mySidebar");
 const overlay = document.getElementById("overlay");


function toggleSidebar() {
  const sidebar = document.querySelector('.sidebar');

  if (sidebar.style.left === '0px') {
    sidebar.style.left = '-250px'; // hide
  } else {
    sidebar.style.left = '0px'; // show
  }
}
document.addEventListener("DOMContentLoaded", function () {
  const toggle = document.getElementById("darkModeToggle");
  const body = document.body;

  // Load stored preference
  if (localStorage.getItem("theme") === "dark") {
    body.classList.add("dark-mode");
    toggle.checked = true;
  }

  toggle.addEventListener("change", function () {
    if (this.checked) {
      body.classList.add("dark-mode");
      localStorage.setItem("theme", "dark");
    } else {
      body.classList.remove("dark-mode");
      localStorage.setItem("theme", "light");
    }
  });


// Close when clicking outside
overlay.addEventListener("click", () => {
  sidebar.style.display = "none";
  overlay.style.display = "none";
});

// Optional: Close with Escape key
document.addEventListener("keydown", function (e) {
  if (e.key === "Escape") {
    sidebar.style.display = "none";
    overlay.style.display = "none";
  }
});
});
