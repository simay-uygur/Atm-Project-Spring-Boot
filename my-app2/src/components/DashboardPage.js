import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthService from './AuthService';
import { useAuth } from './AuthContext';
import LogoutButton from './LogoutButton';
import './DashboardPage.css';

const DashboardPage = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const { user } = useAuth();

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                // Assuming you have an endpoint to fetch user details
                // You might need to adjust this based on your actual API
                const response = await fetch('/api/v1/user', {
                    headers: AuthService.authHeader()
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch user information');
                }

                const data = await response.json();
                setUserInfo(data);
            } catch (err) {
                setError('Error fetching user information');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo();
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="dashboard-container">
            <h1>Welcome to Your Dashboard</h1>
            <div className="user-info">
                <h2>User Information</h2>
                {userInfo ? (
                    <ul>
                        <li>Username: {userInfo.username}</li>
                        <li>Email: {userInfo.email}</li>
                        {/* Add more user details as needed */}
                    </ul>
                ) : (
                    <p>No user information available</p>
                )}
            </div>
            <div className="dashboard-actions">
                <h2>Quick Actions</h2>
                <button onClick={() => navigate('/profile')}>Edit Profile</button>
                <button onClick={() => navigate('/transactions')}>View Transactions</button>
                {/* Add more action buttons as needed */}
            </div>
            <div className="logout-section">
                <LogoutButton />
            </div>
        </div>
    );
};



export default DashboardPage;