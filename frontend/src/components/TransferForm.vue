<template>
 <form @submit.prevent="submitForm" class="transfer-form">
  <div>
   <label for="originAccount">Origin Account</label>
   <input
    v-model="form.originAccount"
    id="originAccount"
    maxlength="10"
    required
   />
  </div>
  <div>
   <label for="destinationAccount">Destination Account</label>
   <input
    v-model="form.destinationAccount"
    id="destinationAccount"
    maxlength="10"
    required
   />
  </div>
  <div>
   <label for="amount">Amount</label>
   <input
    v-model.number="form.amount"
    id="amount"
    type="number"
    min="0.01"
    step="0.01"
    required
   />
  </div>
  <div>
   <label for="transferDate">Transfer Date</label>
   <input
    v-model="form.transferDate"
    id="transferDate"
    type="date"
    :min="minDate"
    required
   />
  </div>
  <button type="submit">Schedule Transfer</button>
  <div v-if="message" :class="{ success: success, error: !success }">
   {{ message }}
  </div>
 </form>
</template>

<script>
import { api } from "../services/api";
export default {
 name: "TransferForm",
 data() {
  return {
   form: {
    originAccount: "",
    destinationAccount: "",
    amount: "",
    transferDate: "",
   },
   message: "",
   success: false,
  };
 },
 computed: {
  minDate() {
   return new Date().toISOString().split("T")[0];
  },
 },
 methods: {
  async submitForm() {
   // Basic validation
   if (
    !this.form.originAccount ||
    !this.form.destinationAccount ||
    !this.form.amount ||
    !this.form.transferDate
   ) {
    this.message = "All fields are required.";
    this.success = false;
    return;
   }
   if (new Date(this.form.transferDate) < new Date(this.minDate)) {
    this.message = "Transfer date must be in the future.";
    this.success = false;
    return;
   }
   try {
    const payload = {
     originAccount: this.form.originAccount,
     destinationAccount: this.form.destinationAccount,
     transferAmount: this.form.amount,
     transferDate: this.form.transferDate,
     scheduleDate: new Date().toISOString().split("T")[0],
     status: "PENDING",
     fee: 0,
    };
    const response = await api.post("/transfers", payload);
    this.message = "Transfer scheduled successfully!";
    this.success = true;
    this.form = {
     originAccount: "",
     destinationAccount: "",
     amount: "",
     transferDate: "",
    };
   } catch (error) {
    this.message = error.response?.data || "Failed to schedule transfer.";
    this.success = false;
   }
  },
 },
};
</script>

<style scoped>
.transfer-form {
 max-width: 400px;
 margin: 2rem auto;
 padding: 2rem;
 border: 1px solid #ccc;
 border-radius: 8px;
 background: #fff;
}
.transfer-form div {
 margin-bottom: 1rem;
}
.transfer-form label {
 display: block;
 margin-bottom: 0.5rem;
}
.transfer-form input {
 width: 100%;
 padding: 0.5rem;
 box-sizing: border-box;
}
button {
 padding: 0.5rem 1rem;
 background: #42b983;
 color: #fff;
 border: none;
 border-radius: 4px;
 cursor: pointer;
}
.success {
 color: #42b983;
 margin-top: 1rem;
}
.error {
 color: #d32f2f;
 margin-top: 1rem;
}
</style>
