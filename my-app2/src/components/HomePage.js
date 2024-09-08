import React from 'react';
import { useNavigate } from 'react-router-dom';

const HomePage = () => {
    const navigate = useNavigate();

    const handleAdminLogin = () => {
        navigate('/admin-login');
    };

    const handleCustomerLogin = () => {
        navigate('/customer-login');
    };

    const handleCreateUser = () => {
        navigate('/create-user');
    };

    const handleCreateAdmin = () => {
        navigate('/create-admin');
    };

    return (
        <div style={styles.container}>
            <h1>Welcome to the Portal</h1>
            <button style={styles.button} onClick={handleAdminLogin}>
                Admin Login
            </button>
            <button style={styles.button} onClick={handleCustomerLogin}>
                Customer Login
            </button>
            <button style={styles.button} onClick={handleCreateUser}>
                Create User
            </button>
            <button style={styles.button} onClick={handleCreateAdmin}>
                Create Admin
            </button>
        </div>
    );
};

const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        height: '100vh',
        gap: '20px',
        backgroundColor: '#f5f5f5',
    },
    button: {
        padding: '10px 20px',
        fontSize: '16px',
        cursor: 'pointer',
        borderRadius: '5px',
        border: '1px solid #ccc',
    },
};

export default HomePage;
