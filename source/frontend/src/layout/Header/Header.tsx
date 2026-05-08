import React from 'react';
import './Header.css';

type HeaderProps = {
    children: React.ReactNode,
    id?: string
}

export default function Header({ children, id }: HeaderProps) {
    return (
        <header
            id={id}>
            {children}
        </header>
    )
}