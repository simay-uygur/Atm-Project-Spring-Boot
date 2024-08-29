import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1/customers'; // Adjust base URL as needed

const CustomerService = {
    // Fetch customer information by ID
    getCustomerInfo: async (userId) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/get/${userId}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching customer information:', error);
            throw error;
        }
    },

    // Retrieve money for the customer
    getMoney: async () => {
        try {
            const response = await axios.post(`${API_BASE_URL}/get-money`);
            return response.data;
        } catch (error) {
            console.error('Error retrieving money:', error);
            throw error;
        }
    },

    // Enter money into the customer's account
    enterMoney: async (amount) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/enter-money`, { amount });
            return response.data;
        } catch (error) {
            console.error('Error entering money:', error);
            throw error;
        }
    },

    // Send money from the customer's account to another IBAN
    sendMoney: async (iban, amount) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/send-money`, { iban, amount });
            return response.data;
        } catch (error) {
            console.error('Error sending money:', error);
            throw error;
        }
    },
};

export default CustomerService;
