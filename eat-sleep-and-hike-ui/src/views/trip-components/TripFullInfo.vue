<template>
  <app_header/>
  <div class="container my-5 p-4 border rounded shadow-sm bg-white">

    <div class="trip-header">
      <!-- Left: Info -->
      <div class="trip-info text-black">
        <h4 class="fw-bold">{{ trip.tripName }}</h4>
        <p class="mb-1">Type: {{ trip.tripType }}</p>
        <p class="mb-1">Duration: {{ trip.duration }} days</p>
        <p class="mb-1">Distance: {{ trip.distance }} km</p>
        <p class="mb-0">Min Difficulty Category: {{trip.minDifficulty}}</p>
        <p class="mb-0">Max Difficulty Category: {{trip.maxDifficulty}}</p>
      </div>

      <!-- Right: Photo -->
      <div class="trip-photo">
        <img src="D:/Java/pics/ANPH-0528.jpg" alt="Trip Photo">
      </div>
    </div>

<!--    &lt;!&ndash; Header &ndash;&gt;
    <div class="d-flex align-items-stretch mb-4">
      <div class="bg-light p-3 flex-grow-1">
        <h4 class="fw-bold">{{ tripName }}</h4>
        <p class="mb-1">Type: {{ tripType }}</p>
        <p class="mb-1">Duration: {{ duration }} days</p>
        <p class="mb-1">Distance: {{ distance }} km</p>
        <p class="mb-0">Difficulty category: </p>
        <div class="row g-1" >
          <div class="col-auto" v-for="dif in difficulties" :key="dif">
            {{ dif.difficulty }}
          </div>
        </div>
      </div>
      <div class="bg-dark flex-grow-1"></div>
    </div>-->

    <!-- Description -->
    <div class="mb-4">
      <h4 class="fw-bold">Description</h4>
      <p>{{ trip.description }}</p>
    </div>

<!--    &lt;!&ndash; Difficulty &ndash;&gt;
    <div class="mb-4">
      <h6 class="fw-bold">Difficulty categories</h6>
      <div class="col-auto" v-for="dif in difficulties" :key="dif">
        <p>{{ dif.difficulty }} category</p>
        <p>{{ dif.desc }}</p>
      </div>
    </div>-->

    <!-- Route -->
    <div class="mb-4">
      <h4 class="fw-bold">Route</h4>
      <p>{{ trip.routeDescription }}</p>
    </div>

    <!-- Points -->
    <div
        v-for="(point, index) in trip.points"
        :key="index"
        class="mb-3 border-top pt-3"
    >
      <h4 class="fw-bold">Point {{ index + 1 }}:</h4>
      <p><strong>Latitude:</strong> {{ point.latitude }}</p>
      <p><strong>Longitude:</strong> {{ point.longitude }}</p>
      <p><strong>Description:</strong> {{ point.description }}</p>
    </div>

    <!-- Countries -->
    <div class="mb-3 border-top pt-3" style="margin-top: 5px">
      <h4 class="fw-bold">Countries</h4>
      <div class="mb-0"  v-for="country in trip.countries" :key="country">
        <p>{{ country }}</p>
      </div>
    </div>
  </div>

  <div id="map" style="height: 700px; margin: 100px 150px;"></div>

  <div class="mb-3" style="text-align: center; margin-bottom: 25px" v-if="authService.isAdmin()">
    <button class="btn btn-warning" style="width: 30%" @click="toUpdateTrip">
      Update this Trip
    </button>
  </div>
  <footer class="bg-dark text-white text-center py-3 mt-5">
    by Evgeniy Sverchkov
  </footer>
</template>

<script>
import L from 'leaflet';
import tripService from "@/services/trip.js";
import routeService from "@/services/route.js";
import authService from "@/services/auth.js";
import app_header from "@/views/Header.vue"

export default {
  name: "TripDetails",
  components:{
    app_header
  },
  computed: {
    authService() {
      return authService
    }
  },
  data() {
    return {
      trip: {
        tripName: {type: String, default: "Trip Name"},
        tripType: {type: String, default: 'cycling'},
        description: {type: String, default: 'fnaeu efu wefiuhu wehuhef welf quiewhf '},
        duration: {type: Number, default: 0},
        distance: {type: Number, default: 0},
        minDifficulty: "{type: String, default: 0}",
        maxDifficulty: "{type: String, default: 6}",
        routeDescription: {type: String, default: 'desс...'},
        countries: {type: Array, default: ['Country1', 'Country2...']},
        imageSrc: {type: String, default: ''},
        points: {
          type: Array,
          default: () => [
            {latitude: 45.123456, longitude: 7.654321, description: 'Starting point near the lake.'},
            {latitude: 45.234567, longitude: 7.765432, description: 'Scenic viewpoint.'},
            {latitude: 45.345678, longitude: 7.876543, description: 'Camp site for night stay.'},
            {latitude: 45.456789, longitude: 7.987654, description: 'Summit of the mountain.'}
          ],
        },
      }
    }
  },
  mounted() {
    this.fetchTrip()
  },
  methods: {
    async fetchTrip() {
      const trip = await tripService.get(this.$route.params.id)
      console.log("Trip", trip)

      this.trip.tripName = trip.name
      this.trip.tripType = trip.tripTypeDto.name
      this.trip.description = trip.description
      this.trip.duration = trip.duration
      this.trip.distance = trip.distance
      this.trip.minDifficulty = trip.minDifficultyCategory
      this.trip.maxDifficulty = trip.maxDifficultyCategory
      this.trip.routeDescription = trip.routeDto.description
      this.trip.countries = trip.routeDto.countryDtos.map(c => c.name)

      this.trip.points = await routeService.getRoutePointByRouteId(trip.routeDto.id)

      const map = L.map('map').setView([this.trip.points[0].latitude, this.trip.points[0].longitude], 9);
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      }).addTo(map);

      this.trip.points.forEach((point, index) => {
        console.log(point.latitude, point.longitude)
        L.marker([point.latitude, point.longitude])
            .addTo(map)
            .bindPopup(`<b>Point ${index + 1}</b><br>${point.description || 'iug'}`);
      });
      //this.imageSrc = trip.
    },
    toUpdateTrip(){
      this.$router.push('/trips/update/' + this.$route.params.id);
    }
  }
}
</script>

<style scoped>
.bg-dark {
  background-color: #343a40;
}
p {
  font-size: 1.15rem;
  margin: 0;
  padding: 0;
}

.trip-header {
  display: flex;
  height: 300px;
  overflow: hidden;
  margin-bottom: 25px;
}

.trip-info {
  background-color: #ddd;
  flex: 1;
  padding: 2rem;
  position: relative;
  clip-path: polygon(0 0, 75% 0, 65% 100%, 0% 100%);
  z-index: 2;
}

.trip-photo {
  background-color: #474343;
  flex: 1;
  position: relative;
  overflow: hidden;
}

.trip-photo img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}
</style>
