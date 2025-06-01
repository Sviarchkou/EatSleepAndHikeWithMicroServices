<template>
  <div class="vh-100" style="background-color: #d9ffd3;">
    <div class="container d-flex align-items-center justify-content-center" style="min-height: 100vh;">
      <div class="card w-100 text-center border-light"  style="max-width: 480px;">
        <div class="card-body">
          <h3 class="card-title text-center mb-4">Register</h3>
          <form @submit.prevent="checkEmail">
            <div class="d-flex flex-row align-items-center mb-4">
              <input class="form-control" v-model="username" placeholder="Username" />
            </div>

            <div class="d-flex flex-row align-items-center mb-4">
              <input class="form-control" v-model="email" placeholder="Email" />
            </div>

            <div class="d-flex flex-row align-items-center mb-4">
              <input class="form-control" v-model="password" type="password" placeholder="Password" />
            </div>

<!--            <div class="d-flex flex-row align-items-center mb-4">
              <div data-mdb-input-init class="form-outline flex-fill mb-0">
                <input type="password" id="form3Example4cd" class="form-control" placeholder="Repeat your password"/>
              </div>
            </div>-->
            <div class="md-3" >
              <button class="btn btn-outline-success" style="width: 200px; margin-top: 5px" type="submit">Next</button>
            </div>
            <div class="mb-lg-3" style="margin-top: 20px">
              <label class="text-danger" v-if="error">{{ error }}</label>
            </div>
            <div class="text-center" style="margin-top: 40px">
              <p>Have already an account? <a href="login">Login here</a></p>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import regService from '@/services/reg.js';
import router from '@/router/index.js'
import store from '@/store/index.js'

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
      error: null,
    };
  },
  methods: {
    async checkEmail() {
      try {
        await regService.emailCheck(this.username, this.email, this.password);
        store.commit('setRegistrationData', {
          username: this.username,
          email: this.email,
          password: this.password
        });
        await router.push('/email-check');
      } catch (e) {
        this.error = 'Login failed ' + e;
      }
    },
  }
};
</script>