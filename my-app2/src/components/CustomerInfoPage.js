import React, { useState, useEffect } from 'react';
import CustomerService from './CustomerService';
import { useParams } from 'react-router-dom'; // Import useParams to get route parameters
import './CustomerInfoPage.css';

const CustomerInfoPage = () => {
    const { userId } = useParams(); // Get userId from route parameters
    const [customer, setCustomer] = useState(null);
    const [iban, setIban] = useState('');
    const [amount, setAmount] = useState(0);
    const [error, setError] = useState('');

    useEffect(() => {
        if (!userId) {
            setError('User ID is missing.');
            return;
        }

        // Fetch customer information
        const fetchCustomer = async () => {
            try {
                const data = await CustomerService.getCustomerInfo(userId); // Pass userId to the service
                setCustomer(data);
            } catch (error) {
                setError('Error fetching customer information.');
            }
        };

        fetchCustomer();
    }, [userId]); // Add userId as a dependency

    const handleGetMoney = async () => {
        try {
            await CustomerService.getMoney();
            alert('Money retrieved successfully.');
        } catch (error) {
            setError('Error retrieving money.');
        }
    };

    const handleEnterMoney = async () => {
        try {
            await CustomerService.enterMoney(amount);
            alert('Money entered successfully.');
        } catch (error) {
            setError('Error entering money.');
        }
    };

    const handleSendMoney = async () => {
        try {
            await CustomerService.sendMoney(iban, amount);
            alert('Money sent successfully.');
        } catch (error) {
            setError('Error sending money.');
        }
    };

    return (
        <div className="customer-info-container">
            {customer ? (
                <>
                    <h2>Customer Information</h2>
                    <p><strong>Name:</strong> {customer.name}</p>
                    <p><strong>Balance:</strong> ${customer.balance}</p>

                    <div className="actions">
                        <button onClick={handleGetMoney}>Get Money</button>

                        <div className="input-group">
                            <label htmlFor="amount">Enter Amount:</label>
                            <input
                                type="number"
                                id="amount"
                                value={amount}
                                onChange={(e) => setAmount(Number(e.target.value))}
                            />
                            <button onClick={handleEnterMoney}>Enter Money</button>
                        </div>

                        <div className="input-group">
                            <label htmlFor="iban">Recipient IBAN:</label>
                            <input
                                type="text"
                                id="iban"
                                value={iban}
                                onChange={(e) => setIban(e.target.value)}
                            />
                            <button onClick={handleSendMoney}>Send Money</button>
                        </div>
                    </div>

                    {error && <p className="error">{error}</p>}
                </>
            ) : (
                <p>Loading customer information...</p>
            )}
        </div>
    );
};

export default CustomerInfoPage;
