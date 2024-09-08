import React from 'react';
import { Link } from 'react-router-dom';
import LogoutButton from './LogoutButton';
import AuthService from './AuthService';

const NavBar = () => {
    const currentUser = AuthService.getCurrentUser();

    return (
        <nav>
            <ul>
                <li><Link to="/">Home</Link></li>
                {currentUser ? (
                    <>
                        <li><Link to="/dashboard">Dashboard</Link></li>
                        <li><LogoutButton /></li>
                    </>
                ) : (
                    <li><Link to="/login">Login</Link></li>
                )}
            </ul>
        </nav>
    );
};

export default NavBar;