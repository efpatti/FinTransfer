<template>
 <div class="container mt-5">
  <h1>FinTransfer Frontend</h1>
  <button class="btn btn-primary" @click="checkHealth">Check Backend</button>
  <p v-if="healthMessage" class="mt-3">{{ healthMessage }}</p>
  <div class="home">
   <h1>Schedule a Transfer</h1>
   <TransferForm />
  </div>
 </div>
</template>

<script>
import { api } from "../services/api";
import TransferForm from "../components/TransferForm.vue";

export default {
 data() {
  return {
   healthMessage: "",
  };
 },
 components: {
  TransferForm,
 },
 methods: {
  async checkHealth() {
   try {
    const response = await api.get("/health");
    this.healthMessage = response.data;
   } catch (error) {
    this.healthMessage = "Backend not reachable";
   }
  },
 },
};
</script>

<style>
.container {
 text-align: center;
}
.home {
 max-width: 600px;
 margin: 2rem auto;
 padding: 2rem;
 background: #f9f9f9;
 border-radius: 8px;
}
h1 {
 text-align: center;
 margin-bottom: 2rem;
}
</style>
