/*
/!*
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
                    <th>IBAN</th> {/!* Update header to "IBAN" *!/}
                </tr>
                </thead>
                <tbody>
                {customers.map((customer) => (
                    <tr key={customer.id}>
                        <td>{customer.id}</td>
                        <td>{customer.name}</td>
                        <td>{customer.amount}</td>
                        <td>{customer.ibanNo}</td> {/!* Ensure this matches your data structure *!/}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CustomerListPage;
*!/

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './CustomerListPage.css';
import adminService from './AdminService'; // Ensure AdminService is imported correctly

const CustomerListPage = () => {
    const { adminId } = useParams(); // Get adminId from URL parameters
    const [customers, setCustomers] = useState([]);
    const [error, setError] = useState('');
    const [updateError, setUpdateError] = useState('');
    const [deleteError, setDeleteError] = useState('');

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

    const handleUpdate = async (customer) => {
        try {
            // Example update data
            const updatedCustomer = { ...customer, name: 'Updated Name' };
            await adminService.updateCustomer(customer.id, updatedCustomer);
            setCustomers(customers.map(c => c.id === customer.id ? updatedCustomer : c));
        } catch (error) {
            setUpdateError('Error updating customer. Please try again later.');
        }
    };

    const handleDelete = async (customerId) => {
        try {
            await adminService.deleteCustomer(customerId);
            setCustomers(customers.filter(c => c.id !== customerId));
        } catch (error) {
            setDeleteError('Error deleting customer. Please try again later.');
        }
    };

    return (
        <div>
            <h2>Customer List</h2>
            {error && <p className="error">{error}</p>}
            {updateError && <p className="error">{updateError}</p>}
            {deleteError && <p className="error">{deleteError}</p>}
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Amount</th>
                    <th>IBAN</th> {/!* Update header to "IBAN" *!/}
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {customers.map((customer) => (
                    <tr key={customer.id}>
                        <td>{customer.id}</td>
                        <td>{customer.name}</td>
                        <td>{customer.amount}</td>
                        <td>{customer.ibanNo}</td> {/!* Ensure this matches your data structure *!/}
                        <td>
                            <button onClick={() => handleUpdate(customer)}>Update</button>
                            <button onClick={() => handleDelete(customer.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CustomerListPage;
*/

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './CustomerListPage.css';
import adminService from './AdminService'; // Ensure AdminService is imported correctly

const CustomerListPage = () => {
    const { adminId } = useParams(); // Get adminId from URL parameters
    const [customers, setCustomers] = useState([]);
    const [error, setError] = useState('');
    const [updateError, setUpdateError] = useState('');
    const [deleteError, setDeleteError] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [selectedCustomer, setSelectedCustomer] = useState(null);
    const [confirmationDialog, setConfirmationDialog] = useState({
        open: false,
        customerId: null
    });

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

    const handleUpdateClick = (customer) => {
        setSelectedCustomer(customer);
        setShowModal(true);
    };

    const handleUpdate = async (event) => {
        event.preventDefault();
        try {
            const updatedCustomer = {
                ...selectedCustomer,
                name: event.target.name.value,
                password: event.target.password.value
            };
            await adminService.updateCustomer(selectedCustomer.id, updatedCustomer);
            setCustomers(customers.map(c => c.id === selectedCustomer.id ? updatedCustomer : c));
            setShowModal(false);
        } catch (error) {
            setUpdateError('Error updating customer. Please try again later.');
        }
    };

    const handleDeleteClick = (customerId) => {
        setConfirmationDialog({ open: true, customerId });
    };

    const handleConfirmDelete = async () => {
        try {
            await adminService.deleteCustomer(confirmationDialog.customerId);
            setCustomers(customers.filter(c => c.id !== confirmationDialog.customerId));
            setConfirmationDialog({ open: false, customerId: null });
        } catch (error) {
            setDeleteError('Error deleting customer. Please try again later.');
        }
    };

    const handleCancelDelete = () => {
        setConfirmationDialog({ open: false, customerId: null });
    };

    return (
        <div>
            <h2>Customer List</h2>
            {error && <p className="error">{error}</p>}
            {updateError && <p className="error">{updateError}</p>}
            {deleteError && <p className="error">{deleteError}</p>}
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Amount</th>
                    <th>IBAN</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {customers.map((customer) => (
                    <tr key={customer.id}>
                        <td>{customer.id}</td>
                        <td>{customer.name}</td>
                        <td>{customer.amount}</td>
                        <td>{customer.ibanNo}</td>
                        <td>
                            <button onClick={() => handleUpdateClick(customer)}>Update</button>
                            <button onClick={() => handleDeleteClick(customer.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {/* Update Modal */}
            {showModal && selectedCustomer && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={() => setShowModal(false)}>&times;</span>
                        <h2>Update Customer</h2>
                        <form onSubmit={handleUpdate}>
                            <label>
                                Username:
                                <input type="text" name="name" defaultValue={selectedCustomer.name} required />
                            </label>
                            <label>
                                Password:
                                <input type="password" name="password" placeholder="New password" />
                            </label>
                            <button type="submit">Update</button>
                        </form>
                    </div>
                </div>
            )}

            {/* Confirmation Dialog */}
            {confirmationDialog.open && (
                <div className="modal">
                    <div className="modal-content">
                        <p>Are you sure you want to delete this customer?</p>
                        <button onClick={handleConfirmDelete}>Yes</button>
                        <button onClick={handleCancelDelete}>No</button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default CustomerListPage;
