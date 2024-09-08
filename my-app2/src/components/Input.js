// src/components/Input.js
import React from 'react';

const Input = ({ id, type, value, onChange, required }) => (
    <input
        id={id}
        type={type}
        value={value}
        onChange={onChange}
        required={required}
        className="input"
    />
);

export default Input;
