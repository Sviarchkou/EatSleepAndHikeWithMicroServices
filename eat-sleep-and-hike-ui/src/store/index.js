import { createStore } from 'vuex' // Импортируем из 'vuex', а не 'vue'

export default createStore({
    state: {
        registrationData: {
            username: '',
            email: '',
            password: ''
        },
        tripData: {
            id: "",
            type: {
                id: 0,
                name: '',
            },
            name: '',
            description: '',
            distance: 0,
            duration: 0,
            difficultyMin: 0,
            difficultyMax: 0,
            route: {
                id: 0,
                name: '',
            },
            photoFile: null,
            photoPreview: null,
        },
        points: []
    },

    mutations: {
        setRegistrationData(state, data) {
            state.registrationData = data;
        },
        setTripData(state, data) {
            state.tripData = data;
        },
        setPoints(state, newPoints) {
            state.points = newPoints;
        },
        addPoint(state, point) {
            state.points.push(point);
        },
        removePoint(state, index) {
            state.points.splice(index, 1);
        }
    },
    getters: {
        registrationData: state => state.registrationData,
        tripData: state => state.tripData,
        getPoints: (state) => state.points
    }
})