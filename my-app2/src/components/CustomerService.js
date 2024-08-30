import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1'; // Adjust base URL if needed

const CustomerService = {
    getCustomerInfo: async (id) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/customers/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching customer info:', error);
            throw error; // Propagate error to be handled by calling component
        }
    },
};

export default CustomerService;