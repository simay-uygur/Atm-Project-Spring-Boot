// src/components/ServerErrorPage.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ErrorPage.css';

const ServerErrorPage = () => {
    const navigate = useNavigate();

    return (
        <div className="error-page">
            <h2>500 - Server Error</h2>
            <p>We encountered an internal server error. Please try again later.</p>
            <button onClick={() => navigate('/')}>Go to Home</button>
        </div>
    );
};

export default ServerErrorPage;
