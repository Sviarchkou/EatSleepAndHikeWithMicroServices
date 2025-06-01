<script setup>
import app_header from './Header.vue'
import trip_type_card from './trip-components/TripTypeCard.vue'
import ContinentCard from './trip-components/ContinentCard.vue'
import HomePageSearchPanelCard from './HomePageSearchPanelCard.vue'

import waterTripImage from 'D:/Java/pics/Water trip.jpg';
import hikeTripImage from 'D:/Java/pics/Hike Trip.jpg';
import cyclingTripImage from 'D:/Java/pics/Cycling trip.jpg';
import mountainHikeTripImage from 'D:/Java/pics/Mountain hike trip.jpg';
import skiTripImage from 'D:/Java/pics/Ski trip.jpg';
import othersTripsImage from 'D:/Java/pics/Others.jpg';
import africaImage from 'D:/Java/pics/Africa.jpg';
import asiaImage from 'D:/Java/pics/Asia.jpg';
import australiaImage from 'D:/Java/pics/Australia.jpg';
import europeImage from 'D:/Java/pics/Europe.jpg';
import northAmericaImage from 'D:/Java/pics/North_America.jpg';
import southAmericaImage from 'D:/Java/pics/South America.jpg';
import antarcticaImage from 'D:/Java/pics/Antarctica.png';

import { ref } from 'vue'
import { useRouter } from 'vue-router'
import TripView from "@/views/TripsView.vue";

const router = useRouter()

const trip_types = ref([
  {id: 2, title: 'Hike Trip', image: hikeTripImage},
  {id: 1, title: 'Water Trip', image: waterTripImage},
  {id: 3, title: 'Mountain Hike', image: mountainHikeTripImage},
  {id: 4, title: 'Cycling Hike', image: cyclingTripImage},
  {id: 5, title: 'Ski Hike', image: skiTripImage},
  {id: 6, title: 'Other types', image: othersTripsImage}
])

const continents = ref([
  { id: 1, title: 'Europe',        image: europeImage },
  { id: 2, title: 'North America', image: northAmericaImage},
  { id: 3, title: 'South America', image: southAmericaImage},
  { id: 4, title: 'Asia',          image: asiaImage },
  { id: 5, title: 'Australia',     image: australiaImage},
  { id: 6, title: 'Africa',        image: africaImage},
  { id: 7, title: 'Antarctica',    image: antarcticaImage},
])

function onTripTypeSelect(id) {
  console.log('Clicked card id:', id)
  router.push({
    path: '/trips',
    query: {
      type: id
    }
  });
}
function onContinentSelect(id) {
  console.log('Clicked card id:', id)
  router.push({
    path: '/trips',
    query: {
      continent: id
    }
  });
}
</script>

<template>
  <app_header/>
  <div class="g-md-3 text-center">
    <h1 style="align-content: center; margin-bottom: 20px" >Explore the world by trips!</h1>
  </div>
  <section class="container my-4">
  <HomePageSearchPanelCard/>
  </section>

  <!-- Trip Types Section -->
  <section class="container text-center my-5">
    <h3 class="mb-4">You can choose any types of trips you prefer!</h3>
    <div class="row row-cols-1 row-cols-md-3 g-4 ">
      <trip_type_card
          v-for="trip_type in trip_types"
          :key="trip_type.id"
          :id="trip_type.id"
          :title="trip_type.title"
          :image="trip_type.image"
          @select="onTripTypeSelect"
      />
    </div>
  </section>

  <!-- Continents Section -->
  <div class="container text-center my-5">
    <h3 class="mb-4">Explore each Continent</h3>
    <div class="row g-3">
      <ContinentCard
          v-for="cont in continents"
          :key="cont.id"
          :id="cont.id"
          :title="cont.title"
          :image="cont.image"
          @select="onContinentSelect"
      />
    </div>
  </div>

  <!-- Footer -->
  <footer class="bg-dark text-white text-center py-3 mt-5">
    by Evgeniy Sverchkov
  </footer>


</template>

<style scoped>

.trip-card {
  min-width: 200px;
  margin-right: 1rem;
}
.scrolling-wrapper {
  overflow-x: auto;
  white-space: nowrap;
}
.scrolling-wrapper::-webkit-scrollbar {
  display: none;
}
</style>