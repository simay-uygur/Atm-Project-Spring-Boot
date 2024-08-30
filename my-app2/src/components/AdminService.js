import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1'; // Adjust base URL if needed

const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});
const AdminService = {
    loginAdmin: async (name, password) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/admins/login`, { name, password });
            return response.data; // Return the full response data
        } catch (error) {
            console.error('Error logging in:', error);
            throw error; // Propagate error to be handled by calling component
        }
    },

    listAllCustomers: async (id) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/admins/${id}/allCustomers`);
            return response.data;
        } catch (error) {
            console.error('Error displaying', error);
            throw error;
        }
    },

    updateCustomer: async (id, customerData) => {
        try {
            const response = await axiosInstance.put(`${API_BASE_URL}/customers/${id}`, customerData);
            return response.data;
        } catch (error) {
            console.error('Error updating customer:', error);
            throw error;
        }
    },

    deleteCustomer: async (id) => {
        try {
            await axiosInstance.delete(`${API_BASE_URL}/customers/${id}`);
        } catch (error) {
            console.error('Error deleting customer:', error);
            throw error;
        }
    },
};

export default AdminService;