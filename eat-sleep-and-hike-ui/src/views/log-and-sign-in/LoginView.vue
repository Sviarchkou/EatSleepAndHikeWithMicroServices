<template>
  <div class="vh-100" style="background-color: #d9ffd3;">
    <div class="container d-flex align-items-center justify-content-center" style="min-height: 100vh;">
      <div class="card w-100 text-center border-light"  style="max-width: 480px;">
        <div class="card-body">
          <h3 class="card-title text-center mb-4">Login</h3>
          <form @submit.prevent="login">
            <div class="mb-3">
              <input class="form-control" v-model="usernameOrEmail" placeholder="Username or Email" />
            </div>
            <div class="mb-3" >
              <input class="form-control" v-model="password" type="password" placeholder="Password" /></div>

            <div class="md-3" >
              <button class="btn btn-outline-success" style="width: 200px; margin-top: 5px" type="submit">Login</button>
            </div>
            <div class="mb-lg-3" style="margin-top: 5px">
              <label class="text-danger" v-if="error">{{ error }}</label>
            </div>

            <!-- Register buttons -->
            <div class="text-center" style="margin-top: 40px">
              <p>Not a member? <a href="register">Register</a></p>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import authService from '@/services/auth.js';

export default {
  data() {
    return {
      usernameOrEmail: '',
      password: '',
      error: null,
    };
  },
  methods: {
    async login() {
      try {
        await authService.login(this.usernameOrEmail, this.password);
        this.$router.push('/');
      } catch (e) {
        this.error = 'Login failed';
      }
    },
  },
};
</script>