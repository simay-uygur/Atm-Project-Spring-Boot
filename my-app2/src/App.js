
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import AdminLoginPage from './components/AdminLoginPage';
import CustomerLoginPage from './components/CustomerLoginPage';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/admin-login" element={<AdminLoginPage />} />
                <Route path="/customer-login" element={<CustomerLoginPage />} />
            </Routes>
        </Router>
    );
}

export default App;
