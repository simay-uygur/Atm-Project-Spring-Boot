import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from './AuthService';

const CustomerDashboardPage = () => {
    const navigate = useNavigate();
    const user = AuthService.getCurrentUser();

    const handleLogout = () => {
        AuthService.logout();
        navigate('/login');
    };

    return (
        <div>
            <h1>Customer Dashboard</h1>
            <p>Welcome, {user.username}!</p>
            <button onClick={() => navigate('/customers/' + user.id)}>View Account Info</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default CustomerDashboardPage;