<template>
 <div class="container mt-5">
  <h1>FinTransfer Frontend</h1>
  <button class="btn btn-primary" @click="checkHealth">Check Backend</button>
  <p v-if="healthMessage" class="mt-3">{{ healthMessage }}</p>
  <ul class="nav nav-tabs mt-4" role="tablist">
   <li class="nav-item" role="presentation">
    <button
     class="nav-link"
     :class="{ active: activeTab === 'form' }"
     @click="activeTab = 'form'"
     type="button"
     role="tab"
    >
     Schedule Transfer
    </button>
   </li>
   <li class="nav-item" role="presentation">
    <button
     class="nav-link"
     :class="{ active: activeTab === 'list' }"
     @click="activeTab = 'list'"
     type="button"
     role="tab"
    >
     List Transfers
    </button>
   </li>
  </ul>
  <div class="tab-content mt-3">
   <div
    v-show="activeTab === 'form'"
    class="tab-pane fade show active"
    role="tabpanel"
   >
    <TransferForm @transfer-scheduled="onTransferScheduled" />
   </div>
   <div
    v-show="activeTab === 'list'"
    class="tab-pane fade show active"
    role="tabpanel"
   >
    <TransferList ref="transferList" />
   </div>
  </div>
 </div>
</template>

<script>
import { api } from "../services/api";
import TransferForm from "../components/TransferForm.vue";
import TransferList from "../components/TransferList.vue";

export default {
 data() {
  return {
   healthMessage: "",
   activeTab: "form",
  };
 },
 components: {
  TransferForm,
  TransferList,
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
  onTransferScheduled() {
   // Switch to list tab and refresh
   this.activeTab = "list";
   this.$refs.transferList.refresh();
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
