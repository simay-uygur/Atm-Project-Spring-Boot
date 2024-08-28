// src/components/CustomerDetails.js

import React, { useState, useEffect } from 'react';
import CustomerService from './CustomerService';

const CustomerDetails = ({ customerId }) => {
    const [customer, setCustomer] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        // Fetch customer data when the component mounts
        CustomerService.getCustomerById(customerId)
            .then((response) => {
                setCustomer(response.data);
            })
            .catch((error) => {
                console.error('There was an error fetching the customer!', error);
                setError('Could not fetch customer details.');
            });
    }, [customerId]);

    if (error) {
        return <div>{error}</div>;
    }

    if (!customer) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Customer Details</h2>
            <p><strong>ID:</strong> {customer.id}</p>
            <p><strong>Name:</strong> {customer.name}</p>
            {/* Add more fields as necessary */}
        </div>
    );
};

export default CustomerDetails;
