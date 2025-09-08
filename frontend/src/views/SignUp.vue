<template>
 <div
  class="flex flex-col items-center justify-center min-h-screen bg-gray-900 py-10 px-4 fade-in"
 >
  <form
   @submit.prevent="handleSignUp"
   class="w-full max-w-md bg-gray-800 rounded-2xl shadow-2xl p-8 border border-gray-700"
  >
   <div class="flex items-center justify-center mb-8">
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
      <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z" />
     </svg>
     <span class="text-white text-2xl font-bold">Sign Up</span>
    </div>
   </div>
   <div class="space-y-6">
    <div class="form-group">
     <label
      for="username"
      class="block text-sm font-semibold mb-3 text-gray-200"
      >Username</label
     >
     <input
      v-model="username"
      id="username"
      required
      class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
      placeholder="Choose a username"
     />
    </div>
    <div class="form-group">
     <label
      for="password"
      class="block text-sm font-semibold mb-3 text-gray-200"
      >Password</label
     >
     <input
      v-model="password"
      id="password"
      type="password"
      required
      class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
      placeholder="Choose a password"
     />
    </div>
    <div class="form-group">
     <label for="confirm" class="block text-sm font-semibold mb-3 text-gray-200"
      >Confirm Password</label
     >
     <input
      v-model="confirm"
      id="confirm"
      type="password"
      required
      class="w-full px-4 py-4 bg-gray-700 border-2 border-gray-600 rounded-xl text-white placeholder-gray-400 focus:outline-none focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20 text-lg transition-all duration-200"
      placeholder="Repeat your password"
     />
    </div>
   </div>
   <button
    type="submit"
    :disabled="isSubmitting"
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
    <span v-else>Sign Up</span>
   </button>
   <div
    v-if="message"
    :class="[
     'mt-6 p-4 rounded-xl border-2 font-semibold text-center transition-all duration-300',
     success
      ? 'bg-green-900/30 border-green-500 text-green-400'
      : 'bg-red-900/30 border-red-500 text-red-400',
    ]"
   >
    {{ message }}
   </div>
   <div class="mt-6 text-center">
    <router-link to="/signin" class="text-purple-400 hover:underline"
     >Already have an account? Sign In</router-link
    >
   </div>
  </form>
 </div>
</template>

<script>
import { useAuth } from "../services/useAuth";

export default {
 name: "SignUp",
 data() {
  return {
   username: "",
   password: "",
   confirm: "",
   isSubmitting: false,
   message: "",
   success: false,
  };
 },
 setup() {
  const { register } = useAuth();
  return { register };
 },
 methods: {
  async handleSignUp() {
   this.isSubmitting = true;
   this.message = "";
   if (this.password !== this.confirm) {
    this.success = false;
    this.message = "Passwords do not match.";
    this.isSubmitting = false;
    return;
   }
   try {
    await this.register(this.username, this.password);
    this.success = true;
    this.message = "Account created! You are now logged in.";
    // Aguardar um pouco para mostrar a mensagem de sucesso
    setTimeout(() => {
     this.$router.push("/");
    }, 1500);
   } catch (error) {
    this.success = false;
    this.message = error.response?.data?.message || "Could not create account.";
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
</style>
