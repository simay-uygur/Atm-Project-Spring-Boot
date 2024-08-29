import React, { useState } from 'react';
import AdminLoginPage from './AdminLoginPage';
import CustomerListPage from './CustomerListPage';

const AdminPanel = () => {
    const [adminId, setAdminId] = useState(null);

    return (
        <div>
            {!adminId ? (
                <AdminLoginPage onLogin={setAdminId} />
            ) : (
                <CustomerListPage adminId={adminId} />
            )}
        </div>
    );
};

export default AdminPanel;
