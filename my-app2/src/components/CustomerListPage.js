import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './CustomerListPage.css';
import adminService from './AdminService'; // Ensure AdminService is imported correctly

const CustomerListPage = () => {
    const { adminId } = useParams(); // Get adminId from URL parameters
    const [customers, setCustomers] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchCustomers = async () => {
            try {
                const customerList = await adminService.listAllCustomers(adminId);
                setCustomers(customerList);
            } catch (error) {
                setError('Error fetching customers. Please try again later.');
            }
        };

        if (adminId) {
            fetchCustomers();
        }
    }, [adminId]);

    return (
        <div>
            <h2>Customer List</h2>
            {error && <p className="error">{error}</p>}
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Amount</th>
                    <th>IBAN</th> {/* Update header to "IBAN" */}
                </tr>
                </thead>
                <tbody>
                {customers.map((customer) => (
                    <tr key={customer.id}>
                        <td>{customer.id}</td>
                        <td>{customer.name}</td>
                        <td>{customer.amount}</td>
                        <td>{customer.ibanNo}</td> {/* Ensure this matches your data structure */}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CustomerListPage;
