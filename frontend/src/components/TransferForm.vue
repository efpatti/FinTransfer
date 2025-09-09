<template>
 <div
  class="flex flex-col items-center justify-center min-h-screen bg-gray-900 py-10 px-4 fade-in"
 >
  <form
   @submit.prevent="submitForm"
   class="w-full max-w-xl bg-gray-800 rounded-2xl shadow-2xl p-8 border border-gray-700"
  >
   <!-- Header com ícone e título -->
   <div class="flex items-center justify-center mb-10">
    <div
     class="flex items-center gap-4 px-6 py-4 rounded-2xl bg-gradient-to-r from-purple-600 to-purple-500 shadow-lg"
    >
     <svg
      width="32"
      height="32"
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
     <span class="text-white text-2xl font-bold">Agendar Transferência</span>
    </div>
   </div>
   <!-- Progress indicator -->
   <div class="mb-8">
    <div class="flex items-center justify-between mb-2">
     <span class="text-sm font-medium text-purple-400">Etapa 1 de 1</span>
     <span class="text-sm text-gray-400">Dados da transferência</span>
    </div>
    <div class="w-full bg-gray-700 rounded-full h-2">
     <div
      class="bg-gradient-to-r from-purple-500 to-purple-400 h-2 rounded-full w-full transition-all duration-300"
     ></div>
    </div>
   </div>
   <div class="space-y-6">
    <!-- Conta de Origem -->
    <div class="form-group">
     <label
      for="originAccount"
      class="block text-sm font-semibold mb-3 text-gray-200"
     >
      Conta de Origem
      <span class="text-red-400 ml-1">*</span>
     </label>
     <div class="relative">
      <input
       v-model="form.originAccount"
       id="originAccount"
       maxlength="10"
       required
       class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
       placeholder="Digite o número da conta"
       @blur="validateOriginAccount"
       :class="{ 'border-red-500 focus:border-red-500': errors.originAccount }"
      />
      <div
       v-if="errors.originAccount"
       class="mt-2 text-sm text-red-400 flex items-center gap-2"
      >
       <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
        <path
         d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
        />
       </svg>
       {{ errors.originAccount }}
      </div>
     </div>
    </div>
    <!-- Conta de Destino -->
    <div class="form-group">
     <label
      for="destinationAccount"
      class="block text-sm font-semibold mb-3 text-gray-200"
     >
      Conta de Destino
      <span class="text-red-400 ml-1">*</span>
     </label>
     <div class="relative">
      <input
       v-model="form.destinationAccount"
       id="destinationAccount"
       maxlength="10"
       required
       class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
       placeholder="Digite o número da conta destino"
       @blur="validateDestinationAccount"
       :class="{
        'border-red-500 focus:border-red-500': errors.destinationAccount,
       }"
      />
      <div
       v-if="errors.destinationAccount"
       class="mt-2 text-sm text-red-400 flex items-center gap-2"
      >
       <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
        <path
         d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
        />
       </svg>
       {{ errors.destinationAccount }}
      </div>
     </div>
    </div>
    <!-- Valor -->
    <div class="form-group">
     <label for="amount" class="block text-sm font-semibold mb-3 text-gray-200">
      Valor da Transferência
      <span class="text-red-400 ml-1">*</span>
     </label>
     <div class="relative">
      <span
       class="absolute left-4 top-1/2 transform -translate-y-1/2 text-purple-400 font-bold text-lg"
       >R$</span
      >
      <input
       v-model.number="form.amount"
       id="amount"
       type="number"
       min="0.01"
       step="0.01"
       required
       class="w-full pl-12 pr-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
       placeholder="0,00"
       @blur="validateAmount"
       :class="{ 'border-red-500 focus:border-red-500': errors.amount }"
      />
      <div
       v-if="errors.amount"
       class="mt-2 text-sm text-red-400 flex items-center gap-2"
      >
       <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
        <path
         d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
        />
       </svg>
       {{ errors.amount }}
      </div>
     </div>
    </div>
    <!-- Data da Transferência -->
    <div class="form-group">
     <label
      for="transferDate"
      class="block text-sm font-semibold mb-3 text-gray-200"
     >
      Data da Transferência
      <span class="text-red-400 ml-1">*</span>
     </label>
     <div class="relative">
      <input
       v-model="form.transferDate"
       id="transferDate"
       type="date"
       :min="minDate"
       required
       class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
       @change="validateTransferDate"
       :class="{ 'border-red-500 focus:border-red-500': errors.transferDate }"
      />
      <div
       v-if="errors.transferDate"
       class="mt-2 text-sm text-red-400 flex items-center gap-2"
      >
       <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24">
        <path
         d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
        />
       </svg>
       {{ errors.transferDate }}
      </div>
     </div>
    </div>
   </div>
   <!-- Resumo da transferência -->
   <div
    v-if="form.amount && form.transferDate"
    class="mt-8 p-6 bg-gray-700/50 rounded-xl border border-gray-600"
   >
    <h3
     class="text-lg font-semibold text-purple-400 mb-4 flex items-center gap-2"
    >
     <svg
      width="20"
      height="20"
      fill="none"
      stroke="currentColor"
      stroke-width="2"
      viewBox="0 0 24 24"
     >
      <path d="M9 12l2 2 4-4" />
     </svg>
     Resumo da Transferência
    </h3>
    <div class="space-y-2 text-sm">
     <div class="flex justify-between">
      <span class="text-gray-400">Valor:</span>
      <span class="text-white font-semibold"
       >R$ {{ formatCurrency(form.amount) }}</span
      >
     </div>
     <div class="flex justify-between">
      <span class="text-gray-400">Data:</span>
      <span class="text-white">{{ formatDate(form.transferDate) }}</span>
     </div>
     <div class="flex justify-between">
      <span class="text-gray-400">Taxa:</span>
      <span class="text-green-400 font-semibold">Grátis</span>
     </div>
    </div>
   </div>
   <!-- Botão de Submit -->
   <button
    type="submit"
    :disabled="isSubmitting || !isFormValid"
    class="w-full py-4 mt-8 bg-gradient-to-r from-purple-600 to-purple-500 hover:from-purple-500 hover:to-purple-400 disabled:from-gray-600 disabled:to-gray-500 text-white font-bold text-xl rounded-xl shadow-lg transition-all duration-300 transform hover:scale-[1.02] disabled:scale-100 disabled:cursor-not-allowed flex items-center justify-center gap-3"
   >
    <svg
     v-if="isSubmitting"
     class="animate-spin h-6 w-6"
     fill="none"
     viewBox="0 0 24 24"
    >
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
    <svg
     v-else
     width="24"
     height="24"
     fill="none"
     stroke="currentColor"
     stroke-width="2"
     viewBox="0 0 24 24"
    >
     <path d="M5 12h14M12 5l7 7-7 7" />
    </svg>
    {{ isSubmitting ? "Agendando..." : "Agendar Transferência" }}
   </button>
   <!-- Mensagens de feedback -->
   <div
    v-if="message"
    :class="[
     'mt-6 p-4 rounded-xl border-2 font-semibold text-center transition-all duration-300',
     success
      ? 'bg-green-900/30 border-green-500 text-green-400'
      : 'bg-red-900/30 border-red-500 text-red-400',
    ]"
   >
    <div class="flex items-center justify-center gap-3">
     <svg
      v-if="success"
      width="20"
      height="20"
      fill="currentColor"
      viewBox="0 0 24 24"
     >
      <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z" />
     </svg>
     <svg v-else width="20" height="20" fill="currentColor" viewBox="0 0 24 24">
      <path
       d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-2h2v2zm0-4h-2V7h2v6z"
      />
     </svg>
     {{ message }}
    </div>
   </div>
  </form>
 </div>
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
   errors: {
    originAccount: "",
    destinationAccount: "",
    amount: "",
    transferDate: "",
   },
   message: "",
   success: false,
   isSubmitting: false,
  };
 },
 computed: {
  minDate() {
   return new Date().toISOString().split("T")[0];
  },
  isFormValid() {
   return (
    this.form.originAccount &&
    this.form.destinationAccount &&
    this.form.amount &&
    this.form.transferDate &&
    !Object.values(this.errors).some((error) => error)
   );
  },
 },
 methods: {
  validateOriginAccount() {
   if (!this.form.originAccount) {
    this.errors.originAccount = "Conta de origem é obrigatória";
   } else if (this.form.originAccount.length < 3) {
    this.errors.originAccount = "Conta deve ter pelo menos 3 caracteres";
   } else {
    this.errors.originAccount = "";
   }
  },
  validateDestinationAccount() {
   if (!this.form.destinationAccount) {
    this.errors.destinationAccount = "Conta de destino é obrigatória";
   } else if (this.form.destinationAccount.length < 3) {
    this.errors.destinationAccount = "Conta deve ter pelo menos 3 caracteres";
   } else if (this.form.destinationAccount === this.form.originAccount) {
    this.errors.destinationAccount =
     "Conta de destino deve ser diferente da origem";
   } else {
    this.errors.destinationAccount = "";
   }
  },
  validateAmount() {
   if (!this.form.amount) {
    this.errors.amount = "Valor é obrigatório";
   } else if (this.form.amount <= 0) {
    this.errors.amount = "Valor deve ser maior que zero";
   } else if (this.form.amount > 1000000) {
    this.errors.amount = "Valor não pode exceder R$ 1.000.000,00";
   } else {
    this.errors.amount = "";
   }
  },
  validateTransferDate() {
   if (!this.form.transferDate) {
    this.errors.transferDate = "Data da transferência é obrigatória";
   } else if (new Date(this.form.transferDate) < new Date(this.minDate)) {
    this.errors.transferDate = "Data deve ser no futuro";
   } else {
    this.errors.transferDate = "";
   }
  },
  formatCurrency(value) {
   return new Intl.NumberFormat("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
   }).format(value);
  },
  formatDate(dateString) {
   if (!dateString) return "";
   return new Date(dateString).toLocaleDateString("pt-BR");
  },
  async submitForm() {
   // Validar todos os campos
   this.validateOriginAccount();
   this.validateDestinationAccount();
   this.validateAmount();
   this.validateTransferDate();

   if (!this.isFormValid) {
    this.message = "Por favor, corrija os erros antes de continuar.";
    this.success = false;
    return;
   }

   this.isSubmitting = true;
   this.message = "";

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

    await api.post("/transfers", payload);

    this.message = "Transferência agendada com sucesso!";
    this.success = true;
    // Reset form
    this.form = {
     originAccount: "",
     destinationAccount: "",
     amount: "",
     transferDate: "",
    };
    // Reset errors
    this.errors = {
     originAccount: "",
     destinationAccount: "",
     amount: "",
     transferDate: "",
    };
    this.$emit("transfer-scheduled");
   } catch (error) {
    this.message = error.response?.data || "Falha ao agendar transferência.";
    this.success = false;
   } finally {
    this.isSubmitting = false;
   }
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
.form-group {
 position: relative;
}
input:focus {
 transform: translateY(-1px);
}
button:hover:not(:disabled) {
 box-shadow: 0 10px 25px rgba(147, 51, 234, 0.3);
}
.border-red-500 {
 animation: shake 0.3s ease-in-out;
}
@keyframes shake {
 0%,
 100% {
  transform: translateX(0);
 }
 25% {
  transform: translateX(-4px);
 }
 75% {
  transform: translateX(4px);
 }
}
.success-message {
 background-color: #f0fdf4;
 border-left: 4px solid #10b981;
}

.error-message {
 background-color: #fef2f2;
 border-left: 4px solid #ef4444;
}

.fade-enter-active,
.fade-leave-active {
 transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
 opacity: 0;
}
</style>
