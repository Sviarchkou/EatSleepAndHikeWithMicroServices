import axios from 'axios';

const API_URL = 'http://localhost:8080/trips';


const tripService = {
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
    async create(form){
        try {
            const res1 = await axios.post(API_URL, {
                name: form.name,
                description: form.description,
                distance: form.distance,
                duration: form.duration,
                tripTypeDto: form.type,
                routeDto: form.route,
                minDifficultyCategory: form.difficultyMin,
                maxDifficultyCategory: form.difficultyMax
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 201)
                throw new Error('201 response status is expected, got ' + res1.status);

        } catch (error) {
            if (error.status === 403){
                alert("Session is finished. Login again")
                this.$router.push('/login');
            }
            console.log("Error: ", error)
            throw new Error('Route creation error: ' + (error.response?.data?.message || error.message));
        }
    },
    async update(form){
        try {
            const res1 = await axios.post(API_URL, {
                id: form.id,
                name: form.name,
                description: form.description,
                distance: form.distance,
                duration: form.duration,
                tripTypeDto: form.type,
                routeDto: form.route,
                minDifficultyCategory: form.difficultyMin,
                maxDifficultyCategory: form.difficultyMax
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 201)
                throw new Error('201 response status is expected, got ' + res1.status);

        } catch (error) {
            if (error.status === 403){
                alert("Session is finished. Login again")
                this.$router.push('/login');
            }
            console.log("Error: ", error)
            throw new Error('Route creation error: ' + (error.response?.data?.message || error.message));
        }
    }
};

export default tripService;