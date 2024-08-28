import React, { useState } from 'react';
import './LoginPage.css'; // Reuse the CSS for styling

const CustomerLoginPage = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleNameChange = (e) => {
        setName(e.target.value);
        setError(''); // Clears error when typing
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
        setError(''); // Clears error when typing
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!name || !password) {
            setError('Please enter both username and password.');
            return;
        }

        try {

            const response = await fetch('http://localhost:8080/api/v1/customers/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, password }),
            });


            const message = await response.text();

            if (response.ok) {
                alert(message); // Consider displaying this in the component instead
            } else {
                setError(message);
            }
        } catch (error) {
            setError('Error logging in. Please try again later.');
        }
    };

    return (
        <div className="login-container">
            <h2>Customer Login</h2>
            {error && <p className="error">{error}</p>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={name}
                        onChange={handleNameChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default CustomerLoginPage;
