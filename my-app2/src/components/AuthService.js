import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1'; // Ensure this matches your backend URL

const AuthService = {
    login: async (username, password) => {
        try {
            const response = await axios.post(`${API_URL}/login`, {
                username,
                password
            });
            return response.data;
        } catch (error) {
            console.error('Login error:', error.response ? error.response.data : error.message);
            throw error;
        }
    },

    logout: () => {
        localStorage.removeItem('user');
    },

    getCurrentUser: () => {
        return JSON.parse(localStorage.getItem('user'));
    },

    authHeader: () => {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user && user.token) {
            return { Authorization: 'Bearer ' + user.token };
        } else {
            return {};
        }
    },

    validateToken: async () => {
        try {
            const response = await axios.get(`${API_URL}/authenticate`, {
                headers: AuthService.authHeader()
            });
            return response.data;
        } catch (error) {
            throw error.response.data;
        }
    }
};

export default AuthService;
