// src/components/RadioGroup.js
import React from 'react';

export const RadioGroup = ({ value, onValueChange, className, children }) => (
    <div className={className}>
        {React.Children.map(children, child =>
            React.cloneElement(child, { checked: child.props.value === value, onChange: () => onValueChange(child.props.value) })
        )}
    </div>
);

export const RadioGroupItem = ({ value, id, checked, onChange }) => (
    <input
        type="radio"
        id={id}
        value={value}
        checked={checked}
        onChange={onChange}
        className="radio"
    />
);
