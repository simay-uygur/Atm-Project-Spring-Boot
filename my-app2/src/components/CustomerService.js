// src/CustomerService.js

import axios from 'axios';

// Set the base URL of your Spring Boot API
const API_URL = 'http://localhost:8080/api/v1/customers';

class CustomerService {
    getCustomerById(id) {
        return axios.get(`${API_URL}/${id}`);
    }

    // Add other methods for create, update, delete, etc.
}

export default new CustomerService();
