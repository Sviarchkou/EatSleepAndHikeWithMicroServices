import { createRouter, createWebHistory } from 'vue-router';
import LoginView from '../views/log-and-sign-in/LoginView.vue';
import TripView from '../views/TripsView.vue';
import authService from '../services/auth';
import Registration from "@/views/log-and-sign-in/Registration.vue";
import EmailCheck from "@/views/log-and-sign-in/EmailCheck.vue";
import HomePage from "@/views/HomePage.vue";
import TripFullInfo from "@/views/trip-components/TripFullInfo.vue";
import TripCreationForm from "@/views/trip-components/TripCreationForm.vue";
import Test from "@/views/Test.vue";
import RouteCreationForm from "@/views/trip-components/RouteCreationForm.vue";
import UpdateTripForm from "@/views/trip-components/UpdateTripForm.vue";
import RouteUpdateForm from "@/views/trip-components/RouteUpdateForm.vue";

const routes = [
    { path: '/login', component: LoginView },
    { path: '/register', component: Registration },
    { path: '/email-check', component: EmailCheck },
    {
        path: '/trips',
        component: TripView,
        meta: { requiresAuth: true },
    },
    {
        path: '/',
        component: HomePage,
        meta: { requiresAuth: true },
    },
    { path: '/home', redirect: '/' },
    {
        path: '/trips/:id',
        component: TripFullInfo,
        meta: { requiresAuth: true }
    },
    {
        path: '/trips/create',
        name: 'TripCreation',
        component: TripCreationForm,
        meta: { requiresAuth: true }
    },
    {
        path: '/trips/update/:id',
        name: 'TripUpdate',
        component: UpdateTripForm,
        meta: { requiresAuth: true }
    },
    {
        path: '/routes/create',
        component: RouteCreationForm,
        meta: { requiresAuth: true }
    },
    {
        path: '/routes/update/:id',
        component: RouteUpdateForm,
        meta: { requiresAuth: true }
    },
    {
        path: '/test',
        component: TripFullInfo
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Защита маршрутов
router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth && !authService.isAuthenticated()) {
        next('/login');
    } else {
        next();
    }
});

export default router;
