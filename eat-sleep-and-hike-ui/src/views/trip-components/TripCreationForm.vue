<template>
  <app_header/>
  <div class="container bg-white p-4 my-5 rounded shadow-sm">
    <h5 class="mb-4">Trip Creation</h5>

    <!-- Тип похода -->
    <div class="mb-3">
      <label class="form-label">Trip type</label>
      <select v-model="form.type" class="form-select">
        <option v-for="type in types" :key="type" :value="type">{{ type.name }}</option>
      </select>
    </div>

    <!-- Название -->
    <div class="mb-3">
      <label class="form-label">Title</label>
      <input v-model="form.name" type="text" class="form-control"/>
    </div>

    <!-- Описание -->
    <div class="mb-3">
      <label class="form-label">Description</label>
      <textarea v-model="form.description" class="form-control" rows="3"></textarea>
    </div>

    <!-- Расстояние и продолжительность -->
    <div class="row mb-3">
      <div class="col">
        <label class="form-label">Distance (km)</label>
        <input v-model.number="form.distance" type="number" min="0" class="form-control"/>
      </div>
      <div class="col">
        <label class="form-label">Duration (days)</label>
        <input v-model.number="form.duration" type="number" min="0" class="form-control"/>
      </div>
    </div>

    <!-- Категория сложности -->
    <div class="mb-4">
      <label class="form-label" style="padding: 0; margin: 0">Difficulty category</label>
      <div class="mt-3 align-items-center" style="padding: 0; margin: 0">
        <div class="flex-grow-1">
          <label class="form-label small text-muted">Min:</label>
          <input
              v-model.number="form.difficultyMin"
              type="range"
              min="0"
              max="6"
              class="form-range"
              @input="validateDifficulty"
          />
        </div>
        <div class="d-flex justify-content-between small text-muted"
             style="padding-left: 5px;padding-right: 5px; margin-bottom: 10px">
          <span>0</span><span>I</span><span>II</span><span>III</span><span>IV</span><span>V</span><span>VI</span>
        </div>
        <div class="flex-grow-1">
          <label class="form-label small text-muted">Max:</label>
          <input
              v-model.number="form.difficultyMax"
              type="range"
              min="0"
              max="6"
              class="form-range"
              @input="validateDifficulty"
          />
        </div>
      </div>
      <div class="d-flex justify-content-between small text-muted" style="padding-left: 5px;padding-right: 5px">
        <span>0</span><span>I</span><span>II</span><span>III</span><span>IV</span><span>V</span><span>VI</span>
      </div>
      <div v-if="difficultyError" class="text-danger small mt-1">
        Min difficulty can not be higher than max
      </div>
    </div>

    <!-- Маршрут с поиском -->
    <div class="mb-3">
      <label class="form-label">Route</label>
      <input
          type="text"
          class="form-control mb-2"
          v-model="searchQuery"
          placeholder="Search by title"
      />
      <select class="form-select" v-model="form.route">
        <option v-for="route in filteredRoutes" :key="route" :value="route">
          {{ route.name }}
        </option>
      </select>
      <div class="text-danger small mt-1">
        If there is no suitable route you can create a new one by clicking the button below
      </div>
    </div>
    <!-- Кнопка -->
    <button class="btn btn-outline-success" :disabled="difficultyError" @click="createRoute">
      Create new route
    </button>
    <!-- Фото -->
    <div class="mb-4" style="margin-top: 15px">
      <label class="form-label">Photo</label>
      <input type="file" class="form-control" accept="image/*" @change="handleImageUpload"/>
      <div v-if="form.photoPreview" class="mt-3">
        <img :src="form.photoPreview" alt="Превью изображения" class="img-fluid rounded shadow-sm"/>
      </div>
    </div>
    <!-- Кнопка -->
    <div class="mb-3" style="text-align: center">
      <button class="btn btn-success" style="width: 100%" :disabled="difficultyError" @click="submitForm">
        Create new Trip
      </button>
    </div>

  </div>
</template>

<script>
import store from "@/store/index.js";
import tripService from "@/services/trip.js";
import tripTypeService from "@/services/tripType.js";
import routeService from "@/services/route.js";
import app_header from "@/views/Header.vue"

export default {
  name: "TripForm",
  components:{
    app_header
  },
  data() {
    return {
      form: {
        type: {
          id: 0,
          name: "",
        },
        name: "",
        description: "",
        distance: null,
        duration: null,
        difficultyMin: 0,
        difficultyMax: 6,
        route: {
          id: 0,
          name: "",
        },
        photoFile: null,
        photoPreview: null,
      },
      types: ['Water Trip', 'Hiking Trip', 'Cycling Trip', 'Ski Trip', 'Mountain Hike', 'Others'],
      routes: [
        {id: 1, name: "Маршрут А"},
        {id: 2, name: "Маршрут Б"},
        {id: 3, name: "Маршрут В"},
        {id: 4, name: "Дальний путь"},
      ],
      searchQuery: "",
      difficultyError: false,
    };
  },
  computed: {
    filteredRoutes() {
      return this.routes.filter((r) =>
          r.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  mounted() {
    this.fetchTripTypes()
    this.fetchRoutes()
    this.fetchTripData()
  },
  methods: {
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.form.photoFile = file;
        this.form.photoPreview = URL.createObjectURL(file);
      }
    },
    validateDifficulty() {
      this.difficultyError = this.form.difficultyMin > this.form.difficultyMax;
    },
    submitForm() {
      if (this.difficultyError) return;
      try {
        console.log("tripService.create(this.form)")
        tripService.create(this.form)
        this.$router.push("/trips");
      } catch (e) {
        alert("An error occurred when creating the route")
        console.error(e)
        return
      }
    },
    createRoute() {
      store.commit('setTripData', {
        type: this.form.type,
        name: this.form.name,
        description: this.form.description,
        distance: this.form.distance,
        duration: this.form.duration,
        difficultyMin: this.form.difficultyMin,
        difficultyMax: this.form.difficultyMax,
        photoFile: this.form.photoFile,
        photoPreview: this.form.photoPreview,
        route: this.form.route
      });
      console.log(this.form)
      console.log(store.getters.tripData)
      this.$router.push({
        path: '/routes/create',
        query: {
          operation: "create",
        }
      });
    },
    async fetchTripTypes() {
      const tripTypes = await tripTypeService.getAll()
      this.types = tripTypes.map(tt => {
        return {name: tt.name, id: tt.id}
      })
    },
    async fetchRoutes() {
      const routes = await routeService.getAll()
      this.routes = routes.map(r => {
        return {name: r.name, id: r.id}
      })
      console.log(routes)
      console.log(this.routes)
    },
    async fetchTripData() {
      const data = store.getters.tripData
      console.log(data)
      this.form.type = data.type
      this.form.name = data.name
      this.form.description = data.description
      this.form.distance = data.distance
      this.form.duration = data.duration
      this.form.difficultyMin = data.difficultyMin
      this.form.difficultyMax = data.difficultyMax
      this.form.photoFile = data.photoFile
      this.form.photoPreview = data.photoPreview
      this.form.route = data.route
    }
  },
};
</script>

<style scoped>
.container {
  max-width: 700px;
}
</style>
