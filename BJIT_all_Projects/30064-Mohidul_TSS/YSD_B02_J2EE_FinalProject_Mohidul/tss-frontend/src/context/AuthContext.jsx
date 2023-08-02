import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    const login = async (username, password) => {
        try {
            const response = await axios.post('http://localhost:8080/user/login', {
                username,
                password,
            });

            // Store the access token in local storage
            const { token } = response.data;
            localStorage.setItem('accessToken', token);

            // Fetch the user details and set the user state
            await fetchUserData();
        } catch (error) {
            console.error('Error during login:', error);
        }
    };

    // Function to handle logout
    const logout = () => {
        // Remove the access token from local storage
        localStorage.removeItem('accessToken');

        // Reset the user state to null
        setUser(null);
    };

    // Function to fetch user data using the access token
    const fetchUserData = async () => {
        try {
            const token = localStorage.getItem('accessToken');
            if (token) {
                // Perform API call to fetch user data using the access token
                const response = await axios.get('http://localhost:8080/user', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                // Set the user state with the fetched user data
                setUser(response.data);
            }
        } catch (error) {
            console.error('Error fetching user data:', error);
        }
    };

    // Check if the user is authenticated on component mount
    useEffect(() => {
        fetchUserData();
    }, []);

    // Value object to be passed to the context provider
    const value = {
        user,
        login,
        logout,
        isAuthenticated: !!user,
    };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
