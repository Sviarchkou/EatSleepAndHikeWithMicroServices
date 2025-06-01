<template>
  <div class="col-6 col-md-4">
    <div class="card h-100" @click="handleClick" style="cursor: pointer;">
      <img
          :src="image"
          :alt="title"
          class="card-img-top"
          style="height: 200px; object-fit: cover;"
      />
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  id:    { type: Number, required: true },
  title: { type: String, required: true },
  image: { type: String, required: true },
})

const router = useRouter()

const emit = defineEmits(['select'])
function handleClick() {
  emit('select', props.id)
}
</script>
