<script>
import TripCard from './trip-components/TripCard.vue';
import Header from "@/views/Header.vue";
import mountainImage from 'D:/Java/pics/ANPH-0528.jpg';
import countryService from "@/services/country.js";
import tripService from "@/services/trip.js";
import tripTypeService from "@/services/tripType.js";

export default {
  name: 'TripsPage',
  components: {
    TripCard,
    Header
  },
  data() {
    return {
      trips: [
        /*{
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 1,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 4,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 2,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 2,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 2,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Mountain Adventure',
          tripType: 'Hiking',
          duration: 7,
          distance: 50,
          difficulty: 3,
          routeDescription: 'A scenic route through the mountains with breathtaking views.',
          countries: ['Switzerland', 'Italy'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Desert Expedition',
          tripType: 'Camping',
          duration: 5,
          distance: 30,
          difficulty: 1,
          routeDescription: 'Explore the vast desert landscapes and starry nights.',
          countries: ['Morocco'],
          imageSrc: mountainImage,
        },
        {
          id: "foeifnlqn",
          tripName: 'Coastal Journey',
          tripType: 'Cycling',
          duration: 3,
          distance: 20,
          difficulty: 2,
          routeDescription: 'Cycle along the beautiful coastline with ocean views.',
          countries: ['Spain', 'France'],
          imageSrc: mountainImage,
        },*/
      ],
      tripTypes: ['Water Trip', 'Hike Trip', 'Cycling Trip', 'Ski Trip', 'Mountain Hike', 'Others'],
      continents: ['Europe', 'North America', 'South America', 'Asia', 'Australia', 'Africa', 'Antarctica'],
      countries: ['Austria'],
      //countries: countries,
      selectedTripTypes: [],
      selectedContinents: [],
      selectedCountries: [],
      difficultyMin: 0,
      difficultyMax: 6,
    };
  },
  mounted() {
    this.fetchCountries()
    this.fetchTripTypes()
    this.fetchTrips()

    const { type, continent } = this.$route.query;

    if (type) {
      this.selectedTripTypes.push(this.tripTypes[type-1]);
    }
    if (continent) {
      this.selectedContinents.push(this.continents[continent-1]);
    }
  },
  methods: {
    updateDifficultyMin() {
      if (this.difficultyMin > this.difficultyMax) {
        this.difficultyMax = this.difficultyMin;
      }
    },
    updateDifficultyMax() {
      if (this.difficultyMax < this.difficultyMin) {
        this.difficultyMin = this.difficultyMax;
      }
    },
    async fetchCountries(){
      const countries = await countryService.getAll()
      this.countries = countries.map(c => c.name)
      // this.countries = countries
      console.log("Countries: ", this.countries)
      console.log("This Countries: ", this.countries)
    },
    async fetchTripTypes(){
      const tripsJson = await tripTypeService.getAll()
      this.tripTypes = tripsJson.map(tt => tt.name)
      //this.tripTypes = tripsJson
      console.log(this.tripTypes)
    },
    async fetchTrips(){
      const tripsJson = await tripService.getAll()
      const trips = []
      tripsJson.forEach(t => {
        const countries = []
        t.routeDto.countryDtos.forEach(c => {
          countries.push({name: c.name, continent: c.continentDto.name})
        })
        trips.push({
          id: t.id,
          tripName: t.name,
          tripType: t.tripTypeDto.name,
          duration: t.duration,
          distance: t.distance,
          difficultyMin: t.minDifficultyCategory,
          difficultyMax: t.maxDifficultyCategory,
          routeDescription: t.routeDto.description,
          countries: countries,
          imageSrc: mountainImage,
        })
        this.trips = trips
      })
      console.log("Trips", trips)
      //onsole.log("This Trips", this.trips)
    },
    parseDifficulty(difficultyCategory){
      switch (difficultyCategory) {
        case "BEYOND_CATEGORICAL":
          return 0;
        case "I":
          return 1;
        case "II":
          return 2;
        case "III":
          return 3;
        case "IV":
          return 4;
        case "V":
          return 5;
        case "VI":
          return 6;
      }
    }
  },
  computed: {
      filteredTrips() {
        return this.trips.filter(trip => {
          // Фильтр по типам
          const typeMatch = this.selectedTripTypes.length === 0 || this.selectedTripTypes.includes(trip.tripType);

          // Фильтр по континентам (через континент внутри country)
          const continentMatch =
              this.selectedContinents.length === 0 ||
              trip.countries.some(country => this.selectedContinents.includes(country.continent));

          // Фильтр по странам (по имени)
          const countryMatch =
              this.selectedCountries.length === 0 ||
              trip.countries.some(country => this.selectedCountries.includes(country.name));

          // Фильтр по диапазону сложности
          const difficultyMatch =
              this.parseDifficulty(trip.difficultyMin) <= this.difficultyMax &&
              this.parseDifficulty(trip.difficultyMax) >= this.difficultyMin;

          return typeMatch && continentMatch && countryMatch && difficultyMatch;
        });
      }
  }


};
</script>


<template>

  <Header/>

  <div class="trips-page" style="padding-top:25px">
    <!-- Панель фильтров -->
    <div class="row" style="min-width: 30%;margin-right: 5%;;">
      <div class="col-md-4 bg-light p-3" style="min-width: 90%; margin-left: 30%; max-height: 1250px">
<!--        <h3>Trip types</h3>-->
        <h3 class="filter-heading trip-types-heading">Trip types</h3>
        <div class="form-check" v-for="type in tripTypes" :key="type">
          <input type="checkbox" class="form-check-input" :id="type" v-model="selectedTripTypes" :value="type">
          <label class="form-check-label filter-label" :for="type">{{ type }}</label>
        </div>

        <h3 class="mt-3 filter-heading" >Continents</h3>
        <div class="form-check" v-for="continent in continents" :key="continent">
          <input type="checkbox" class="form-check-input" :id="continent" v-model="selectedContinents" :value="continent">
          <label class="form-check-label filter-label" :for="continent">{{ continent }}</label>
        </div>

        <h4 class="mt-3">Difficulty Category</h4>
        <div class="mt-3 align-items-center">
          <h6 class="mt-3 filter-label">From:</h6>
          <div class="mt-3" style="margin-left: 5px; margin-right: 5px">
            <div class="d-flex justify-content-between mt-1">
              <span>0</span>
              <span>I</span>
              <span>II</span>
              <span>III</span>
              <span>IV</span>
              <span>V</span>
              <span>VI</span>
            </div>
          </div>

          <input
              type="range"
              class="form-range"
              min="0"
              max="6"
              v-model.number="difficultyMin"
              @input="updateDifficultyMin"
          >

          <h6 class="mt-3 filter-label">To:</h6>
          <div class="mt-3" style="margin-left: 5px; margin-right: 5px">
            <div class="d-flex justify-content-between mt-1">
              <span>0</span>
              <span>I</span>
              <span>II</span>
              <span>III</span>
              <span>IV</span>
              <span>V</span>
              <span>VI</span>
            </div>
          </div>
          <input
              type="range"
              class="form-range"
              min="0"
              max="6"
              v-model.number="difficultyMax"
              @input="updateDifficultyMax"
          >
        </div>

        <!-- ... other sections ... -->

        <h4 class="filter-heading countries-heading mt-3">Countries</h4>
        <div class="mb-3" style="background: #FFFFFF; padding-left: 15px;">
          <div class="countries-panel">
            <div class="form-check" style="margin-top: 5px" v-for="country in countries" :key="country">
              <input type="checkbox" class="form-check-input" :id="country" v-model="selectedCountries" :value="country">
              <label class="form-check-label filter-label" :for="country">{{ country }}</label>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Список карточек TripCard -->
    <div class="trips-list">
      <div class="container">
        <div class="row">
          <div class="md-1" style="margin-bottom: 15px" v-for="(trip, index) in filteredTrips" :key="index">
            <TripCard
                :id="trip.id"
                :trip-name="trip.tripName"
                :trip-type="trip.tripType"
                :duration="trip.duration"
                :distance="trip.distance"
                :minDifficulty="trip.difficultyMin"
                :maxDifficulty="trip.difficultyMax"
                :route-description="trip.routeDescription"
                :countries="trip.countries"
                :image-src="trip.imageSrc"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
  <footer class="bg-dark text-white text-center py-3 mt-5">
    by Evgeniy Sverchkov
  </footer>
</template>

<style scoped>
.countries-panel {
  max-height: 350px;
  overflow-y: auto;
  padding-right: 10px;
}

.trips-page {
  display: flex;
  min-height: 100vh;
  background-color: #fff;
}

.trips-list {
  width: 75%;
  padding-left: 5%;
  padding-right: 8%;
}

.filter-heading {
  font-size: 1.75rem; /* Larger heading size for Trip types, Continents, and Countries */
}

.filter-label {
  font-size: 1.15rem; /* Larger font size for checkbox labels */
}
/*
@media (max-width: 768px) {
  .trips-page {
    flex-direction: column;
  }

!*  .filters-panel {
    width: 100%;
    height: auto;
    position: relative;
    padding-bottom: 20px;
  }*!
  .trips-list {
    width: 75%;
    padding-left: 5%;
    padding-right: 2%;
  }

  .countries-panel {
    max-height: 150px;
    overflow-y: auto;
    padding-right: 10px;
  }

  !* Ensure both range inputs overlap in the same space *!
  .form-range {
    width: 100%;
    position: relative;
    margin: 0;
  }

  .form-range:nth-child(2) {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
  }
}*/
</style>