import axios from 'axios';

const API_URL_ROUTES = 'http://localhost:8080/routes';
const API_URL_ROUTE_POINTS_ALL = 'http://localhost:8080/route-points/all';
const API_URL_ROUTE_POINTS_IN_ROUTE = 'http://localhost:8080/route-points/in-route/';
const API_URL_ROUTE_POINTS = 'http://localhost:8080/route-points';

const routeService = {
    async createAll(form) {
        try {
            const res1 = await axios.post(API_URL_ROUTES, {
                name: form.title,
                description: form.description,
                countryDtos: form.countries
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 201)
                throw new Error('201 response status is expected, got ' + res1.status);

            const routeId = res1.data.id
            if (routeId) {
                /*this.$store.commit('setTripData',{
                    routeId: routeId
                })*/
                console.log("RouteID saved: ", routeId)
            } else {
                throw new Error('No id vas received')
            }

            const routePointDtos =  []

            let i = 1;
            form.points.forEach((p) => {
                routePointDtos.push({
                    routeDto:{
                        id: routeId
                    },
                    latitude: p.latitude,
                    longitude: p.longitude,
                    description: p.description,
                    sequenceNumber: i++
                })
            })
            console.log('points: ', routePointDtos);
            console.log("Sending request")
            const res2 = await axios.post(API_URL_ROUTE_POINTS_ALL,  routePointDtos, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res2.status !== 201)
                throw new Error('201 response status is expected while sending route_points/all request, got ' + res1.status);
        } catch (error) {
            if (error.status === 403){
                alert("Session is finished. Login again")
                this.$router.push('/login');
            }

            console.log("Error: ", error)
            throw new Error('Route creation error: ' + (error.response?.data?.message || error.message));
        }
    },

    async update(form) {
        try {
            const res1 = await axios.put(API_URL_ROUTES, {
                id: form.id,
                name: form.title,
                description: form.description,
                countryDtos: form.countries
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 200)
                throw new Error('200 response status is expected, got ' + res1.status);

            const points =  []

            let i = 1;
            form.points.forEach((p) => {
                points.push({
                    id: p.id,
                    routeDto:{
                        id: form.id
                    },
                    latitude: p.latitude,
                    longitude: p.longitude,
                    description: p.description,
                    sequenceNumber: i++
                })

            })
            console.log('points: ', points);
            console.log("Sending request")
            const res2 = await axios.put(API_URL_ROUTE_POINTS + "/" + form.id, points, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res2.status !== 200)
                throw new Error('200 response status is expected while sending route_points/all request, got ' + res1.status);
        } catch (error) {
            if (error.status === 403){
                alert("Session is finished. Login again")
                this.$router.push('/login');
            }
            console.log("Error: ", error)
            throw new Error('Route creation error: ' + (error.response?.data?.message || error.message));
        }
    },

    async getAll() {
        try {
            const res1 = await axios.get(API_URL_ROUTES, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 200)
                throw new Error('200 response status is expected, got ' + res1.status);

            return res1.data
        } catch (error) {
            console.log("Error: ", error)
            throw new Error((error.response?.data?.message || error.message));
        }
    },

    async getById(id) {
        try {
            const res1 = await axios.get(API_URL_ROUTES + "/" + id, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 200)
                throw new Error('200 response status is expected, got ' + res1.status);

            return res1.data
        } catch (error) {
            console.log("Error: ", error)
            throw new Error(error.response?.data?.message || error.message);
        }
    },

    async getRoutePointByRouteId(route_id){
        try {
            const res1 = await axios.get(API_URL_ROUTE_POINTS_IN_ROUTE + route_id, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem("jwt"),
                },
            });
            if (res1.status !== 200)
                throw new Error('200 response status is expected, got ' + res1.status);
            return res1.data
        } catch (error) {
            console.log("Error: ", error)
            throw new Error(error.response?.data?.message || error.message);
        }
    }
};

export default routeService;