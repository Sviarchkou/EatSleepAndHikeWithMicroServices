import axios from 'axios';

const API_URL_REG = 'http://localhost:8080/register';
const API_URL_EMAIL_CHECK = 'http://localhost:8080/email-check';


const regService = {
    async emailCheck(username, email, password) {
        try {
            delete axios.defaults.headers.common['Authorization'];
            await axios.post(API_URL_EMAIL_CHECK, { username, email, password }, {
                headers: {
                    'Content-Type': 'application/json'
                },
            });
        } catch (error) {
            console.log("status" , error.response.status)
            if (error.response.status === 301){
                return
            }
            throw new Error('Email push failed: ' + (error.response?.data?.message || error.message));
        }
    },

    async register(username, email, password, confirmNum) {
        try {
            delete axios.defaults.headers.common['Authorization'];
            const res = await axios.post(API_URL_REG, { username, email, password, confirmNum }, {
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const id = res.data.id;
            if (id) {
                localStorage.setItem('id', id);
            } else {
                throw new Error('No id is presented');
            }
        } catch (error) {
            throw new Error('Registration failed: ' + (error.response?.data?.message || error.message));
        }
    },
};

export default regService;