<script setup>

import authService from "@/services/auth.js";
</script>

<template>
  <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom" style="background-color: z;">
    <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
      <svg class="bi me-2" width="40" height="32"><use href="#home"/></svg>
      <span class="fs-4">Eat, Sleep & Hike </span>
    </a>

    <ul class="nav nav-pills">
      <li class="nav-item" style="width: 120px; text-align: center; margin-right: 15px">
        <a href="/" class="nav-link active" style="background-color: #164423; color: white;" aria-current="page">Home</a>
      </li>
      <li class="nav-item" v-if="authService.isAdmin()">
        <a href="/trips/create" class="nav-link" style="color: #164423">Create new Trip</a>
      </li>
<!--      <li class="nav-item"><a href="#" class="nav-link">Цены</a></li>
      <li class="nav-item"><a href="#" class="nav-link">FAQ</a></li>-->
      <li class="nav-item" @click="authService.logout() "style="margin-right: 10px">
        <a href="/login"  class="nav-link" style="color: #164423">Log out</a>
      </li>
    </ul>
  </header>
</template>

<style scoped>

</style>