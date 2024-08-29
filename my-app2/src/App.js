
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import AdminLoginPage from './components/AdminLoginPage';
import CustomerLoginPage from './components/CustomerLoginPage';
import CustomerListPage from './components/CustomerListPage';
import CustomerInfoPage from "./components/CustomerInfoPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/admin-login" element={<AdminLoginPage/>}/>
                <Route path="/admin/customers/:adminName" element={<CustomerListPage/>}/>
                <Route path="/customer-login" element={<CustomerLoginPage/>}/>
                <Route path="/admins/:adminId/allCustomers" element={<CustomerListPage/>}/>
                <Route path="/customers/get/:userId" element={<CustomerInfoPage/>}/>
            </Routes>
        </Router>
    );


}
    export default App;
