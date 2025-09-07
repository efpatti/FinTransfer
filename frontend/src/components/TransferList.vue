<template>
 <div class="transfer-list">
  <h2>Scheduled Transfers</h2>
  <table v-if="transfers.length" class="table table-striped">
   <thead>
    <tr>
     <th>Origin</th>
     <th>Destination</th>
     <th>Amount</th>
     <th>Transfer Date</th>
     <th>Schedule Date</th>
     <th>Status</th>
     <th>Fee</th>
    </tr>
   </thead>
   <tbody>
    <tr v-for="transfer in transfers" :key="transfer.id">
     <td>{{ transfer.originAccount }}</td>
     <td>{{ transfer.destinationAccount }}</td>
     <td>{{ transfer.transferAmount }}</td>
     <td>{{ transfer.transferDate }}</td>
     <td>{{ transfer.scheduleDate }}</td>
     <td>{{ transfer.status }}</td>
     <td>{{ transfer.fee }}</td>
    </tr>
   </tbody>
  </table>
  <div v-else class="no-transfers">No transfers scheduled.</div>
 </div>
</template>

<script>
import { api } from "../services/api";
export default {
 name: "TransferList",
 data() {
  return {
   transfers: [],
  };
 },
 mounted() {
  this.fetchTransfers();
 },
 methods: {
  async fetchTransfers() {
   try {
    const response = await api.get("/transfers");
    this.transfers = response.data;
   } catch (error) {
    this.transfers = [];
   }
  },
  refresh() {
   this.fetchTransfers();
  },
 },
};
</script>

<style scoped>
.transfer-list {
 max-width: 800px;
 margin: 2rem auto;
 padding: 1rem;
 background: #fff;
 border-radius: 8px;
 box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}
.table {
 width: 100%;
 border-collapse: collapse;
}
th,
td {
 padding: 0.75rem;
 text-align: left;
}
.no-transfers {
 text-align: center;
 color: #888;
 margin-top: 2rem;
}
@media (max-width: 600px) {
 .transfer-list {
  padding: 0.5rem;
 }
 th,
 td {
  padding: 0.5rem;
  font-size: 0.95rem;
 }
}
</style>
