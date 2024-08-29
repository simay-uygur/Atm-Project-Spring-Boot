import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1'; // Adjust base URL if needed

const UserService = {
    loginUser: async (name, password) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/customers/login`, { name, password });
            return response.data;
        } catch (error) {
            console.error('Error logging in:', error);
            throw error; // Propagate error to be handled by calling component
        }
    },
};

export default UserService;
