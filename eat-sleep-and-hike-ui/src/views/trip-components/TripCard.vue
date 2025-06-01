<template>
  <div class="trip-card-container" @click="handleClick">
    <div class="trip-card-image" :style="{ backgroundImage: `url(${imageSrc})` }">
      <!-- Пустая область для изображения -->
    </div>
    <div class="trip-card-content">
      <h1 class="trip-title">{{ tripName }}</h1>
      <p><strong>Trip Type:</strong> {{ tripType }}</p>
      <p><strong>Duration:</strong> {{ duration }} days</p>
      <p><strong>Distance:</strong> {{ distance }} km</p>
      <p><strong>Min Difficulty category:</strong> {{ minDifficulty }}</p>
      <p><strong>Max Difficulty category:</strong> {{ maxDifficulty }}</p>
      <p><strong>Route Description:</strong> {{ routeDescription }}</p>
      <p><strong >Countries:</strong></p>
      <div class="form-check" style="margin-top: 5px" v-for="country in countries" :key="country">
        <p>{{ country.name }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TripCard',
  props: {
    id: { type: String, required: true },
    tripName: { type: String, required: true },
    tripType: { type: String, default: 'X' },
    duration: { type: Number, default: 0 },
    distance: { type: Number, default: 0 },
    minDifficulty: { type: String, default: "0" },
    maxDifficulty: { type: String, default: "6" },
    routeDescription: { type: String, default: 'desс...' },
    countries: { type: Array, default: ['Country1', 'Country2']},
    imageSrc: { type: String, default: '' },
  },
  methods: {
    handleClick() {
      this.$router.push(`/trips/${this.$props.id}`)
    }
  }
};


</script>

<style scoped>
.trip-card-container {
  display: flex;
  width: 100%;
  min-height: 360px;
  border: 1px solid #ccc;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.trip-card-image {
  flex: 1;
  min-width: 55%;
  background-size: cover;
  background-position: center;
  background-color: #6c757d;
}

.trip-card-content {
  flex: 2;
  padding: 15px;
  background-color: #fff;
}

.trip-title {
  font-size: 1.7rem;
  margin-bottom: 10px;
}

p {
  margin: 5px 0;
  line-height: 1.4;
}

p strong {
  color: #333;
}

@media (max-width: 768px) {
  .trip-card-container {
    flex-direction: column;
  }
  .trip-card-image {
    min-height: 150px;
  }
}
</style>