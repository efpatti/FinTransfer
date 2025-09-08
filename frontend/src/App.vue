<template>
 <div class="min-h-screen bg-gray-50">
  <Navbar />
  <main class="main-content">
   <router-view />
  </main>
 </div>
</template>

<script>
import Navbar from "./components/Navbar.vue";

export default {
 components: {
  Navbar,
 },
};
console.log("testando");
</script>

<style>
* {
 margin: 0;
 padding: 0;
 box-sizing: border-box;
}

body {
 font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Roboto", "Oxygen",
  "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", sans-serif;
 -webkit-font-smoothing: antialiased;
 -moz-osx-font-smoothing: grayscale;
}

.main-content {
 min-height: calc(100vh - 64px);
}
</style>
