<template>
 <nav class="navbar">
  <div class="navbar-container">
   <!-- Logo/Brand -->
   <div class="navbar-brand">
    <router-link to="/" class="brand-link"> FinTransfer </router-link>
   </div>

   <!-- Navigation Links -->
   <div class="navbar-menu" :class="{ 'is-active': mobileMenuActive }">
    <div class="navbar-nav">
     <router-link
      v-if="isAuthenticated"
      to="/"
      class="nav-link"
      @click="closeMobileMenu"
     >
      Home
     </router-link>
    </div>

    <!-- Authentication Buttons -->
    <div class="navbar-auth">
     <template v-if="!isAuthenticated">
      <router-link
       to="/signin"
       class="btn btn-outline"
       @click="closeMobileMenu"
      >
       Sign In
      </router-link>
      <router-link
       to="/signup"
       class="btn btn-primary"
       @click="closeMobileMenu"
      >
       Sign Up
      </router-link>
     </template>
     <template v-else>
      <span class="user-greeting"> Welcome back, {{ user?.username }}! </span>
      <button @click="handleLogout" class="btn btn-outline">Logout</button>
     </template>
    </div>
   </div>

   <!-- Mobile Menu Toggle -->
   <div class="navbar-toggle" @click="toggleMobileMenu">
    <span class="toggle-bar"></span>
    <span class="toggle-bar"></span>
    <span class="toggle-bar"></span>
   </div>
  </div>
 </nav>
</template>

<script>
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import { useAuth } from "../services/useAuth";

export default {
 name: "Navbar",
 setup() {
  const router = useRouter();
  const mobileMenuActive = ref(false);
  const { isAuthenticated, user, logout } = useAuth();

  const toggleMobileMenu = () => {
   mobileMenuActive.value = !mobileMenuActive.value;
  };

  const closeMobileMenu = () => {
   mobileMenuActive.value = false;
  };

  const handleLogout = () => {
   logout();
   closeMobileMenu();
   router.push("/signin");
  };

  // Escutar eventos de mudança de autenticação
  const handleAuthChange = () => {
   // Forçar re-render quando o estado de auth mudar
   // (o reactive system do Vue já cuida disso, mas é bom ter como backup)
  };

  onMounted(() => {
   window.addEventListener("auth-changed", handleAuthChange);
  });

  onUnmounted(() => {
   window.removeEventListener("auth-changed", handleAuthChange);
  });

  // Watch for route changes to close mobile menu
  router.afterEach(() => {
   closeMobileMenu();
  });

  return {
   mobileMenuActive,
   isAuthenticated,
   user,
   toggleMobileMenu,
   closeMobileMenu,
   handleLogout,
  };
 },
};
</script>

<style scoped>
.navbar {
 background-color: #ffffff;
 box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
 padding: 0;
 position: sticky;
 top: 0;
 z-index: 1000;
}

.navbar-container {
 max-width: 1200px;
 margin: 0 auto;
 display: flex;
 align-items: center;
 justify-content: space-between;
 padding: 0 1rem;
 height: 64px;
}

.navbar-brand {
 font-size: 1.5rem;
 font-weight: bold;
}

.brand-link {
 color: #2563eb;
 text-decoration: none;
 transition: color 0.2s;
}

.brand-link:hover {
 color: #1d4ed8;
}

.navbar-menu {
 display: flex;
 align-items: center;
 gap: 2rem;
}

.navbar-nav {
 display: flex;
 align-items: center;
 gap: 1rem;
}

.nav-link {
 color: #374151;
 text-decoration: none;
 padding: 0.5rem 1rem;
 border-radius: 0.375rem;
 transition: all 0.2s;
 font-weight: 500;
}

.nav-link:hover,
.nav-link.router-link-active {
 color: #2563eb;
 background-color: #eff6ff;
}

.navbar-auth {
 display: flex;
 align-items: center;
 gap: 1rem;
}

.user-greeting {
 color: #374151;
 font-weight: 500;
 padding: 0.5rem;
}

.btn {
 padding: 0.5rem 1rem;
 border-radius: 0.375rem;
 text-decoration: none;
 font-weight: 500;
 transition: all 0.2s;
 border: none;
 cursor: pointer;
 font-size: 0.875rem;
 display: inline-flex;
 align-items: center;
 justify-content: center;
}

.btn-primary {
 background-color: #2563eb;
 color: white;
}

.btn-primary:hover {
 background-color: #1d4ed8;
}

.btn-outline {
 background-color: transparent;
 color: #2563eb;
 border: 1px solid #2563eb;
}

.btn-outline:hover {
 background-color: #2563eb;
 color: white;
}

.navbar-toggle {
 display: none;
 flex-direction: column;
 cursor: pointer;
 padding: 0.5rem;
}

.toggle-bar {
 width: 24px;
 height: 2px;
 background-color: #374151;
 margin: 2px 0;
 transition: 0.3s;
}

/* Mobile Styles */
@media (max-width: 768px) {
 .navbar-container {
  padding: 0 1rem;
 }

 .navbar-toggle {
  display: flex;
 }

 .navbar-menu {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: #ffffff;
  flex-direction: column;
  align-items: stretch;
  padding: 1rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transform: translateY(-100%);
  opacity: 0;
  visibility: hidden;
  transition: all 0.3s ease;
  gap: 1rem;
 }

 .navbar-menu.is-active {
  transform: translateY(0);
  opacity: 1;
  visibility: visible;
 }

 .navbar-nav {
  flex-direction: column;
  align-items: stretch;
  gap: 0.5rem;
 }

 .navbar-auth {
  flex-direction: column;
  align-items: stretch;
  gap: 0.5rem;
  border-top: 1px solid #e5e7eb;
  padding-top: 1rem;
 }

 .nav-link,
 .btn {
  text-align: center;
  padding: 0.75rem 1rem;
 }

 .user-greeting {
  text-align: center;
  padding: 0.5rem;
 }

 /* Hamburger animation */
 .navbar-menu.is-active ~ .navbar-toggle .toggle-bar:nth-child(1) {
  transform: rotate(-45deg) translate(-5px, 6px);
 }

 .navbar-menu.is-active ~ .navbar-toggle .toggle-bar:nth-child(2) {
  opacity: 0;
 }

 .navbar-menu.is-active ~ .navbar-toggle .toggle-bar:nth-child(3) {
  transform: rotate(45deg) translate(-5px, -6px);
 }
}
</style>
