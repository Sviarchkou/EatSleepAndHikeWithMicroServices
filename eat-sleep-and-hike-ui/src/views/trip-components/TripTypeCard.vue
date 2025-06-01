<template>
  <div class="col">
    <div class="card h-100 cursor-pointer" @click="handleClick">
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
      </div>
      <img
          :src="image"
          class="card-img-top"
          :alt="title"
          style="height: 240px; object-fit: cover;"
      />
    </div>
  </div>
<!--  <div class="col">
    <div class="card">
      <div class="card-body" >
        <h5 class="card-title">Card title</h5>
      </div>
      <img src="..." class="card-img-top" alt="..." style="height: 240px">
    </div>
  </div>-->
</template>

<script>
export default {
  name: 'TripTypeCard',
  props: {
    id: {
      type: [String, Number],
      required: true
    },
    title: {
      type: String,
      required: true
    },
    image: {
      type: String,
      required: true
    }
  },
  emits: ['select'],
  methods: {
    handleClick() {
      this.$emit('select', this.id)
    }
  }
}
</script>

<style scoped>
.cursor-pointer {
  cursor: pointer;
}
</style>
