import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom
import AdminService from "./AdminService";
import './LoginPage.css';

const AdminLoginPage = () => {
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
            const response = await AdminService.loginAdmin(name, password);
            const { message, adminId } = response;

            if (message === "Login successful!") {
                //alert(message);
                navigate(`/admins/${adminId}/allCustomers`); // Navigate to CustomerListPage with adminId
            } else {
                setError(message);
            }
        } catch (error) {
            setError('Error logging in. Please try again later.');
        }
    };

    return (
        <div className="login-container">
            <h2>Admin Login</h2>
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

export default AdminLoginPage;
