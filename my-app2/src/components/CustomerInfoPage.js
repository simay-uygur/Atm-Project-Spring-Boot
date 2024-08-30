import { toast } from 'react-toastify';
import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ErrorPage from './ErrorPage';
import NotFoundPage from './NotFoundPage';
import ServerErrorPage from './ServerErrorPage';
import './CustomerInfoPage.css';
import UserService from "./UserService";

const CustomerInfoPage = () => {
    const { userId } = useParams();
    const [customer, setCustomer] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [amount, setAmount] = useState('');
    const [transferIban, setTransferIban] = useState('');

    useEffect(() => {
        fetchCustomerInfo();
    }, [userId]);

    const fetchCustomerInfo = async () => {
        if (!userId) {
            setError('User ID is missing.');
            setLoading(false);
            return;
        }

        try {
            const customerData = await UserService.getCustomerInfo(userId);
            setCustomer(customerData);
        } catch (error) {
            console.error('Error fetching customer:', error);
            if (error.response && error.response.status === 404) {
                setError('not_found');
            } else {
                setError('server_error');
            }
        } finally {
            setLoading(false);
        }
    };

    const handleWithdraw = async () => {
        if (!amount ||  parseFloat(amount) <= 0) {
            toast.error('Please enter a valid positive amount');
            return;
        }
        try {
            const response = await UserService.withdrawMoney(userId,amount);
            const newBalance = response.data.newBalance;
            setCustomer(prevCustomer => ({ ...prevCustomer, amount: newBalance }));
            toast.success('Withdrawal successful');
            setAmount('');
        } catch (error) {
            console.error('Withdrawal error:', error);
            if (error.response) {
                // The request was made and the server responded with a status code
                // that falls out of the range of 2xx
                console.error('Error data:', error.response.data);
                console.error('Error status:', error.response.status);
                console.error('Error headers:', error.response.headers);
                toast.error(`Server error: ${error.response.data.message || 'Unknown error'}`);
            } else if (error.request) {
                // The request was made but no response was received
                console.error('Error request:', error.request);
                toast.error('No response received from server');
            } else {
                // Something happened in setting up the request that triggered an Error
                console.error('Error message:', error.message);
                toast.error('Error setting up the request');
            }
        }
    };



    const handleDeposit = async () => {
        if (!amount || isNaN(amount) || parseFloat(amount) <= 0) {
            toast.error('Please enter a valid positive amount');
            return;
        }
        try {
            const response = await UserService.depositMoney(userId, amount);
            const newBalance = response.data.newBalance;
            setCustomer(prevCustomer => ({ ...prevCustomer, amount: newBalance }));
            toast.success('Deposit successful');
            setAmount('');
        }  catch (error) {
        console.error('Deposit error:', error.response ? error.response.data : error.message);
        if (error.response) {
            toast.error(`Error depositing money: ${error.response.data.message}`);
        } else {
            toast.error('Network error');
        }
    }
    };

    const handleTransfer = async () => {
        if (!amount || isNaN(amount) || parseFloat(amount) <= 0) {
            toast.error('Please enter a valid positive amount');
            return;
        }
        if (!transferIban) {
            toast.error('Please enter a valid IBAN');
            return;
        }
        try {
            const response = await UserService.transferMoney(userId, transferIban, amount);
            const newBalance = response.data.newBalance;
            setCustomer(prevCustomer => ({ ...prevCustomer, amount: newBalance }));
            toast.success('Transfer successful');
            setAmount('');
            setTransferIban('');
        } catch (error) {
        console.error('Transfer error:', error.response ? error.response.data : error.message);
        if (error.response) {
            if (error.response.status === 404) {
                toast.error('Recipient not found');
            } else if (error.response.status === 402) {
                toast.error('Insufficient funds');
            } else {
                toast.error(`Error transferring money: ${error.response.data.message}`);
            }
        } else {
            toast.error('Network error');
        }
    }
    };

    if (loading) return <p>Loading...</p>;
    if (error === 'not_found') return <NotFoundPage />;
    if (error === 'server_error') return <ServerErrorPage />;
    if (error) return <ErrorPage message={error} />;

    return (
        <div className="customer-info-container">
            <h2>Customer Information</h2>
            {customer ? (
                <>
                    <p><strong>Name:</strong> {customer.name}</p>
                    <p><strong>Balance:</strong> ${customer.amount}</p>

                    <div className="transaction-section">
                        <input
                            type="number"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                            placeholder="Enter amount"
                            min="0"
                        />
                        <button onClick={handleWithdraw}>Withdraw</button>
                        <button onClick={handleDeposit}>Deposit</button>
                    </div>

                    <div className="transfer-section">
                        <input
                            type="text"
                            value={transferIban}
                            onChange={(e) => setTransferIban(e.target.value)}
                            placeholder="Enter IBAN for transfer"
                        />
                        <button onClick={handleTransfer}>Transfer</button>
                    </div>
                </>
            ) : (
                <p>No customer information available.</p>
            )}
        </div>
    );
};

export default CustomerInfoPage;
