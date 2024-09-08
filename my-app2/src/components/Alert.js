// src/components/Alert.js
import React from 'react';

export const Alert = ({ variant, children }) => (
    <div className={`alert ${variant}`}>
        {children}
    </div>
);

export const AlertDescription = ({ children }) => (
    <div className="alert-description">
        {children}
    </div>
);
