<template>
  <app_header/>
  <button class="btn btn-outline-danger w-100" @click="toTrip">Back to Trip</button>
  <div class="container bg-white p-4 my-5 rounded shadow-sm" style="max-width: 700px;">
    <h5 class="mb-4">Creation</h5>

    <!-- Title -->
    <div class="mb-3">
      <label class="form-label">Title</label>
      <input v-model="form.title" type="text" class="form-control" placeholder="Enter title" required/>
    </div>

    <!-- Route Points -->
    <div v-for="(point, index) in form.points" :key="index" class="mb-4 p-4 border rounded bg-light position-relative">
      <h6 class="mb-3">{{ index + 1 }}. Point</h6>

      <!-- Delete button -->
      <button
          type="button"
          class="btn-close position-absolute top-0 end-0 m-3"
          aria-label="Remove point"
          @click="removePoint(index)"
      ></button>

      <div class="row">
        <!-- Latitude & Longitude -->
        <div class="col-md-6 d-flex flex-column gap-2">
          <div>
            <label class="form-label">Latitude:</label>
            <input v-model="point.latitude" type="text" class="form-control" placeholder="Enter latitude" />
          </div>
          <div>
            <label class="form-label">Longitude:</label>
            <input v-model="point.longitude" type="text" class="form-control" placeholder="Enter longitude" />
          </div>
        </div>

        <!-- Description -->
        <div class="col-md-6">
          <label class="form-label">Description:</label>
          <textarea v-model="point.description" class="form-control h-100" style="max-height: 78%" rows="4" placeholder="Enter description"></textarea>
        </div>
      </div>
    </div>

    <!-- Add Point Button -->
    <div class="mb-3">
      <button class="btn btn-outline-primary w-100" @click="addPoint">Add route point</button>
    </div>

    <!-- Description -->
    <div class="mb-3">
      <label class="form-label">Description</label>
      <textarea v-model="form.description" class="form-control" rows="4" placeholder="Route description"></textarea>
    </div>

    <h6 class="filter-heading countries-heading mt-3">Set countries</h6>
    <div class="mb-3" style="background: #FFFFFF; padding-left: 15px;">
      <div class="countries-panel">
        <div class="form-check" style="margin-top: 5px" v-for="country in countries" :key="country">
          <input type="checkbox" class="form-check-input" :id="country" v-model="form.countries" :value="country">
          <label class="form-check-label filter-label" :for="country">{{ country.name }}</label>
        </div>
      </div>
    </div>
    <!-- Submit -->
    <button class="btn btn-success w-100" @click="submitForm">Create route</button>
  </div>
</template>

<script>
import routeService from "@/services/route.js";
import countryService from "@/services/country.js";
import app_header from "@/views/Header.vue"

export default {
  name: "RouteCreationForm",
  components: {
    app_header
  },
  data() {
    return {
      form: {
        title: "",
        description: "",
        points: [
          { latitude: "", longitude: "", description: "" }
        ],
        countries: [],
      },
      countries: ['Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy',
        'Austria', 'Australia', 'Belgium', 'Belarus', 'Italy'],
      path: ""
    };
  },
  mounted() {
    const { operation, id } = this.$route.query;
    console.log(operation, id)
    if (operation === "update"){
      this.path = "/trips/update/" + id
    } else{
      this.path = "/trips/create"
    }
    this.fetchCountries()
  },
  methods: {
    toTrip(){
      this.$router.push(this.path);
    },
    addPoint() {
      this.form.points.push({
        latitude: "",
        longitude: "",
        description: ""
      });
    },
    removePoint(index) {
      this.form.points.splice(index, 1);
    },
    submitForm() {
      if (this.form.points.length === 0){
        alert("Route can contains no points")
        return
      }
      try{
        console.log("routeService.createAll(this.form)")
        routeService.createAll(this.form)
      }catch (e){
        alert("An error occurred when creating the route")
        console.error(e)
        return
      }
      console.log("Route created:", this.form);
      this.$router.push('/trips/create');
      alert("Route created!");
    },
    async fetchCountries(){
      const countryDtos = await countryService.getAll()
      this.countries = countryDtos.map(c => {
        return {
          id: c.id,
          name: c.name
        }
      })
    }
  },
  watch: {
    '$route.query': {
      immediate: true, // вызовется при загрузке компонента
      handler(newQuery) {
        if (newQuery.operation === "update") {
          this.path = `/trips/update/${newQuery.id}`;
        } else {
          this.path = "/trips/create";
        }
      }
    }
  }
};
</script>

<style scoped>
h6 {
  font-weight: bold;
}

.btn-close {
  font-size: 0.9rem;
}

.countries-panel {
  max-height: 150px;
  overflow-y: auto;
  padding-right: 10px;
}
</style>
