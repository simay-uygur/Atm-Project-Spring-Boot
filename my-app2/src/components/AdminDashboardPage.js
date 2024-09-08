import React from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from './AuthService';

const AdminDashboardPage = () => {
    const navigate = useNavigate();
    const user = AuthService.getCurrentUser();

    const handleLogout = () => {
        AuthService.logout();
        navigate('/login');
    };

    return (
        <div>
            <h1>Admin Dashboard</h1>
            <p>Welcome, {user.username}!</p>
            <button onClick={() => navigate('/admin/customers/' + user.username)}>View Customers</button>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default AdminDashboardPage;