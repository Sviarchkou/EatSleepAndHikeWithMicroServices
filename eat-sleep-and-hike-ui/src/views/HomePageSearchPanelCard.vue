<template>
  <div class="panel-container">
    <div class="panel-overlay">
      <div class="panel-content">
        <h1 class="text-white">Explore all Trips?</h1>
        <div class="button-spacer"></div> <!-- Добавляем элемент для увеличения зазора -->
        <button class="btn btn-light btn-lg" @click="redirectToTrips">Search for all Trips</button>
      </div>
    </div>
    <div class="panel-image">
      <!-- Здесь будет изображение -->
      <img src="D:\Java\pics\ANPH-0528.jpg" alt="Trip Image" class="img-fluid" />
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    redirectToTrips() {
      // Переадресация на страницу с поездками (например, /trips)
      this.$router.push('/trips');
    },
  },
};
</script>

<style scoped>
.panel-container {
  position: relative;
  width: 100%;
  height: 400px; /* Высота панели */
  overflow: hidden;
}

.panel-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(to bottom right, #6c757d, rgba(0, 0, 0, 0));
  clip-path: polygon(0 0, 0 20%, 100% 80%, 100% 0);
  display: flex;
  align-items: flex-start; /* Сдвигаем содержимое к верхней части */
  justify-content: center;
  padding-top: 20px; /* Добавляем отступ сверху */
  z-index: 1;
}

.panel-content {
  text-align: center;
  z-index: 2;
}

.button-spacer {
  height: 20px; /* Увеличиваем зазор между текстом и кнопкой */
}

.panel-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.panel-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center center;
  filter: brightness(0.7);
}
</style>