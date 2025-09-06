<template>
 <div class="container mt-5">
  <h1>FinTransfer Frontend</h1>
  <button class="btn btn-primary" @click="checkHealth">Check Backend</button>
  <p v-if="healthMessage" class="mt-3">{{ healthMessage }}</p>
 </div>
</template>

<script>
import { api } from "../services/api";

export default {
 data() {
  return {
   healthMessage: "",
  };
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
</style>
