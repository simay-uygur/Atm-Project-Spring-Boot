import React, { useState } from 'react';
import UserService from './UserService'; // Adjust import path as needed
import './CreatePage.css'; // Add styles if needed

const CreateUserPage = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [amount, setAmount] = useState(0); // Added amount field
    const [ibanNo, setIbanNo] = useState(''); // Added ibanNo field
    const [message, setMessage] = useState('');

    const handleCreateUser = async (e) => {
        e.preventDefault();

        try {
            const response = await UserService.createUser({ name, password, amount, ibanNo });
            setMessage('User created successfully!');
        } catch (error) {
            setMessage('Error creating user. Please try again.');
        }
    };

    return (
        <div className="create-container">
            <h2>Create User</h2>
            {message && <p className="message">{message}</p>}
            <form onSubmit={handleCreateUser}>
                <div className="form-group">
                    <label htmlFor="name">Username:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password:</label>
                    <input
                        type="password"
                        id="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="amount">Amount:</label>
                    <input
                        type="number"
                        id="amount"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="ibanNo">IBAN Number:</label>
                    <input
                        type="text"
                        id="ibanNo"
                        value={ibanNo}
                        onChange={(e) => setIbanNo(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Create User</button>
            </form>
        </div>
    );
};

export default CreateUserPage;
