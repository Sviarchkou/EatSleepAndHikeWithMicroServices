import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from "./store/index.js";
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'leaflet/dist/leaflet.css';
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';

const app = createApp(App);
app.use(router);
app.use(store);
app.mount('#app');

