document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('#loginForm');
    const usernameInput = document.querySelector('#username');
    const passwordInput = document.querySelector('#password');
    const togglePassword = document.querySelector('#togglePassword');

    form.addEventListener('submit', (e) => {
        if (!usernameInput.value.trim() || !passwordInput.value.trim()) {
            e.preventDefault(); // stop form submission
            alert('Please enter both username and password.');
        }
    });

    if (togglePassword) {
        togglePassword.addEventListener('click', () => {
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            togglePassword.classList.toggle('showing');
        });
    }
});
