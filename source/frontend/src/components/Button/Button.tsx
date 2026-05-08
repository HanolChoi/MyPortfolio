import React from 'react';
import './Button.css';

type ButtonProps = {
    children: React.ReactNode,
    disabled?: boolean,
    size?: 'large' | 'medium' | 'small',
    onClick?: () => void,
    type?: 'button' | 'submit' | 'reset',
    variant?: 'primary' | 'secondary' | 'danger' | 'ghost' | 'outline',
}

export default function Button({
    children,
    disabled = false,
    size = 'medium',
    onClick,
    type = 'button',
    variant = 'primary'
}: ButtonProps) {
    return (
        <button
            className={`button--${size} button--${variant}`}
            disabled={disabled}
            onClick={onClick}
            type={type}
        >
            {children}
        </button>
    )
}