<template>
 <div class="min-h-screen bg-gray-900">
  <!-- Background decorativo -->
  <div
   class="absolute inset-0 bg-gradient-to-br from-purple-900/20 via-gray-900 to-gray-900 pointer-events-none"
  ></div>
  <div class="relative max-w-6xl mx-auto px-4 py-8">
   <!-- Header Principal -->
   <div class="text-center mb-12 fade-in">
    <div class="flex items-center justify-center mb-6">
     <div
      class="bg-gradient-to-r from-purple-600 to-purple-500 p-4 rounded-2xl shadow-2xl"
     >
      <svg
       width="48"
       height="48"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
       class="text-white"
      >
       <path
        d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2z"
       />
       <path d="M8 14s1.5 2 4 2 4-2 4-2" />
       <path d="M9 9h.01" />
       <path d="M15 9h.01" />
      </svg>
     </div>
    </div>
    <h1
     class="text-5xl font-bold text-white mb-4 bg-gradient-to-r from-white to-purple-200 bg-clip-text text-transparent"
    >
     FinTransfer
    </h1>
    <p class="text-xl text-gray-400 max-w-2xl mx-auto leading-relaxed">
     Sua plataforma de transferências financeiras simples, segura e intuitiva.
     Agende suas transferências com facilidade total.
    </p>
   </div>
   <!-- Status do Backend -->
   <div class="text-center mb-8 fade-in-delay">
    <div
     class="inline-flex items-center gap-4 bg-gray-800 border border-gray-700 rounded-xl px-6 py-4 shadow-lg"
    >
     <button
      @click="checkHealth"
      :disabled="isCheckingHealth"
      class="flex items-center gap-3 px-4 py-2 bg-purple-600 hover:bg-purple-500 disabled:bg-purple-400 text-white font-semibold rounded-lg transition-all duration-200 transform hover:scale-105 disabled:scale-100"
     >
      <svg
       v-if="isCheckingHealth"
       class="animate-spin h-5 w-5"
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
       width="20"
       height="20"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
      >
       <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
      </svg>
      {{ isCheckingHealth ? "Verificando..." : "Verificar Status do Sistema" }}
     </button>
     <div v-if="healthMessage" class="flex items-center gap-3">
      <div
       :class="[
        'w-3 h-3 rounded-full',
        healthStatus === 'success' ? 'bg-green-400' : 'bg-red-400',
       ]"
      ></div>
      <span
       :class="[
        'font-medium',
        healthStatus === 'success' ? 'text-green-400' : 'text-red-400',
       ]"
      >
       {{ healthMessage }}
      </span>
     </div>
    </div>
   </div>
   <!-- Container Principal -->
   <div
    class="bg-gray-800 rounded-3xl shadow-2xl border border-gray-700 overflow-hidden"
   >
    <!-- Navigation Tabs -->
    <div class="bg-gray-750 border-b border-gray-700">
     <div class="flex">
      <button
       @click="activeTab = 'form'"
       :class="[
        'flex-1 py-6 px-8 font-semibold text-lg transition-all duration-300 relative group',
        activeTab === 'form'
         ? 'text-purple-400 bg-gray-800'
         : 'text-gray-400 hover:text-white hover:bg-gray-700',
       ]"
      >
       <div class="flex items-center justify-center gap-3">
        <svg
         width="24"
         height="24"
         fill="none"
         stroke="currentColor"
         stroke-width="2"
         viewBox="0 0 24 24"
        >
         <path d="M12 6v6l4 2" />
         <circle cx="12" cy="12" r="10" />
        </svg>
        Agendar Transferência
       </div>
       <div
        v-if="activeTab === 'form'"
        class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-20 h-1 bg-gradient-to-r from-purple-500 to-purple-400 rounded-t-full transition-all duration-300"
       ></div>
       <div
        v-else
        class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-0 group-hover:w-20 h-1 bg-gray-500 rounded-t-full transition-all duration-300"
       ></div>
      </button>
      <button
       @click="activeTab = 'list'"
       :class="[
        'flex-1 py-6 px-8 font-semibold text-lg transition-all duration-300 relative group',
        activeTab === 'list'
         ? 'text-purple-400 bg-gray-800'
         : 'text-gray-400 hover:text-white hover:bg-gray-700',
       ]"
      >
       <div class="flex items-center justify-center gap-3">
        <svg
         width="24"
         height="24"
         fill="none"
         stroke="currentColor"
         stroke-width="2"
         viewBox="0 0 24 24"
        >
         <path
          d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2-2z"
         />
         <path d="M8 21v-4a2 2 0 012-2h4a2 2 0 012 2v4" />
        </svg>
        Minhas Transferências
        <span
         v-if="transferCount > 0"
         class="bg-purple-600 text-white text-sm px-2 py-1 rounded-full min-w-[24px] text-center"
         >{{ transferCount }}</span
        >
       </div>
       <div
        v-if="activeTab === 'list'"
        class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-20 h-1 bg-gradient-to-r from-purple-500 to-purple-400 rounded-t-full transition-all duration-300"
       ></div>
       <div
        v-else
        class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-0 group-hover:w-20 h-1 bg-gray-500 rounded-t-full transition-all duration-300"
       ></div>
      </button>
     </div>
    </div>
    <!-- Conteúdo das Abas -->
    <div class="relative min-h-[600px]">
     <transition name="tab-fade" mode="out-in">
      <div v-if="activeTab === 'form'" key="form" class="fade-in">
       <TransferForm @transfer-scheduled="onTransferScheduled" />
      </div>
      <div v-else-if="activeTab === 'list'" key="list" class="fade-in">
       <TransferList ref="transferList" @transfers-loaded="onTransfersLoaded" />
      </div>
     </transition>
    </div>
   </div>
   <!-- Features destacadas -->
   <div class="grid md:grid-cols-3 gap-6 mt-12 fade-in-delay-2">
    <div
     class="bg-gray-800 border border-gray-700 rounded-xl p-6 text-center hover:border-purple-500 transition-all duration-300 transform hover:scale-105"
    >
     <div
      class="w-12 h-12 bg-purple-600 rounded-xl mx-auto mb-4 flex items-center justify-center"
     >
      <svg
       width="24"
       height="24"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
       class="text-white"
      >
       <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
      </svg>
     </div>
     <h3 class="text-lg font-semibold text-white mb-2">Segurança Total</h3>
     <p class="text-gray-400 text-sm">
      Suas transferências são protegidas com criptografia de ponta a ponta.
     </p>
    </div>
    <div
     class="bg-gray-800 border border-gray-700 rounded-xl p-6 text-center hover:border-purple-500 transition-all duration-300 transform hover:scale-105"
    >
     <div
      class="w-12 h-12 bg-purple-600 rounded-xl mx-auto mb-4 flex items-center justify-center"
     >
      <svg
       width="24"
       height="24"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
       class="text-white"
      >
       <circle cx="12" cy="12" r="10" />
       <polyline points="12,6 12,12 16,14" />
      </svg>
     </div>
     <h3 class="text-lg font-semibold text-white mb-2">Agilidade</h3>
     <p class="text-gray-400 text-sm">
      Agende suas transferências em segundos, de forma simples e intuitiva.
     </p>
    </div>
    <div
     class="bg-gray-800 border border-gray-700 rounded-xl p-6 text-center hover:border-purple-500 transition-all duration-300 transform hover:scale-105"
    >
     <div
      class="w-12 h-12 bg-purple-600 rounded-xl mx-auto mb-4 flex items-center justify-center"
     >
      <svg
       width="24"
       height="24"
       fill="none"
       stroke="currentColor"
       stroke-width="2"
       viewBox="0 0 24 24"
       class="text-white"
      >
       <path d="M16 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2" />
       <circle cx="8.5" cy="7" r="4" />
       <path d="M20 8v6M23 11l-3 3-3-3" />
      </svg>
     </div>
     <h3 class="text-lg font-semibold text-white mb-2">Controle Total</h3>
     <p class="text-gray-400 text-sm">
      Acompanhe todas as suas transferências com transparência completa.
     </p>
    </div>
   </div>
  </div>
 </div>
</template>

<script>
import { api } from "../services/api";
import TransferForm from "../components/TransferForm.vue";
import TransferList from "../components/TransferList.vue";

export default {
 name: "HomePage",
 components: {
  TransferForm,
  TransferList,
 },
 data() {
  return {
   healthMessage: "",
   healthStatus: "",
   activeTab: "form",
   transferCount: 0,
   isCheckingHealth: false,
  };
 },
 mounted() {
  this.checkHealth();
 },
 methods: {
  async checkHealth() {
   this.isCheckingHealth = true;
   try {
    const response = await api.get("/api/health");
    this.healthMessage = "Sistema operacional";
    this.healthStatus = "success";
   } catch (error) {
    this.healthMessage = "Sistema indisponível";
    this.healthStatus = "error";
   } finally {
    this.isCheckingHealth = false;
   }
  },
  onTransferScheduled() {
   this.activeTab = "list";
   this.$nextTick(() => {
    if (this.$refs.transferList) {
     this.$refs.transferList.refresh();
    }
   });
  },
  onTransfersLoaded(count) {
   this.transferCount = count || 0;
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
.fade-in {
 animation: fadeIn 0.6s ease-out forwards;
}

@keyframes fadeIn {
 from {
  opacity: 0;
  transform: translateY(10px);
 }
 to {
  opacity: 1;
  transform: translateY(0);
 }
}

.tab-fade-enter-active,
.tab-fade-leave-active {
 transition: opacity 0.5s, transform 0.5s;
}

.tab-fade-enter, .tab-fade-leave-to /* .tab-fade-leave-active in <2.1.8 */ {
 opacity: 0;
 transform: translateY(10px);
}
</style>
