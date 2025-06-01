<template>
  <div id="map" style="height: 500px;"></div>
</template>

<script>
import L from 'leaflet';
import 'leaflet-routing-machine';

export default {
  name: 'RouteMap',
  mounted() {
    const map = L.map('map').setView([55.751244, 37.618423],10); // Москва

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors',
    }).addTo(map);
/*
    // Добавим маршрут от точки А до точки B
    L.Routing.control({
      waypoints: [
        L.latLng(55.751244, 37.618423), // Москва
        L.latLng(59.9342802, 30.3350986) // Санкт-Петербург
      ],
      routeWhileDragging: true
    }).addTo(map);*/
  }
};
</script>



<!--

<template>
  <TripForm :initialData="tripData" />
</template>

<script>
import TripForm from './trip-components/UpdateTripForm.vue';

export default {
  components: { TripForm },
  data() {
    return {
      tripData: {
        type: "Пеший",
        name: "Эльбрус",
        description: "Крутой маршрут",
        distance: 100,
        duration: 5,
        difficultyMin: 1,
        difficultyMax: 4,
        route: "Маршрут А",
        photoUrl: "https://via.placeholder.com/300"
      }
    };
  }
};
</script>
-->
