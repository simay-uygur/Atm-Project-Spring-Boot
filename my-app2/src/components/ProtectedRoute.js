import React from 'react';
import { Navigate } from 'react-router-dom';
import AuthService from './AuthService';

const ProtectedRoute = ({ children }) => {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

export default ProtectedRoute;