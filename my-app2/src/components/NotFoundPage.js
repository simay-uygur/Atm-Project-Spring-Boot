// src/components/NotFoundPage.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ErrorPage.css';

const NotFoundPage = () => {
    const navigate = useNavigate();

    return (
        <div className="error-page">
            <h2>404 - Not Found</h2>
            <p>The resource you are looking for could not be found.</p>
            <button onClick={() => navigate('/')}>Go to Home</button>
        </div>
    );
};

export default NotFoundPage;
