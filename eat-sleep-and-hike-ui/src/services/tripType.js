import axios from 'axios';

const API_URL = 'http://localhost:8080/trip-types';


const tripTypeService = {
    async getAll() {
        try {
            const response = await axios.get(API_URL, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            return response.data;
        } catch (error) {
            console.log("status" , error.response.status)
            if (error.response.status === 301){
                return
            }
            throw new Error(error.response?.data?.message || error.message);
        }
    },
    async get(id) {
        try {
            const response = await axios.get(API_URL + "/" + id, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            return response.data;
        } catch (error) {
            console.log("status" , error.response.status)
            if (error.response.status === 301){
                return
            }
            throw new Error(error.response?.data?.message || error.message);
        }
    },
};

export default tripTypeService;