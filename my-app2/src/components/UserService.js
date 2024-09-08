import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1/customers'; // Adjust if needed
const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

const createUser = async (user) => {
    const response = await axios.post(`${API_BASE_URL}/register`, user);
    return response.data;
};

const UserService = {


    loginUser: async (name, password) => {
        try {
            const loginUser = (username, password) => {
                return axios.post(`${API_BASE_URL}/authenticate`, { username, password });
            };
        } catch (error) {
            console.error('Error logging in:', error);
            throw error;
        }
    },
    getCustomerInfo: async (id) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching customer info:', error);
            throw error;
        }
    },

    withdrawMoney: async (id, amount) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/${id}/withdraw`, null, {
                params: {amount: parseFloat(amount) }
            });
            return response;
        } catch (error) {
            console.error('Error withdrawing money:', error);
            throw error;
        }
    },

    depositMoney: async (id, amount) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/${id}/deposit`, null, {
                params: { amount: parseFloat(amount) }
            });
            return response;
        } catch (error) {
            console.error('Error depositing money:', error);
            throw error;
        }
    },

    transferMoney: async (id, iban, amount) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/${id}/sendMoney`, null, {
                params: { iban, amount: parseFloat(amount) }
            });
            return response;
        } catch (error) {
            console.error('Error transferring money:', error);
            throw error;
        }
    },


    createUser(param) {
        
    }
};

export default UserService;
