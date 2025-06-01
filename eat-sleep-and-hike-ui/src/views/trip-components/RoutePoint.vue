<template>
  <div class="route-point">
    <h3>Point {{ pointNumber }}</h3>
    <hr class="divider" />
    <p><strong>Latitude:</strong> {{ latitude }}</p>
    <p><strong>Longitude:</strong> {{ longitude }}</p>
    <p><strong>Description:</strong> {{ description }}</p>
  </div>
</template>

<script>
export default {
  name: 'RoutePoint',
  props: {
    pointNumber: {
      type: Number,
      required: true
    },
    latitude: {
      type: String,
      required: true
    },
    longitude: {
      type: String,
      required: true
    },
    description: {
      type: String,
      required: true
    }
  }
};
</script>

<style scoped>
.route-point {
  margin-bottom: 20px;
}

.route-point h3 {
  font-size: 1.2rem;
  margin-bottom: 10px;
}

.divider {
  border: 0;
  border-top: 1px dashed #999;
  margin: 10px 0;
}
</style>