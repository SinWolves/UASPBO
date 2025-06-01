document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("loginForm");
    const errorMsg = document.getElementById("errorMsg"); // Get the alert container

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        errorMsg.style.display = "none"; // Reset visibility

        const identifier = document.getElementById("identifier").value;
        const pwd = document.getElementById("pwd").value;

        if (!identifier || !pwd) {
            showError("Please fill in all fields.");
            return;
        }

        const params = new URLSearchParams();
        params.append("identifier", identifier);
        params.append("pwd", pwd);

        fetch("/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;
            } else {
                showError("Invalid credentials or unknown role.");
            }
        })
        .catch(error => {
            console.error("Login error:", error);
            showError("Server error. Please try again.");
        });
    });

    // Helper function to display errors
    function showError(message) {
        errorMsg.textContent = message;
        errorMsg.style.display = "block"; // Make alert visible
    }
});