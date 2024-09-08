// src/components/Button.js
import React from 'react';

const Button = ({ type, className, children }) => (
    <button type={type} className={className}>
        {children}
    </button>
);

export default Button;
