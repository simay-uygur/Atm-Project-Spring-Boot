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

    return (
        <div style={styles.container}>
            <h1>Welcome to the Portal</h1>
            <button style={styles.button} onClick={handleAdminLogin}>
                Admin Login
            </button>
            <button style={styles.button} onClick={handleCustomerLogin}>
                Customer Login
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
