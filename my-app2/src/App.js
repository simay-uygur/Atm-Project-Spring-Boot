import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Add necessary imports
import AdminLoginPage from './components/AdminLoginPage';
import CustomerLoginPage from './components/CustomerLoginPage';
import CustomerListPage from './components/CustomerListPage';
import CustomerInfoPage from "./components/CustomerInfoPage";
import HomePage from './components/HomePage'; // Make sure to import HomePage
import { ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css'; // Ensure toast styles are imported

function App() {
    return (
        <>
            <ToastContainer />
            <Router>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/admin-login" element={<AdminLoginPage />} />
                    <Route path="/admin/customers/:adminName" element={<CustomerListPage />} />
                    <Route path="/customer-login" element={<CustomerLoginPage />} />
                    <Route path="/admins/:adminId/allCustomers" element={<CustomerListPage />} />
                    <Route path="/customers/:userId" element={<CustomerInfoPage />} />
                </Routes>
            </Router>
        </>
    );
}

export default App;
