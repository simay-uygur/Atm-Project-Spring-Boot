import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation
import { Alert, AlertDescription } from './Alert';
import Button from './Button';
import { Card, CardContent, CardHeader, CardTitle } from './Card';
import Input from './Input';
import Label from './Label';
import { RadioGroup, RadioGroupItem } from './RadioGroup';



const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('ROLE_USER');
    const [error, setError] = useState('');
    const navigate = useNavigate(); // React Router hook

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        // Construct the payload correctly
        const payload = {
            username,
            password,
            userType: role === 'ROLE_ADMIN' ? 'admin' : 'customer',
        };

        try {
            const response = await fetch('http/localhost:8080/api/v1/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(payload), // Send payload as JSON
            });

            // Check if response is not ok
            if (!response.ok) {
                const errorData = await response.json();
                console.log('Response error details:', errorData); // Log the error details
                alert(errorData)
                throw new Error(errorData.message || 'Login failed');
            }


            const data = await response.json();
            console.log('Login successful', data);

            // Store token and navigate based on user role
            localStorage.setItem('token', data.token);

            if (data.userType.includes('ROLE_ADMIN')) {
                navigate('/customerListPage');
            } else {
                navigate('/customerInfPage');
            }
        } catch (error) {
            setError(error.message || 'Invalid username or password');
        }
    };


    return (
        <div className="flex items-center justify-center min-h-screen bg-gray-100">
            <Card className="w-full max-w-md">
                <CardHeader>
                    <CardTitle>Login</CardTitle>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div className="space-y-2">
                            <Label htmlFor="username">Username</Label>
                            <Input
                                id="username"
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                            />
                        </div>
                        <div className="space-y-2">
                            <Label htmlFor="password">Password</Label>
                            <Input
                                id="password"
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>
                        <div className="space-y-2">
                            <Label>Role</Label>
                            <RadioGroup value={role} onValueChange={setRole} className="flex space-x-4">
                                <div className="flex items-center space-x-2">
                                    <RadioGroupItem value="ROLE_USER" id="role-user" />
                                    <Label htmlFor="role-user">User</Label>
                                </div>
                                <div className="flex items-center space-x-2">
                                    <RadioGroupItem value="ROLE_ADMIN" id="role-admin" />
                                    <Label htmlFor="role-admin">Admin</Label>
                                </div>
                            </RadioGroup>
                        </div>
                        {error && (
                            <Alert variant="destructive">
                                <AlertDescription>{error}</AlertDescription>
                            </Alert>
                        )}
                        <Button type="submit" className="w-full">Login</Button>
                    </form>
                </CardContent>
            </Card>
        </div>
    );
};

export default LoginPage;
