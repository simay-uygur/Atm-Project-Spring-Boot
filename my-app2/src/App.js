import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CustomerListPage from './components/CustomerListPage';
import CustomerInfoPage from './components/CustomerInfoPage';
import HomePage from './components/HomePage';
import CreateUserPage from './components/CreateUserPage';
import CreateAdminPage from './components/CreateAdminPage';
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import LoginPage from './components/LoginPage';
import ProtectedRoute from './components/ProtectedRoute';
import AdminDashboardPage from './components/AdminDashboardPage';
import CustomerDashboardPage from './components/CustomerDashboardPage';

function App() {
    return (
        <>
            <ToastContainer />
            <Router>
                <Routes>
                    <Route path="/" element={<LoginPage />} />
                    <Route path="/create-user" element={<CreateUserPage />} />
                    <Route path="/create-admin" element={<CreateAdminPage />} />

                    <Route path="/admin/dashboard" element={
                        <ProtectedRoute>
                            <AdminDashboardPage />
                        </ProtectedRoute>
                    } />
                    <Route path="/customer/dashboard" element={
                        <ProtectedRoute>
                            <CustomerDashboardPage />
                        </ProtectedRoute>
                    } />

                    <Route path="/admin/customers/:adminName" element={
                        <ProtectedRoute>
                            <CustomerListPage />
                        </ProtectedRoute>
                    } />
                    <Route path="/customers/:userId" element={
                        <ProtectedRoute>
                            <CustomerInfoPage />
                        </ProtectedRoute>
                    } />
                </Routes>
            </Router>
        </>
    );
}

export default App;