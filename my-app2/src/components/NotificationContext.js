// NotificationContext.js
import React, { createContext, useState, useContext } from 'react';

const NotificationContext = createContext();

export const NotificationProvider = ({ children }) => {
    const [notification, setNotification] = useState(null);

    const showNotification = (message, type = 'info') => {
        setNotification({ message, type });
        setTimeout(() => setNotification(null), 3000); // Hide after 3 seconds
    };

    return (
        <NotificationContext.Provider value={{ showNotification }}>
            {children}
            {notification && (
                <div className={`notification ${notification.type}`}>
                    {notification.message}
                </div>
            )}
        </NotificationContext.Provider>
    );
};

export const useNotification = () => useContext(NotificationContext);

// In your main App.js or index.js
import { NotificationProvider } from './NotificationContext';

function App() {
    return (
        <NotificationProvider>
            {/* Your other components */}
        </NotificationProvider>
    );
}

// In CustomerInfoPage.js
import { useNotification } from './NotificationContext';

const CustomerInfoPage = () => {
    const { showNotification } = useNotification();

    // Use it in your component
    const handleWithdraw = async () => {
        // ... other code ...
        showNotification('Withdrawal successful', 'success');
        // ... other code ...
    };

    // ... rest of your component ...
};