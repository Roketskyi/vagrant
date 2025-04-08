document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    
    registerForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        
        if (password !== confirmPassword) {
            alert('Паролі не співпадають!');
            return;
        }
        
        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });
            
            if (response.ok) {
                alert('Реєстрація успішна! Тепер ви можете увійти.');
                window.location.href = '/login';
            } else {
                const error = await response.json();
                alert(error.message || 'Помилка при реєстрації');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Помилка при реєстрації');
        }
    });
}); 