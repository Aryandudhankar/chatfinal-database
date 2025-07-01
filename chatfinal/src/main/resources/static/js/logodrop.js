function toggleDropdown() {
  const dropdown = document.querySelector('.profile-dropdown');
  dropdown.classList.toggle('show');
}

// Optional: close when clicking outside
document.addEventListener('click', function (event) {
  const dropdown = document.querySelector('.profile-dropdown');
  if (!dropdown.contains(event.target)) {
    dropdown.classList.remove('show');
  }
});
