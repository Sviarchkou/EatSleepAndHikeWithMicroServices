import axios from 'axios';

const API_URL = 'http://localhost:8080/login';

const authService = {
    async login(usernameOrEmail, password) {
        try {
            delete axios.defaults.headers.common['Authorization'];
            console.log("Request is ready")
            console.log('usernameOrEmail:', usernameOrEmail);
            console.log('password:', password);
            const res = await axios.post(API_URL, { usernameOrEmail, password }, {
                //timeout: 5000,
            });
            /*const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    usernameOrEmail,
                    password,
                }),
            });
            const data = await response.json()
            const token = data.accessToken;
            if (token) {
                localStorage.setItem('jwt', token);
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                console.log("Access token saved: ", token)
            } else {
                throw new Error('No token received from server');
            }*/
            const token = res.data.accessToken;
            if (token) {
                localStorage.setItem('jwt', token);
                const role = res.data.role;
                if (!role || role !== "")
                    localStorage.setItem('role', role);
                else
                    localStorage.setItem('role', 'TOURIST');
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
                axios.defaults.headers.common['Content-Type'] = `application/json`;
                console.log("Access token saved: ", token)
            } else {
                throw new Error('No token received from server');
            }
        } catch (error) {
            console.log("Error: ", error)
            throw new Error('Login failed: ' + (error.response?.data?.message || error.message));
        }
    },

    logout() {
        localStorage.removeItem('jwt');
        delete axios.defaults.headers.common['Authorization'];
    },

    isAuthenticated() {
        return !!localStorage.getItem('jwt');
    },

    isAdmin() {
        return localStorage.getItem('role') === "ADMIN";
    },
};

export default authService;