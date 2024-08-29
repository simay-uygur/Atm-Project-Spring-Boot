// src/components/ErrorPage.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ErrorPage.css'; // Create a CSS file for styling

const ErrorPage = ({ message, onRetry }) => {
    const navigate = useNavigate();

    return (
        <div className="error-page">
            <h2>Oops! Something went wrong.</h2>
            <p>{message || 'An unexpected error occurred. Please try again later.'}</p>
            <div className="error-actions">
                {onRetry && <button onClick={onRetry}>Retry</button>}
                <button onClick={() => navigate('/')}>Go to Home</button>
            </div>
        </div>
    );
};

export default ErrorPage;
