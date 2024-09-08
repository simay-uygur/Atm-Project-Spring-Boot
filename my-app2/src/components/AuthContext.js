import React, { createContext, useState, useContext, useEffect } from 'react';
import AuthService from './AuthService';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const user = AuthService.getCurrentUser();
        if (user) {
            setUser(user);
        }
    }, []);

    const login = (userData) => {
        setUser(userData);
    };

    const logout = () => {
        AuthService.logout();
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);