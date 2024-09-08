import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/admins'; // Adjust the base URL as needed

const createUser = async (user) => {
    const response = await axios.post(`${API_URL}/register`, user);
    return response.data;
};

const UserService = {
    createUser
};

export default UserService;
