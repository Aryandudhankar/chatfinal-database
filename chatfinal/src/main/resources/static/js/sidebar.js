function toggleSidebar() {
  const sidebar = document.querySelector('.sidebar');

  if (sidebar.style.left === '0px') {
    sidebar.style.left = '-250px'; // hide
  } else {
    sidebar.style.left = '0px'; // show
  }
}
