<template>
 <div
  class="flex flex-col items-center justify-center min-h-[60vh] py-10 px-4 fade-in"
 >
  <div
   class="w-full max-w-6xl bg-gray-800 rounded-2xl shadow-2xl border border-gray-700"
  >
   <!-- Header -->
   <div class="p-8 border-b border-gray-700">
    <div class="flex items-center justify-between">
     <div class="flex items-center gap-4">
      <div class="p-3 bg-purple-600 rounded-xl">
       <svg
        width="24"
        height="24"
        fill="none"
        stroke="currentColor"
        stroke-width="2"
        viewBox="0 0 24 24"
        class="text-white"
       >
        <path
         d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2-2z"
        />
        <path d="M8 21v-4a2 2 0 012-2h4a2 2 0 012 2v4" />
       </svg>
      </div>
      <div>
       <h2 class="text-2xl font-bold text-white">Transferências Agendadas</h2>
       <p class="text-gray-400 text-sm">
        {{ transfers.length }} transferência{{
         transfers.length !== 1 ? "s" : ""
        }}
        encontrada{{ transfers.length !== 1 ? "s" : "" }}
       </p>
      </div>
     </div>
     <button
      @click="refresh"
      :disabled="isLoading"
      class="flex items-center gap-2 px-4 py-2 bg-gray-700 hover:bg-gray-600 text-white rounded-lg transition-all duration-200 disabled:opacity-50"
     >
      <svg
       :class="{ 'animate-spin': isLoading }"
       width="18"
       height="18"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
      >
       <path d="M1 4v6h6M23 20v-6h-6" />
       <path
        d="M20.49 9A9 9 0 005.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 013.51 15"
       />
      </svg>
      Atualizar
     </button>
    </div>
   </div>
   <!-- Filters -->
   <div class="p-6 border-b border-gray-700 bg-gray-750">
    <div class="flex flex-wrap gap-4 items-center">
     <div class="flex items-center gap-2">
      <span class="text-sm font-medium text-gray-300">Filtrar por status:</span>
      <select
       v-model="filterStatus"
       @change="applyFilters"
       class="px-3 py-2 bg-gray-700 border border-gray-600 rounded-lg text-white text-sm focus:outline-none focus:border-purple-500"
      >
       <option value="">Todos</option>
       <option value="PENDING">Pendente</option>
       <option value="COMPLETED">Concluída</option>
       <option value="CANCELLED">Cancelada</option>
      </select>
     </div>
     <div class="flex items-center gap-2">
      <span class="text-sm font-medium text-gray-300">Ordenar por:</span>
      <select
       v-model="sortBy"
       @change="applyFilters"
       class="px-3 py-2 bg-gray-700 border border-gray-600 rounded-lg text-white text-sm focus:outline-none focus:border-purple-500"
      >
       <option value="transferDate">Data da Transferência</option>
       <option value="scheduleDate">Data de Agendamento</option>
       <option value="transferAmount">Valor</option>
      </select>
     </div>
    </div>
   </div>
   <!-- Loading State -->
   <div v-if="isLoading" class="p-8 text-center">
    <div class="inline-flex items-center gap-3 text-purple-400">
     <svg class="animate-spin h-6 w-6" fill="none" viewBox="0 0 24 24">
      <circle
       class="opacity-25"
       cx="12"
       cy="12"
       r="10"
       stroke="currentColor"
       stroke-width="4"
      ></circle>
      <path
       class="opacity-75"
       fill="currentColor"
       d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
      ></path>
     </svg>
     <span class="font-medium">Carregando transferências...</span>
    </div>
   </div>
   <!-- Transferências List -->
   <div v-else-if="filteredTransfers.length" class="overflow-hidden">
    <!-- Desktop Table View -->
    <div class="hidden lg:block overflow-x-auto">
     <table class="w-full">
      <thead class="bg-gray-750">
       <tr>
        <th
         class="px-6 py-4 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Origem
        </th>
        <th
         class="px-6 py-4 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Destino
        </th>
        <th
         class="px-6 py-4 text-right text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Valor
        </th>
        <th
         class="px-6 py-4 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Data Transferência
        </th>
        <th
         class="px-6 py-4 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Agendada em
        </th>
        <th
         class="px-6 py-4 text-left text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Status
        </th>
        <th
         class="px-6 py-4 text-right text-sm font-semibold text-gray-300 uppercase tracking-wider"
        >
         Taxa
        </th>
       </tr>
      </thead>
      <tbody class="divide-y divide-gray-700">
       <tr
        v-for="transfer in filteredTransfers"
        :key="transfer.id"
        class="hover:bg-gray-750 transition-colors duration-200 group"
       >
        <td class="px-6 py-4">
         <div class="font-semibold text-white">
          {{ transfer.originAccount }}
         </div>
        </td>
        <td class="px-6 py-4">
         <div class="font-semibold text-white">
          {{ transfer.destinationAccount }}
         </div>
        </td>
        <td class="px-6 py-4 text-right">
         <div class="font-bold text-lg text-purple-400">
          R$ {{ formatCurrency(transfer.transferAmount) }}
         </div>
        </td>
        <td class="px-6 py-4">
         <div class="text-white">{{ formatDate(transfer.transferDate) }}</div>
         <div class="text-sm text-gray-400">
          {{ formatRelativeDate(transfer.transferDate) }}
         </div>
        </td>
        <td class="px-6 py-4">
         <div class="text-white">{{ formatDate(transfer.scheduleDate) }}</div>
        </td>
        <td class="px-6 py-4">
         <span
          :class="getStatusClass(transfer.status)"
          class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium"
         >
          <span
           :class="getStatusDotClass(transfer.status)"
           class="w-2 h-2 rounded-full mr-2"
          ></span>
          {{ getStatusText(transfer.status) }}
         </span>
        </td>
        <td class="px-6 py-4 text-right">
         <span class="text-green-400 font-semibold">
          {{
           transfer.fee > 0 ? "R$ " + formatCurrency(transfer.fee) : "Grátis"
          }}
         </span>
        </td>
       </tr>
      </tbody>
     </table>
    </div>
    <!-- Mobile Card View -->
    <div class="lg:hidden space-y-4 p-4">
     <div
      v-for="transfer in filteredTransfers"
      :key="transfer.id"
      class="bg-gray-750 rounded-xl p-6 border border-gray-600 hover:border-purple-500 transition-all duration-200"
     >
      <div class="flex justify-between items-start mb-4">
       <div>
        <div class="text-lg font-bold text-purple-400">
         R$ {{ formatCurrency(transfer.transferAmount) }}
        </div>
        <div class="text-sm text-gray-400">
         {{ formatDate(transfer.transferDate) }}
        </div>
       </div>
       <span
        :class="getStatusClass(transfer.status)"
        class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium"
       >
        <span
         :class="getStatusDotClass(transfer.status)"
         class="w-2 h-2 rounded-full mr-2"
        ></span>
        {{ getStatusText(transfer.status) }}
       </span>
      </div>
      <div class="space-y-3">
       <div class="flex justify-between">
        <span class="text-gray-400 text-sm">De:</span>
        <span class="text-white font-medium">{{ transfer.originAccount }}</span>
       </div>
       <div class="flex justify-between">
        <span class="text-gray-400 text-sm">Para:</span>
        <span class="text-white font-medium">{{
         transfer.destinationAccount
        }}</span>
       </div>
       <div class="flex justify-between">
        <span class="text-gray-400 text-sm">Taxa:</span>
        <span class="text-green-400 font-semibold">{{
         transfer.fee > 0 ? "R$ " + formatCurrency(transfer.fee) : "Grátis"
        }}</span>
       </div>
       <div class="flex justify-between">
        <span class="text-gray-400 text-sm">Agendada em:</span>
        <span class="text-white">{{ formatDate(transfer.scheduleDate) }}</span>
       </div>
      </div>
     </div>
    </div>
   </div>
   <!-- Empty State -->
   <div v-else class="p-12 text-center">
    <div class="max-w-sm mx-auto">
     <div class="mb-6">
      <div
       class="w-20 h-20 mx-auto bg-gray-700 rounded-full flex items-center justify-center"
      >
       <svg
        width="32"
        height="32"
        fill="none"
        stroke="currentColor"
        stroke-width="1.5"
        viewBox="0 0 24 24"
        class="text-gray-400"
       >
        <path
         d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2-2z"
        />
        <path d="M8 21v-4a2 2 0 012-2h4a2 2 0 012 2v4" />
       </svg>
      </div>
     </div>
     <h3 class="text-xl font-semibold text-white mb-2">
      {{
       filterStatus
        ? "Nenhuma transferência encontrada"
        : "Nenhuma transferência agendada"
      }}
     </h3>
     <p class="text-gray-400 mb-6">
      {{
       filterStatus
        ? `Não há transferências com status "${getStatusText(filterStatus)}".`
        : "Agende sua primeira transferência usando o formulário."
      }}
     </p>
     <button
      v-if="filterStatus"
      @click="clearFilters"
      class="inline-flex items-center gap-2 px-4 py-2 bg-purple-600 hover:bg-purple-500 text-white rounded-lg transition-colors duration-200"
     >
      <svg
       width="16"
       height="16"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
      >
       <path d="M6 18L18 6M6 6l12 12" />
      </svg>
      Limpar Filtros
     </button>
    </div>
   </div>
  </div>
 </div>
</template>

<script>
import { api } from "../services/api";

export default {
 name: "TransferList",
 data() {
  return {
   transfers: [],
   filteredTransfers: [],
   isLoading: false,
   filterStatus: "",
   sortBy: "transferDate",
  };
 },
 mounted() {
  this.fetchTransfers();
 },
 methods: {
  async fetchTransfers() {
   this.isLoading = true;
   try {
    const response = await api.get("/transfers");
    this.transfers = response.data || [];
    this.applyFilters();
    this.$emit("transfers-loaded", this.transfers.length);
   } catch (error) {
    console.error("Erro ao carregar transferências:", error);
    this.transfers = [];
    this.filteredTransfers = [];
    this.$emit("transfers-loaded", 0);
   } finally {
    this.isLoading = false;
   }
  },
  refresh() {
   this.fetchTransfers();
  },
  applyFilters() {
   let filtered = [...this.transfers];
   // Aplicar filtro de status
   if (this.filterStatus) {
    filtered = filtered.filter(
     (transfer) => transfer.status === this.filterStatus
    );
   }
   // Aplicar ordenação
   filtered.sort((a, b) => {
    const aValue = a[this.sortBy];
    const bValue = b[this.sortBy];
    if (this.sortBy === "transferAmount") {
     return bValue - aValue; // Descendente para valores
    } else {
     return new Date(bValue) - new Date(aValue); // Descendente para datas
    }
   });
   this.filteredTransfers = filtered;
  },
  clearFilters() {
   this.filterStatus = "";
   this.sortBy = "transferDate";
   this.applyFilters();
  },
  formatCurrency(value) {
   return new Intl.NumberFormat("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
   }).format(value);
  },
  formatDate(dateString) {
   if (!dateString) return "-";
   return new Date(dateString).toLocaleDateString("pt-BR");
  },
  formatRelativeDate(dateString) {
   if (!dateString) return "";
   const date = new Date(dateString);
   const today = new Date();
   const diffTime = date - today;
   const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
   if (diffDays === 0) return "Hoje";
   if (diffDays === 1) return "Amanhã";
   if (diffDays === -1) return "Ontem";
   if (diffDays > 1) return `Em ${diffDays} dias`;
   if (diffDays < -1) return `${Math.abs(diffDays)} dias atrás`;
   return "";
  },
  getStatusText(status) {
   const statusMap = {
    PENDING: "Pendente",
    COMPLETED: "Concluída",
    CANCELLED: "Cancelada",
    PROCESSING: "Processando",
   };
   return statusMap[status] || status;
  },
  getStatusClass(status) {
   const classMap = {
    PENDING: "bg-yellow-900/30 text-yellow-400 border border-yellow-500/30",
    COMPLETED: "bg-green-900/30 text-green-400 border border-green-500/30",
    CANCELLED: "bg-red-900/30 text-red-400 border border-red-500/30",
    PROCESSING: "bg-blue-900/30 text-blue-400 border border-blue-500/30",
   };
   return (
    classMap[status] || "bg-gray-900/30 text-gray-400 border border-gray-500/30"
   );
  },
  getStatusDotClass(status) {
   const classMap = {
    PENDING: "bg-yellow-400",
    COMPLETED: "bg-green-400",
    CANCELLED: "bg-red-400",
    PROCESSING: "bg-blue-400",
   };
   return classMap[status] || "bg-gray-400";
  },
 },
};
</script>

<style scoped>
.fade-in {
 animation: fadeIn 0.6s ease-out forwards;
}
@keyframes fadeIn {
 from {
  opacity: 0;
  transform: translateY(20px);
 }
 to {
  opacity: 1;
  transform: translateY(0);
 }
}
.bg-gray-750 {
 background-color: #2d3748;
}
select {
 transition: border-color 0.2s ease;
}
select:focus {
 box-shadow: 0 0 0 3px rgba(147, 51, 234, 0.1);
}
@keyframes spin {
 from {
  transform: rotate(0deg);
 }
 to {
  transform: rotate(360deg);
 }
}
@media (max-width: 1024px) {
 .hidden.lg\:block {
  display: none !important;
 }
 .lg\:hidden {
  display: block !important;
 }
}
@media (max-width: 1023px) {
 .bg-gray-750:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.3);
 }
}
</style>
