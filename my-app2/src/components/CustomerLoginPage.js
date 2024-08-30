import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom
import UserService from './UserService'; // Adjust import path as needed
import './LoginPage.css';

const CustomerLoginPage = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!name || !password) {
            setError('Please enter both username and password.');
            return;
        }

        try {
            const response = await UserService.loginUser(name, password);
            const { message, adminId } = response;

            if (message === "Login successful!") {
                navigate(`/customers/${adminId}`);
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
                    <label htmlFor="name">Username:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default CustomerLoginPage;
