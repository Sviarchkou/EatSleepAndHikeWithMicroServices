<template>
  <div class="vh-100" style="background-color: #d9ffd3;">
    <div class="container d-flex align-items-center justify-content-center" style="min-height: 100vh;">
      <div class="card w-100 text-center border-light"  style="max-width: 480px;">
        <div class="card-body">
          <h3 class="card-title text-center mb-4">Register</h3>
          <form @submit.prevent="register">
            <div class="d-flex flex-row align-items-center mb-4">
              <input class="form-control" v-model="confirmNum" placeholder="Your code from email" />
            </div>
            <div class="md-3" >
              <button class="btn btn-outline-success" style="width: 200px; margin-top: 5px" type="submit">Next</button>
            </div>
            <div class="mb-lg-3" style="margin-top: 20px">
              <label class="text-danger" v-if="error">{{ error }}</label>
            </div>
            <div class="text-center" style="margin-top: 40px">
              <a href="register">Back to register page</a>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import regService from '@/services/reg.js';

export default {
  data() {
    return {
      confirmNum: '',
      error: null,
    };
  },
  methods: {
    async register() {
      try {
        const { username, email, password } = this.$store.getters.registrationData;
        await regService.register(username, email, password, this.confirmNum);
        this.$router.push('/login');
        alert("You Are Registered!")
      } catch (e) {
        this.error = 'Login failed';
      }
    },
  },
};
</script>